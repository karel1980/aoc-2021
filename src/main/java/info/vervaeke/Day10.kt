package info.vervaeke

import java.util.Stack

val PAIRS = mapOf(
    '(' to ')',
    '<' to '>',
    '[' to ']',
    '{' to '}',
)

class Day10(val inputLines: List<String>) {

    fun part1(): Int {
        val scores = mapOf<Char, Int>(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)//26397
        return inputLines.map {
            firstInvalidChar(it).first
        }
            .filterNotNull()
            .map { scores.get(it) }
            .filterNotNull()
            .sum()

    }

    public fun firstInvalidChar(line: String): Pair<Char?, Stack<Char>> {
        val stack = Stack<Char>()

        var result: Char? = null

        line.forEach {
            if (result != null) {
                return@forEach
            }
//            println("char $it")
            if (it.isClosing()) {
                if (stack.isEmpty()) {
                    result = it
                    return@forEach
                }
//                println("peeking $it ${stack.peek()}")
                if (it.matches(stack.peek())) {
                    stack.pop()
                } else {
                    result = it
                }
            } else {
//                println("added $it to stack")
                stack.push(it)
            }
//            println("stack $stack")
        }
        return result to stack
    }

    fun part2(): Long {
        val scoresList = inputLines.map {
            firstInvalidChar(it)
        }
            .filter { it.first == null }
            .map { it.second }
            .map { stackScore(it) }

        println(scoresList)
        return scoresList.sorted().get((scoresList.size)/2)

    }

    private fun stackScore(stack: Stack<Char>): Long {
        val scores = stack.reversed()
            .map { scoreOf(it) }
//        println("scores $scores")
        val total = scores
            .fold(0L) { total, score -> total * 5 + score }
//        println("total $total")
        return total
    }

    private fun scoreOf(c: Char): Long {
        return mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4).get(c)!!
            .toLong()
    }


}

fun Char.isClosing() = this in "}])>".toSet()
fun Char.matches(other: Char) = ((PAIRS.get(this)) == other) || ((PAIRS.get(other)) == this)

fun main() {
    println(Day10(inputLinesOfDay(10)).part1())
    println(Day10(inputLinesOfDay(10)).part2())
}

