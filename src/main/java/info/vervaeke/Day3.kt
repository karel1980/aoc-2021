package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

class Day3(val fileName: String) {

    fun part1(): Int {
        val lines = readFile()
        val n = lines[0].length

        var ones = (0..n - 1).map { 0 }.toIntArray()
        lines.forEach { line ->
            (0..n - 1)
                .forEach { pos ->
                    if (line[pos] == '1') {
                        ones[pos] += 1;
                    }
                }
        }

        val gamma = StringBuffer()
        val epsilon = StringBuffer()
        ones.forEach { count ->
            gamma.append(
                if (count > lines.size / 2) {
                    1
                } else {
                    0
                }
            )
            epsilon.append(
                if (count > lines.size / 2) {
                    0
                } else {
                    1
                }
            )
        }
//        println(gamma.toString())
//        println(epsilon.toString())

        val gammaVal = gamma.toString().toInt(2)
//        println(gammaVal)
        val epsilonVal = epsilon.toString().toInt(2)
//        println(epsilonVal)

        return gammaVal * epsilonVal
    }

    fun part2(): Int {
        val lines = readFile()
        val oxygen = findOxygen(lines)
        val co2 = findCo2(lines)

        println(oxygen.toInt(2))
        println(co2.toInt(2))
        return oxygen.toInt(2) * co2.toInt(2)
    }

    private fun findOxygen(lines: List<String>): String {
        var current = lines
        var found = false
        var pos = 0
        while (!found) {
//            println(current)
            var mostCommonChar = mostCommon(current, pos)
            current = current.filter {
                it[pos] == mostCommonChar
            }
            found = current.toSet().size == 1
            pos += 1
        }

        return current[0]
    }
    private fun findCo2(lines: List<String>): String {
        var current = lines
        var found = false
        var pos = 0
        while (!found) {
            var leastCommonChar = leastCommon(current, pos)
            current = current.filter {
                it[pos] == leastCommonChar
            }
            found = current.toSet().size == 1
            pos += 1
        }

        return current[0]
    }

    private fun mostCommon(current: List<String>, pos: Int): Any? {
        val zeros = current.filter {it[pos] == '0'}.size
        val ones = current.filter {it[pos] == '1'}.size

        if (zeros > ones) {
            return '0'
        } else {
            return '1'
        }
    }

    private fun leastCommon(current: List<String>, pos: Int): Any? {
        val zeros = current.filter {it[pos] == '0'}.size
        val ones = current.filter {it[pos] == '1'}.size

        if (zeros > ones) {
            return '1'
        } else {
            return '0'
        }
    }

    fun readFile(): List<String> {
        return File(fileName)
            .readLines(UTF_8)
    }
}

fun main() {
    println(Day3(inputFileOfDay(3)).part1())
    println(Day3(inputFileOfDay(3)).part2())
}
