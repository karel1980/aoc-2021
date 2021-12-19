package info.vervaeke

class Day12() {

    companion object {
        fun parse(lines: List<String>): Map<String, Set<String>> {
            val pairs = lines
                .map {
                    val parts = it.split("-")
                    parts[0] to parts[1]
                }

            val nodeMap: Map<String, Set<String>> = assoc(pairs)
            val reverseNodeMap: Map<String, Set<String>> = assoc(pairs.map { it.second to it.first })

            val keys = nodeMap.keys
            val values = nodeMap.values.flatMap { it }
            val ids = keys.union(values)

            return ids.associate { it to ((nodeMap.get(it)?: emptySet()) union (reverseNodeMap.get(it)?: emptySet())) }
        }

        private fun assoc(pairs: List<Pair<String, String>>) = pairs
            .groupBy { it.first }
            .mapValues { connection ->
                connection.value.map {
                    it.second
                }.toSet()
            }
            .withDefault { emptySet() } //TODO: withDefault not working?
    }

    fun part1(lines: List<String>): Int {
        return findDistinctPaths(parse(lines)).size
    }

    fun part2(lines: List<String>): Int {
        return findDistinctPaths2(parse(lines)).size
    }

    fun findDistinctPaths(nodes: Map<String, Set<String>>): Set<List<String>> {
        val result = mutableSetOf<List<String>>()

        collectPaths(result, nodes, listOf("start"))

        return result
    }

 fun findDistinctPaths2(nodes: Map<String, Set<String>>): Set<List<String>> {
        val result = mutableSetOf<List<String>>()

        collectPaths2(result, nodes, listOf("start"))

        return result
    }

    private fun collectPaths(result: MutableSet<List<String>>, nodes: Map<String, Set<String>>, currentPath: List<String>) {
        val current = currentPath.last()

        if (current == "end") {
            result.add(currentPath)
            return
        }

        nodes[current]!!.forEach { neighbor ->
            val isUpper = neighbor.uppercase() == neighbor
            if (isUpper || neighbor !in currentPath) {
                collectPaths(result, nodes, currentPath + listOf(neighbor))
            }
        }
    }

    private fun collectPaths2(result: MutableSet<List<String>>, nodes: Map<String, Set<String>>, currentPath: List<String>) {
//        println("path $currentPath")
        val current = currentPath.last()

        if (current == "end") {
//            println("Added $currentPath")
            result.add(currentPath)
            return
        }

        nodes[current]!!.forEach { neighbor ->
            if (allowAddingNeighbor(currentPath, neighbor)) {
                collectPaths2(result, nodes, currentPath + listOf(neighbor))
            }
        }
    }

    private fun allowAddingNeighbor(currentPath: List<String>, neighbour: String): Boolean {
        if (neighbour.uppercase() == neighbour) {
            return true
        }

        if (neighbour == "end") {
            return true
        }
        if (neighbour == "start") {
            return false
        }

        if (neighbour !in currentPath) {
            return true
        }

        val doubleLowerCaseVisits = currentPath
            .filter { it.lowercase() == it }
            .groupBy { it }
            .values
            .count { it.size > 1  }

        return doubleLowerCaseVisits < 1
    }

    data class Node(val id: String, val neighbours: List<String>)

}

fun main() {
    println(Day11().part1(Day11.parse(inputLinesOfDay(11))))
//    println(Day11(inputLinesOfDay(11)).part2())
}

