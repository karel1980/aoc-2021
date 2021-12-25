package info.vervaeke

import java.math.BigInteger
import java.util.Collections.min

class Day21 {

    companion object {
        val BIG27 = BigInteger.valueOf(27)

        fun part1(p1Start: Int, p2Start: Int): Int {
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

                val oldPos = positions[player]
                val newPos = (oldPos + 9 + moves) % 10 + 1

                scores[player] += newPos
                positions[player] = newPos

                player = 1 - player
            }

            return round * 3 * min(scores)
        }


        fun part2(p1Start: Int, p2Start: Int): BigInteger {
            val endStates = playUntilOnlyWinners(p1Start, p2Start)
            return endStates.maxOf { it.value }
        }

        fun playUntilOnlyWinners(p1Start: Int, p2Start: Int): Map<GameState, BigInteger> {
            val initialStates = mapOf<GameState, BigInteger>(LiveGame(listOf(0, 0), listOf(p1Start, p2Start)) to BigInteger.valueOf(1))
            var currentStates = initialStates
            var player = 0
            println("states: ${currentStates.size}")
            while (currentStates.keys.any { it is LiveGame }) {
                val prevTotalStates: BigInteger = currentStates.values.sumOf { it }

                currentStates = playOneRound(currentStates, player)
                println("states: ${currentStates.size}")
                player = 1 - player
                val totalStates: BigInteger = currentStates.values.sumOf { it }
                if (prevTotalStates > totalStates) {
                    println("disappearing universes! $prevTotalStates -> $totalStates")
                    throw RuntimeException("total state count shouldn't go down")
                }
            }
            return currentStates
        }

        fun playOneRound(
            allStates: Map<GameState, BigInteger>,
            player: Int
        ): Map<GameState, BigInteger> {
            return applyRoll(player, allStates)
        }

        fun applyRoll(player: Int, allStates: Map<GameState, BigInteger>): Map<GameState, BigInteger> {
            println("player rolling: $player")
            val totalStates = allStates.values.sumOf { it }
            val live = allStates.filterKeys { it is LiveGame } as Map<LiveGame, BigInteger>
            val won = allStates.filterKeys { it is WonGameState }

            val newStates = listOf(
                applyRollWithValue(live, player, 3, 1),
                applyRollWithValue(live, player, 4, 3),
                applyRollWithValue(live, player, 5, 6),
                applyRollWithValue(live, player, 6, 7),
                applyRollWithValue(live, player, 7, 6),
                applyRollWithValue(live, player, 8, 3),
                applyRollWithValue(live, player, 9, 1),
            )

            val result = won.toMutableMap()

            newStates.forEach { stateMap ->
                stateMap.forEach { (state, count) ->
                    result[state] = result.getOrDefault(state, BigInteger.ZERO).plus(count)
                }
            }


            // before X won, Y not won
            // after roll X + Yw won, (Y * 27) - Yw
            val origWon = allStates.filterKeys { it is WonGameState }.values.sumOf { it }
            val origLive = allStates.filterKeys { it is LiveGame }.values.sumOf { it }

            val afterRollWon = newStates.sumOf { m -> m.filterKeys { it is WonGameState }.values.sumOf { it }}
            val afterRollLive = newStates.sumOf { m -> m.filterKeys { it is LiveGame }.values.sumOf { it }}

            val resultWon = result.filterKeys { it is WonGameState }.values.sumOf { it }
            val resultLive = result.filterKeys { it is LiveGame }.values.sumOf { it }

            if (origWon.plus(afterRollWon) != resultWon) {
                TODO("Amount of wins should increase by number of wins caused by roll")
            }

            println("live before: $origLive")
            println("live after roll: $afterRollLive")
            println("won after roll: $afterRollWon")
            if (origLive.multiply(BIG27) != afterRollWon.plus(afterRollLive)) {
                TODO("each roll should multiply amount of universes by 27")
            }

            val newTotalStates = result.values.sumOf { it }
            if (totalStates > newTotalStates) {
                TODO("shouldnt happen")
            }

            return result
        }

        fun applyRollWithValue(states: Map<LiveGame, BigInteger>, player: Int, rollValue: Int, multiply: Long): Map<GameState, BigInteger> {
            val result = states.map { (state, count) -> createRollPair(state, player, rollValue, multiply, count) }
                .groupBy { it.first }
                .mapValues {
                    it.value.map { it.second }.sumOf { count -> count }
                }

            val liveBefore = states.values.sumOf { it }
            val liveAfter = result.filterKeys { it is LiveGame }.values.sumOf { it }
            val wonAfter = result.filterKeys { it is WonGameState }.values.sumOf { it }

            if (liveBefore.multiply(BigInteger.valueOf(multiply)) != liveAfter.plus(wonAfter)) {
//                println(states)
//                println(result)
                TODO("rolling $rollValue should multiply universes by $multiply: $liveBefore * $multiply != $liveAfter + $wonAfter")
            }

            return result
        }

        private fun createRollPair(
            state: LiveGame,
            player: Int,
            rollValue: Int,
            multiply: Long,
            count: BigInteger
        ): Pair<GameState, BigInteger> {
            return state.roll(player, rollValue) to BigInteger.valueOf(multiply).multiply(count)
        }
    }




        interface GameState {
        fun roll(player: Int, rolledValue: Int): GameState
    }

    data class LiveGame(val scores: List<Int>, val positions: List<Int>) : GameState {
        override fun roll(player: Int, rolledValue: Int): GameState {
            val newPositions = positions.toMutableList()
            val oldPos = positions[player]
            newPositions[player] = (oldPos + 9 + rolledValue) % 10 + 1

            val newScores = scores.toMutableList()
            newScores[player] += newPositions[player]

            if (newScores[player] >= 21) {
                return WonGameState(player)
            } else {
                return LiveGame(newScores.toList(), newPositions.toList())
            }
        }
    }

    data class WonGameState(val winner: Int) : GameState {
        override fun roll(player: Int, rolledValue: Int) = throw IllegalStateException("Cant roll for a stopped game")
    }

}
