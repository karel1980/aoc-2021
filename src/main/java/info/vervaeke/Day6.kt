package info.vervaeke

class Day6(val inputLines: List<String>) {

    fun part1(): Long {
        return countFishAfterNDays(80)
    }

    fun part2(): Long {
        return countFishAfterNDays(256)
    }

    private fun countFishAfterNDays(numDays: Int): Long {
        val fish = parseInput()
        val counts = fish.groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        return (0 until numDays)
            .fold(counts) { c,n -> nextDay(c) }
            .values
            .sum()
    }

    fun nextDay(values: Map<Long, Long>) : Map<Long, Long> {
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

    fun parseInput(): List<Long> {
        return inputLines[0].split(",").map{ it.toLong()}
    }

}

fun main() {
    println(Day6(inputLinesOfDay(6)).part1())
    println(Day6(inputLinesOfDay(6)).part2())
}

