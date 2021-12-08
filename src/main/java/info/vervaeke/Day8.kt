package info.vervaeke

class Day8(val inputLines: List<String>) {

    fun part1(): Int {
        return parseInputLines().flatMap { it.second }
            .filter { it.segments.size in setOf(2,3,4,7) }
            .size
    }

    fun part2(): Int {
        return parseInputLines().sumOf { getOutput(it.first, it.second) }
    }

    private fun getOutput(digits: List<Digit>, outputDigits: List<Digit>): Int {
        val digitMap = buildDigitMap(digits)
        return outputDigits.map { digitMap.get(it)!! }.joinToString("").toInt()
    }

    fun parseInputLines(): List<Pair<List<Digit>, List<Digit>>> = inputLines
        .map { it.split("|") }
        .map { parts ->
            val digits = parts[0].trim().split(" ")
                .map { Digit(it.toSet()) }
            val output = parts[1].trim().split(" ")
                .map { Digit(it.toSet()) }
            Pair(digits, output)
        }

    fun buildDigitMap(digits: List<Digit>): Map<Digit, Int> {
        // easy ones
        val one = digits.first { it.numberOfSegments() == 2 }
        val seven = digits.first { it.numberOfSegments() == 3 }
        val four = digits.first { it.numberOfSegments() == 4 }
        val eight = digits.first { it.numberOfSegments() == 7 }

        val fiveSegmentDigits = digits.filter { it.numberOfSegments() == 5 }
        val sixSegmentDigits = digits.filter { it.numberOfSegments() == 6 }

        // three is the only one with 5 segments that has 2 segments in common with one
        val three = fiveSegmentDigits.first { it.commonSegments(one) == 2 }

        // 2,5 -> only 2 has 2 segments in common with four, 5 has 3 in common
        val two = fiveSegmentDigits.filter { it != three }.first { it.commonSegments(four) == 2 }
        val five = fiveSegmentDigits.first { it != three && it != two }

        // 0,6,9 -> 9 has 4 segments in common with 4
        val nine = sixSegmentDigits.first { it.commonSegments(four) == 4 }
        // 0 has 2 digits in common with one
        val zero = sixSegmentDigits.filter { it != nine }.first { it.commonSegments(one) == 2 }
        // remaining 6 segment digit is 6
        val six = sixSegmentDigits.first { it != nine && it != zero }

        return mapOf(
            one to 1,
            two to 2,
            three to 3,
            four to 4,
            five to 5,
            six to 6,
            seven to 7,
            eight to 8,
            nine to 9,
            zero to 0,
        )
    }

    data class Digit(val segments: Set<Char>) {

        constructor(segments: String):
                this(segments.toSet())

        init {
            if (!segments.all { it in "abcdefg" }) {
                throw RuntimeException("Invalid segments: $segments")
            }
        }

        fun numberOfSegments() = segments.size

        fun commonSegments(other: Digit) = segments.intersect(other.segments).size
    }

}

fun main() {
    println(Day8(inputLinesOfDay(8)).part1())
    println(Day8(inputLinesOfDay(8)).part2())
}

