package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

class Day2(val fileName: String) {

    fun part1(): Int {
        var x = 0
        var depth = 0
        readFile2()
            .forEach {
                val dir = it[0]
                val dist = it[1].toInt()

                if (dir == "forward") {
                    x += dist
                } else if (dir == "down") {
                    depth += dist
                } else if (dir == "up") {
                    depth -= dist
                } else {
                    println("oops ${it}")
                    throw RuntimeException()
                }
            }

        return x * depth
    }

    fun part(): Int {
        var pos = 0
        var aim = 0
        var depth = 0
        readFile2()
            .forEach {
                val dir = it[0]
                val x = it[1].toInt()

                println(it)
                if (dir == "forward") {
                    pos += x
                    depth += aim * x
                } else if (dir == "down") {
                    aim += x
                } else if (dir == "up") {
                    aim -= x
                } else {
                    println("oops ${it}")
                    throw RuntimeException()
                }
                println("pos $pos / aim $aim / depth $depth")
            }

        return pos * depth
    }

    fun readFile2(): List<List<String>> {
        return File(fileName)
            .readLines(UTF_8)
            .map { it.split(" ") }
    }
}

fun main() {
    println(Day2(DAY2_INPUT).part1())
    println(Day2(DAY2_INPUT).part())
}
