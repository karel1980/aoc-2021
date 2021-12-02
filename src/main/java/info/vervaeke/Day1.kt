package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

class Day1(val fileName: String) {

    companion object {
        const val SAMPLE_INPUT = "p1.sample.txt"
        const val REAL_INPUT = "p1.txt"
    }

    fun part1(): Int {
        return readFile()
            .windowed(2)
            .count { it[0] < it[1] }
    }

    fun part2(): Int {
        return readFile()
            .windowed(3)
            .map { it.sum() }
            .windowed(2)
            .count { it[0] < it[1] }
    }

    fun readFile(): List<Int> {
        return File(fileName)
            .readLines(UTF_8)
            .map { it.toInt() }
    }
}

fun main() {
    println(Day1("p1.txt").part1())
    println(Day1("p1.txt").part2())
}