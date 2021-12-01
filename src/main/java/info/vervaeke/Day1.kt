package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

fun day1(fileName: String): Int {
    return readFile(fileName)
        .windowed(2)
        .count { it[0] < it[1] }
}

fun day1b(fileName: String): Int {
    return readFile(fileName)
        .windowed(3)
        .map { it.sum() }
        .windowed(2)
        .count { it[0] < it[1] }
}

fun readFile(fileName: String): List<Int> {
    return File(fileName)
        .readLines(UTF_8)
        .map { it.toInt() }
}

fun main() {
    println(day1("p1.txt"))
    println(day1b("p1.txt"))
}