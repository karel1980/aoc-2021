package info.vervaeke

import java.util.Collections.min

class Day21 {

    companion object {
        fun play(p1Start: Int, p2Start: Int): Int {
            var round = 0
            var player = 0
            val scores = mutableListOf(0, 0)
            val positions = mutableListOf(p1Start, p2Start)

            var nextRoll = 1
            while (scores.all { it < 1000 }) {
                round += 1
                var moves = 0
                moves += nextRoll
                nextRoll = (nextRoll + 100) % 100 + 1
                moves += nextRoll
                nextRoll = (nextRoll + 100) % 100 + 1
                moves += nextRoll
                nextRoll = (nextRoll + 100) % 100 + 1
                println("rolled " + moves)

                val oldPos = positions[player]
                val newPos = (oldPos + 9 + moves) % 10 + 1

                scores[player] += newPos
                println("scores $scores")
                positions[player] = newPos
                println("positions: $positions")

                player = 1 - player
            }

            println(scores)
            println(round * 3 * min(scores))

            return 0
        }
    }

}

fun main() {
    println(Day21.play(8, 9))
}
