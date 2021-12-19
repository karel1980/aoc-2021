package info.vervaeke

import kotlin.math.max
import kotlin.math.min

class Day11() {

    companion object {
        fun parse(lines: List<String>): MutableList<MutableList<Int>> {
            return lines
                .map {
                    it.toList()
                        .map { char -> char.toString().toInt() }
                        .toMutableList()
                }
                .toMutableList()
        }
    }

    fun part1(octopi: List<List<Int>>): Int {
        var total = 0
        var state = octopi
        (0 until 100).forEach {
            state = step(state)
            total += countEqualTo(state, 0)
        }
        return total
    }

    fun part2(octopi: List<List<Int>>): Int {
        var state = octopi
        var step = 0

        while (!state.all { row -> row.all { it == 0 }}) {
            state = step(state)
            step += 1
        }

        return step
    }

    fun step(octopi: List<List<Int>>): List<List<Int>> {
        var result = octopi.map { row -> row.map { it + 1 } }

//        println("After increase: ")
//        println(result)

        while (countOverNine(result) > 0) {
            //for each flashing octopus, increase all of its neighbors
            var overNine = findOverNine(result)
            result = (0 until result.size).map { row ->
                (0 until result[row].size).map { col ->
                    if (overNine[row][col] || result[row][col] == 0) {
                        0
                    } else {
                        result[row][col] + countFlashingNeighbors(overNine, row, col)
                    }
                }
            }

//            println("Updated neighbors:")
//            println(result)
        }

        return result
    }

    private fun findOverNine(result: List<List<Int>>): List<List<Boolean>> {
        return result.map { row -> row.map { value -> value > 9 } }
    }

    private fun countOverNine(result: List<List<Int>>): Int {
        return result.sumOf { row -> row.count { it > 9 } }
    }

    private fun findEqualTo10(result: List<List<Int>>): List<List<Boolean>> {
        return findEqualTo(result, 10)
    }

    private fun findEqualToZero(result: List<List<Int>>): List<List<Boolean>> {
        return findEqualTo(result, 10)
    }

    private fun findEqualTo(octopi: List<List<Int>>, value: Int) =
        octopi.map { row -> row.map { it == value } }

    private fun countEqualTo(octopi: List<List<Int>>, value: Int) =
        findEqualTo(octopi, value).sumOf { row -> row.count { it } }

    fun countFlashingNeighbors(flashing: List<List<Boolean>>, row: Int, col: Int): Int {
        val minRow = max(0, row - 1)
        val maxRow = min(flashing.size - 1, row + 1)

//        println("rows from $minRow to $maxRow")

        return (minRow..maxRow).sumOf { rowNum ->
            val minCol = max(0, col - 1)
            val maxCol = min(flashing[rowNum].size - 1, col + 1)
//            println("cols from $minCol to $maxCol")

            (minCol..maxCol).count { colNum ->
                flashing[rowNum][colNum]
            }
        }
    }

}

fun main() {
    println(Day11().part1(Day11.parse(inputLinesOfDay(11))))
//    println(Day11(inputLinesOfDay(11)).part2())
}

