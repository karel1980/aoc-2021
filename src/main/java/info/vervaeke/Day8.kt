package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

class Day8(val fileName: String) {

    fun part1(): Int {
        return count1478(readFile())
    }

    private fun count1478(lines: List<Pair<List<String>, List<String>>>): Int {
        return lines.map { countPart1Digits(it) }.sum()
    }

    fun countPart1Digits(line: Pair<List<String>, List<String>>): Int {
        return line.second.filter { it.length in setOf(2, 3, 4, 7) }.count()
    }

    fun part2(): Int {
        return readFile().map { getOutput(it) }.sum()
    }

    private fun getOutput(line: Pair<List<String>, List<String>>): Int {
        val digits = line.first
        val outputDigits = line.second.drop(1).map { it.alphabeticSort() }

        val digitMap = buildDigitMap(digits)

        return outputDigits.map { digitMap.get(it)!! }.joinToString("").toInt()
    }

    fun readFile(): List<Pair<List<String>, List<String>>> {
        return File(fileName)
            .readLines(UTF_8)
            .map { it.split("|") }
            .map {
                val digits = it[0].split(" ")
                    .map { it.alphabeticSort() }
                val output = it[1].split(" ")
                    .map { it.alphabeticSort() }
                Pair(digits, output)
            }
    }

    fun buildDigitMap(digits: List<String>): Map<String, Int> {
        // easy ones
        val one = digits.first { it.length == 2 }
        val seven = digits.first { it.length == 3 }
        val four = digits.first { it.length == 4 }
        val eight = digits.first { it.length == 7 }

        val fiveSegmentNumbers = digits.filter { it.length == 5 }
        val sixSegmentNumbers = digits.filter { it.length == 6 }

        // three is the only one with 5 segments that has 2 segments in common with one
        val three = fiveSegmentNumbers.first { commonSegments(it, one) == 2 }

        // 2,5 -> only 2 has 2 segments in common with four, 5 has 3 in common
        val two = fiveSegmentNumbers.filter { it != three }.first { commonSegments(it, four) == 2 }
        val five = fiveSegmentNumbers.first { it != three && it != two }

        // 0,6,9 -> 9 has 4 segments in common with 4
        val nine = sixSegmentNumbers.first { commonSegments(it, four) == 4 }
        // 0 has 2 digits in common with one
        val zero = sixSegmentNumbers.filter { it != nine }.first { commonSegments(it, one) == 2 }
        // remaining 6 segment digit is 6
        val six = sixSegmentNumbers.first { it != nine && it != zero }

        return mapOf(
            Pair(one, 1),
            Pair(two, 2),
            Pair(three, 3),
            Pair(four, 4),
            Pair(five, 5),
            Pair(six, 6),
            Pair(seven, 7),
            Pair(eight, 8),
            Pair(nine, 9),
            Pair(zero, 0),
        ).mapKeys { it.key.alphabeticSort() }
    }

    private fun commonSegments(firstDigit: String, secondDigit: String) = firstDigit.toSet().intersect(secondDigit.toSet()).size

}

private fun String.alphabeticSort() = this.toSet().sorted().joinToString("")


fun main() {
    println(Day8(DAY8_INPUT).part1())
    println(Day8(DAY8_INPUT).part2())
}

