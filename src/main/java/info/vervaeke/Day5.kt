package info.vervaeke

import java.io.File
import java.lang.Math.abs
import kotlin.math.max
import kotlin.math.sign
import kotlin.text.Charsets.UTF_8

class Day5(val fileName: String) {

    fun part1(): Int {
        val (segments, world) = readFile()

        segments.forEach { segment ->
            segment.orthogonalCells.forEach { cell ->
                world.increment(cell)
            }
        }

        return world.countCrossings()
    }

    fun part2(): Int {
        val (segments, world) = readFile()

        segments.forEach { segment ->
            segment.allCells.forEach { cell ->
                world.increment(cell)
            }
        }

        return world.countCrossings()
    }

    fun readFile(): Day5Input {
        val segments = File(fileName)
            .readLines(UTF_8)
            .map {
                it.split(" -> ")
            }
            .map {
                Segment(Coordinate(it[0].split(",").map { it.toInt() }), Coordinate(it[1].split(",").map { it.toInt() }))
            }
        val maxRow = segments.maxOf { it.from.row }
        val maxCol = segments.maxOf { it.from.col }

        return Day5Input(segments, World(
            (0..maxRow).map {
                (0..maxCol).map { 0 }.toMutableList()
            }
        ))
    }

    class World(rows: List<MutableList<Int>>) : Grid<Int>(rows) {
        fun increment(cell: Coordinate) {
            rows[cell.row][cell.col] += 1
        }

        fun countCrossings() = rows.flatMap { it }
            .count { it > 1 }
    }

    data class Coordinate(private val rowCol: List<Int>) {
        val row = rowCol[0]
        val col = rowCol[1]
    }

    data class Segment(val from: Coordinate, val to: Coordinate) {
        val orthogonalCells: List<Coordinate>
            get() =
                if (isHorizontal() || isVertical()) {
                    allCells
                } else {
                    emptyList()
                }


        val allCells: List<Coordinate>
            get() {
                val dx = (to.col - from.col).sign
                val dy = (to.row - from.row).sign
                return (0 until length).map {
                    Coordinate(listOf(from.row + it * dy, from.col + it * dx))
                }
            }

        val length: Int
            get() {
                return max(abs(from.row - to.row), abs(from.col - to.col)) + 1
            }

        fun isHorizontal() = from.row == to.row
        fun isVertical() = from.col == to.col
    }

    data class Day5Input(val segments: List<Segment>, val world: World)
}

fun main() {
    println(Day5(DAY5_INPUT).part1())
    println(Day5(DAY5_INPUT).part2())
}

