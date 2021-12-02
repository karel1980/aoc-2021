package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

class Day3(val fileName: String) {

    fun part1(): Int {
        readFile()
            .forEach {
                println(it)
            }

        return 42
    }

    fun part2(): Int {
        TODO()
    }

    fun readFile(): List<List<String>> {
        return File(fileName)
            .readLines(UTF_8)
            .map { it.split(" ") }
    }
}

fun main() {
    println(Day3(DAY3_INPUT).part1())
    println(Day3(DAY3_INPUT).part2())
}
