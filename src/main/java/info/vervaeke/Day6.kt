package info.vervaeke

import java.io.File
import kotlin.text.Charsets.UTF_8

class Day6(val fileName: String) {

    fun part1(): Long {
        return countFishAfterNDays(80)
    }

    fun part2(): Long {
        return countFishAfterNDays(256)
    }

    private fun countFishAfterNDays(numDays: Int): Long {
        var fish = readFile().lines
        var counts = fish.groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        (0 until numDays).forEach {
            counts = iteration2(counts).toMap()
        }
        return counts.values.sum()
    }

    fun iteration2(values: Map<Long, Long>) : Map<Long, Long> {
        val result = mutableMapOf<Long, Long>()
        result.put(0, values.get(1)?:0)
        result.put(1, values.get(2)?:0)
        result.put(2, values.get(3)?:0)
        result.put(3, values.get(4)?:0)
        result.put(4, values.get(5)?:0)
        result.put(5, values.get(6)?:0)
        result.put(6, (values.get(7)?:0 )+ (values.get(0)?:0))
        result.put(7, values.get(8)?:0)
        result.put(8, values.get(0)?:0)

        return result
    }

    fun readFile(): Day6Input {
        val lines = File(fileName)
            .readLines(UTF_8)
            .get(0)

        return Day6Input(lines.split(",").map{ it.toLong()})
    }

    data class Day6Input(val lines: List<Long>)
}

fun main() {
    println(Day6(inputFileOfDay(6)).part1())
    println(Day6(inputFileOfDay(6)).part2())
}

