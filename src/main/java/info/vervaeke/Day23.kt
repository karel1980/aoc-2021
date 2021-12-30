package info.vervaeke

import java.util.Stack
import java.util.concurrent.atomic.AtomicLong

class Day23 {

    companion object {

        val HALL_DISTANCES = mapOf(
            "A" to listOf(2, 1, 1, 3, 5, 7, 8),
            "B" to listOf(4, 3, 1, 1, 3, 5, 6),
            "C" to listOf(6, 5, 3, 1, 1, 3, 4),
            "D" to listOf(8, 7, 5, 3, 1, 1, 2)
        )

        // this returns the position of the first hall position right of the room exit
        val roomEntrancePositions = mapOf(
            "A" to 2,
            "B" to 3,
            "C" to 4,
            "D" to 5,
        )

        fun parse(input: String): World {
            val roomStacks = input.split(",")
                .map { createStack(it.toList().map { it.toString() }.reversed()) }
            val rooms = listOf(
                Room("A", roomStacks[0]),
                Room("B", roomStacks[1]),
                Room("C", roomStacks[2]),
                Room("D", roomStacks[3]),
            ).map { it.id to it }.toMap()

            return World((0 until 7).map { null }.toMutableList(), rooms)
        }

        private fun createStack(stackContents: List<String>): Stack<String> {
            val result = Stack<String>()
            stackContents.forEach { result.push(it) }
            return result
        }

        fun part1(world: World): Long {
            return world.findCheapestWayToSolve(mutableListOf(), 0)
        }

        fun part2(world: World): Long {
            return world.findCheapestWayToSolve(mutableListOf(), 0)
        }

        fun calculateMoveCost(move: Move, room: Room): Long {
            val distance = HALL_DISTANCES[room.id]!![move.hallPosition] + (room.roomSize - room.pods.size) +
                    if (move.direction == Direction.TO_HALL) {
                        1
                    } else {
                        0
                    }
            return when (move.pod) {
                "A" -> distance.toLong()
                "B" -> 10 * distance.toLong()
                "C" -> 100 * distance.toLong()
                "D" -> 1000 * distance.toLong()
                else -> TODO("shouldnt happen")
            }
        }

    }

    data class World(val hall: MutableList<String?>, val rooms: Map<String, Room>) {

        fun findCheapestWayToSolve(moves: MutableList<Move>, cost: Long): Long {
            if (moves.size > 32) {
                println("Too many moves (error) ${hall}, ${rooms}, $moves")
                throw RuntimeException("debug me")
            }

//            println(moves)

            if (rooms.values.all { it.isDone() }) {
//                println("Solution! $moves")
                return cost
            }

            val possibleMoves = findPossibleMoves()
            if (possibleMoves.isEmpty()) {
                return Long.MAX_VALUE
            }
            val remainingMoves = possibleMoves.map { it to calculateMoveCost(it, rooms[it.room]!!) }

            var best = Long.MAX_VALUE
            remainingMoves.forEach { (move, moveCost) ->
                moves.add(move)
                applyMove(move)
                val newCost = findCheapestWayToSolve(moves, cost + moveCost)
                if (newCost < best) {
                    best = newCost
                }
                applyMove(
                    move.copy(
                        direction = if (move.direction == Direction.TO_HALL) {
                            Direction.TO_ROOM
                        } else {
                            Direction.TO_HALL
                        }
                    )
                )
                moves.removeLast()
            }
            return best
        }

        fun findPossibleMoves(): List<Move> {
            val result = mutableListOf<Move>()

            // room to hall moves
            rooms.values.filter { room -> !room.pods.isEmpty() && !room.pods.all { it == room.id } }
                .forEach { room ->
                    hall.forEachIndexed { idx, pod ->
                        if (pod == null) {
                            result.add(Move(Direction.TO_HALL, idx, room.id, room.pods.peek()))
                        }
                    }
                }

            // hall to room moves
            hall.forEachIndexed { idx, pod ->
                // TODO: make rooms a map for readability here
                if (pod != null) {
                    val room = rooms[pod]!!
                    if (room.canBeEntered()) {
                        result.add(Move(Direction.TO_ROOM, idx, pod, pod))
                    }
                }
            }

            //filter out impossible moves (where there is something in the way between the hall position and the room entrance)
            return result.filter { move ->
                val roomPos = roomEntrancePositions[move.room]!!
                if (move.hallPosition < roomPos) {
                    hall.subList(move.hallPosition + 1, roomPos).all { it == null }
                } else {
                    hall.subList(roomPos, move.hallPosition).all { it == null }
                }
            }

        }

        fun applyMove(move: Move) {
            val room = rooms[move.room]!!
            if (move.direction == Direction.TO_ROOM) {
                room.pods.push(hall[move.hallPosition])
                hall[move.hallPosition] = null
            } else {
                hall[move.hallPosition] = room.pods.pop()
            }
        }

        override fun toString(): String {
            return "$hall // $rooms"
        }

        //        override fun toString(): String {
//            val hall = hall.map { it ?: "." }
//            val hallRow = "${hall[0]}${hall[1]}.${hall[2]}.${hall[3]}.${hall[4]}.${hall[5]}${hall[6]}"
//
//            val roomSize = rooms.values.first().roomSize
//
//            val roomLines = (0 until roomSize)
//                .map { roomSize - it - 1 } // pod index
//                .map { podIdx: Int ->
//                    "ABCD".map { it.toString() }
//                        .joinToString("#") {
//                            if (rooms[it]!!.pods.size < podIdx) {
//                                "."
//                            } else {
//                                rooms[it]!!.pods[podIdx]
//                            }
//                        }
//                }
//
//            return """
//#############
//#$hallRow#
//###${roomLines.first()}###
//""" + roomLines.drop(1).joinToString("\n") { "  #$it#" }
//        }
    }

    data class Room(val id: String, val pods: Stack<String>) {
        val roomSize = pods.size

        fun isDone(): Boolean {
            return pods.size == roomSize && pods.all { it == id }
        }

        fun canBeEntered(): Boolean {
            return pods.all { it == id }
        }
    }

    enum class Direction {
        TO_HALL, TO_ROOM
    }

    data class Move(val direction: Direction, val hallPosition: Int, val room: String, val pod: String) {
        override fun toString(): String {
            return "Move(direction=$direction, hallPosition=$hallPosition, room=$room)"
        }
    }
}

