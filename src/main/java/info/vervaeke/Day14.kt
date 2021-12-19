package info.vervaeke

class Day14() {

    companion object {
        fun parse(lines: List<String>): Pair<String, Map<String, String>> {
            val template = lines.first()
            val rules = lines.drop(2)
                .map { it.split(" -> ") }
                .map { it[0] to it[1] }
                .toMap()

            return template to rules
        }
    }

    fun part1(template: String, rules: Map<String, String>): Int {
        val endChain = (0 until 10).fold(template) { chain, value ->
            insert(chain, rules)
        }
        val mostFrequent = endChain.groupBy { it }.maxOf { it.value.size }
        val leastFrequent = endChain.groupBy { it }.minOf { it.value.size }

        return mostFrequent - leastFrequent
    }

    fun insert(template: String, rules: Map<String, String>): String {
        return template.zipWithNext()
            .joinToString("") {
                val pair = "${it.first}${it.second}"

                val a = it.first
                val b = rules.get(pair) ?: ""

                "$a$b"
            } + template.last()
    }

    data class Rule(val pair: String, val insertion: String)

}


fun main() {
    println(Day11().part1(Day11.parse(inputLinesOfDay(11))))
//    println(Day11(inputLinesOfDay(11)).part2())
}

