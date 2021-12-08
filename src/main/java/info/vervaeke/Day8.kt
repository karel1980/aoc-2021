package info.vervaeke

class Day8(val inputLines: List<String>) {

    fun part1(): Int {
        return parseInputLines().flatMap { it.second }
            .filter { it.length in setOf(2,3,4,7) }
            .size
    }

    fun part2(): Int {
        return parseInputLines().sumOf { getOutput(it.first, it.second) }
    }

    private fun getOutput(digits: List<String>, outputDigits: List<String>): Int {
        val digitMap = buildDigitMap(digits)
        return outputDigits.map { digitMap.get(it)!! }.joinToString("").toInt()
    }

    fun parseInputLines(): List<Pair<List<String>, List<String>>> = inputLines
        .map { it.split("|") }
        .map { parts ->
            val digits = parts[0].split(" ")
                .map { it.alphabeticSort() }
            val output = parts[1].trim().split(" ")
                .map { it.alphabeticSort() }
            Pair(digits, output)
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
        ).mapKeys { it.key.alphabeticSort() }
    }

    private fun commonSegments(firstDigit: String, secondDigit: String) = firstDigit.toSet().intersect(secondDigit.toSet()).size

}

private fun String.alphabeticSort() = this.toSet().sorted().joinToString("")


fun main() {
    println(Day8(inputLinesOfDay(8)).part1())
    println(Day8(inputLinesOfDay(8)).part2())
}

