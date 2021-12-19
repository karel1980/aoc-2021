package info.vervaeke

import java.util.LinkedList

class Day15(val lines: List<List<Int>>) {

    companion object {
        fun parse(lines: List<String>): List<List<Int>> {
            return lines.map { it.map { it.toString().toInt() } }
        }
    }

    fun part1(): Int {
        return findShortestPath().get(Coordinate(lines.size - 1, lines.last().size - 1))!!.score
    }

    private fun findShortestPath(): Map<Coordinate, Path> {
        val startPath = pathOf(listOf(Coordinate(0, 0)))
        val bestPaths = mutableMapOf(Coordinate(0, 0) to startPath)
        var candidates = LinkedList(listOf(startPath))

        while (candidates.isNotEmpty()) {
            println("Remaining candidates: ${candidates.size}")
            val path = candidates.pop()
            val extensions = extend(path)
            val improvements = extensions.filter {
                val best = bestPaths.get(it.coordinates.last())
                best == null || it.score < best.score
            }
//            println("improvemnts: ${improvements.size}")
            improvements.forEach {
                bestPaths.put(it.coordinates.last(), it)
                candidates.push(it)
            }

            candidates = LinkedList(candidates.sortedBy { it.score })
        }

        return bestPaths
    }

    fun pathScore(coordinates: List<Coordinate>): Int {
        return coordinates.drop(1)
            .sumOf {
                lines.get(it.row).get(it.col)
            }
    }

    fun extend(path: Path): List<Path> {
        val end = path.coordinates.last()

        return listOf(end.left, end.right, end.top, end.bottom)
            .filter { isWithinBounds(it) }
            .filter { it !in path.coordinates }
            .map { pathOf(path.coordinates + listOf(it)) }
    }

    private fun isWithinBounds(coordinate: Coordinate): Boolean {
        return coordinate.row >= 0 && coordinate.row < lines.size
                && coordinate.col >= 0 && coordinate.col < lines[coordinate.row].size
    }

    fun pathOf(coordinates: List<Coordinate>) = Path(coordinates, pathScore(coordinates))

    data class Path(val coordinates: List<Coordinate>, val score: Int)

    data class Coordinate(val row: Int, val col: Int) {
        val left get() = Coordinate(row, col - 1)
        val right get() = Coordinate(row, col + 1)
        val top get() = Coordinate(row - 1, col)
        val bottom get() = Coordinate(row + 1, col)
    }


}


