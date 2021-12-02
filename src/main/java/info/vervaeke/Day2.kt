package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

fun day2(fileName: String): Int {
    var x = 0
    var depth = 0
    readFile2(fileName)
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

fun day2b(fileName: String): Long {
    var pos = 0L
    var aim = 0L
    var depth = 0L
    readFile2(fileName)
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

//fun day2b(fileName: String): Int {
//    return readFile2(fileName)
//        .windowed(3)
//        .map { it.sum() }
//        .windowed(2)
//        .count { it[0] < it[1] }
//}

fun readFile2(fileName: String): List<List<String>> {
    return File(fileName)
        .readLines(UTF_8)
        .map { it.split(" ") }
}

fun main() {
//    println(day2("p2.txt"))
    println(day2b("p2.txt"))
}
