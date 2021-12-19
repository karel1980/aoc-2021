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

    fun part1(template: String, rules: Map<String, String>): Long {
        return solveWithCountMap(template, rules, 10)
    }

    fun part2(template: String, rules: Map<String, String>): Long {
        return solveWithCountMap(template, rules, 40)
    }

    fun solveWithCountMap(template: String, rules: Map<String, String>, iterations: Int): Long {
        val pairCounts: Map<String, Long> = createPairCounts(template)

        val endCounts: Map<String, Long> = (0 until iterations).fold(pairCounts) { counts, idx ->
            val result = insert2(counts, rules)
            println("pair counts $result")
            result
        }

        println("endCounts: " + endCounts)

        val chars = endCounts.keys.map { it.first() }.union(endCounts.keys.map { it.last() })
            .filter { it != '#' }
        println("Chars: $chars")
        val charCounts = chars.map { char ->
            char to (endCounts.filter { it.key.first() == char }.values.sum() + endCounts.filter { it.key.last() == char }.values.sum())/2
        }.toMap()
        println("charCounts: $charCounts")

        val result = charCounts.maxOf { it.value } - charCounts.minOf { it.value }
        println(result)

        return result
    }

    fun insert2(pairCounts: Map<String, Long>, rules: Map<String, String>): Map<String, Long> {
        val result = pairCounts.toMutableMap()

        pairCounts.forEach { entry ->
            val pair = entry.key
            val count = entry.value

            val insertion = rules.get(pair)
            if (insertion != null) {
                updateCount(result, "${pair.first()}$insertion", count)
                updateCount(result, "$insertion${pair.last()}", count)
                updateCount(result, pair, -count)
            } else {
                result.put(pair, count)
            }
        }

        return result
    }

    private fun updateCount(result: MutableMap<String, Long>, key: String, delta: Long) {
        result.put(key, (result.get(key) ?: 0) + delta)
        if (result.get(key) == 0L) {
            result.remove(key)
        }
    }

    fun createPairCounts(template: String) = "#${template}#".zipWithNext()
        .map { "${it.first}${it.second}" }
        .groupBy { it }
        .map { it.key to it.value.size.toLong() }
        .toMap()

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

