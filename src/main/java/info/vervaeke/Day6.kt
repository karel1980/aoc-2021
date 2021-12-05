package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

class Day6(val fileName: String) {

    fun part1(): Int {
        return 0
    }

    fun part2(): Int {
        return 0
    }

    fun readFile(): Day6Input {
        val lines = File(fileName)
            .readLines(UTF_8)

        return Day6Input(lines)
    }

    data class Day6Input(val lines: List<String>)
}

fun main() {
    println(Day6(DAY6_INPUT).part1())
    println(Day6(DAY6_INPUT).part2())
}

