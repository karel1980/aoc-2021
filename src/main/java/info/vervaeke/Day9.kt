package info.vervaeke

class Day9(val inputLines: List<String>) {

    fun part1() = parseInput().size

    fun part2() = parseInput().size

    fun parseInput() = inputLines
}

fun main() {
    println(Day9(inputLinesOfDay(9)).part1())
    println(Day9(inputLinesOfDay(9)).part2())
}

