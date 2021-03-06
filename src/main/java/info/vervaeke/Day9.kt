package info.vervaeke

class Day9(val inputLines: List<String>) {

    fun part1(): Int {
        val grid = parseInput()
        val lowPoints = findLowPoints(grid)
        println("low ${lowPoints}")
        val corner = adjacentCells(grid, 0, 0)
        println("adjacent to corner cell ${corner}")
        return lowPoints.sumOf {it.value + 1}
    }

    private fun findLowPoints(grid: List<List<Int>>) : List<Cell> {
        val cells = allCells(grid)
        println("num cells ${cells.size}")
        return cells.filter { cell ->
            adjacentCells(grid, cell.row, cell.col).all { adjacent -> adjacent.value > cell.value }
        }
    }

    private fun adjacentCells(grid: List<List<Int>>, row: Int, col: Int): List<Cell> {
        return listOf(row-1 to col, row+1 to col, row to col-1, row to col+1)
            .filter { p -> p.first >= 0 && p.first  <grid.size }
            .filter { p -> p.second >= 0 && p.second < grid[p.first].size }
            .map { p -> Cell(p.first, p.second, grid[p.first][p.second]) }
    }

    private fun allCells(grid: List<List<Int>>) : List<Cell> {
        return (0 until grid.size).flatMap { rowNo ->
            (0 until grid[rowNo].size).map { colNo ->
                Cell(rowNo, colNo, grid[rowNo][colNo])
            }
        }
    }

    fun part2(): Int {
        val grid = parseInput()
        val lowPoints = findLowPoints(grid)

        val largestBasins: List<List<Cell>> = lowPoints.map { basinOf(grid, it) }
            .sortedBy { it.size }
            .takeLast(3)

        return largestBasins[0].size * largestBasins[1].size * largestBasins[2].size
    }

    private fun basinOf(grid: List<List<Int>>, cell: Cell): List<Cell> {
        var candidates = setOf(cell)
        var seen = mutableSetOf<Cell>()

        while (candidates.isNotEmpty()) {
            val candidate = candidates.first()
            seen.add(candidate)
            val adjacent = adjacentCells(grid, candidate.row, candidate.col)
                .filter { it.value > candidate.value && it.value < 9 }
            candidates = (candidates.drop(1) + adjacent.filter { it !in seen }).toSet()
        }

        return seen.toList()
    }

    fun parseInput(): List<List<Int>> {
        return inputLines.map {
            it.map { c ->
                c.toString()
                    .toInt()
            }
        }
    }

    data class Cell(val row: Int, val col: Int, val value: Int)
}

fun main() {
    println(Day9(inputLinesOfDay(9)).part1())
    println(Day9(inputLinesOfDay(9)).part2())
}

