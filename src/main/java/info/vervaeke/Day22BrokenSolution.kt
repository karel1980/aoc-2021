package info.vervaeke

import kotlin.math.max
import kotlin.math.min

class Day22BrokenSolution {

    companion object {
        fun parse(lines: List<String>): List<Instruction> {
            return lines.map { parseInstruction(it) }
        }

        fun parseInstruction(str: String): Instruction {
            val regex = "([a-z]*) x=(.*),y=(.*),z=(.*)".toRegex()

            println("parsing $str")
            val groups = regex.matchEntire(str)!!.groupValues
            val state = if (groups[1] == "on") State.ON else State.OFF
            val xyz = groups.drop(2).take(3).map { it.split("..").map { it.toInt() } }

            val corner1 = Coordinate(xyz[0].minOf { it }, xyz[1].minOf { it }, xyz[2].minOf { it })
            val corner2 = Coordinate(xyz[0].maxOf { it }, xyz[1].maxOf { it }, xyz[2].maxOf { it })

            return Instruction(state, Cuboid(corner1, corner2))
        }

        fun part1(instructions: List<Instruction>): Long {
            var world = RangeManager<RangeManager<RangeManager<State>>>()

            instructions.forEach { instruction ->
                world = applyInstructions(instruction, world)
            }

            val totalOn = world.ranges.sumOf {
                (it.end.toLong() - it.start - 1) * it.value.ranges.sumOf {
                    (it.end.toLong() - it.start - 1) * it.value.ranges.filter { it.value == State.ON }.sumOf { it.end.toLong() - it.start - 1 }
                }
            }

            return totalOn
        }

        fun applyInstructions(
            instruction: Instruction,
            world: RangeManager<RangeManager<RangeManager<State>>>
        ) : RangeManager<RangeManager<RangeManager<State>>> {
            val dx = instruction.cuboid.corner1.x to instruction.cuboid.corner2.x + 1
            val dy = instruction.cuboid.corner1.y to instruction.cuboid.corner2.y + 1
            val dz = instruction.cuboid.corner1.z to instruction.cuboid.corner2.z + 1
            return world.applyToRange(dx.first, dx.second) {
                (it ?: RangeManager()).applyToRange(dy.first, dy.second) {
                    (it ?: RangeManager()).applyToRange(dz.first, dz.second) { instruction.state }
                }
            }
        }
    }

    data class Instruction(val state: State, val cuboid: Cuboid)

    enum class State(val value: Int) {
        ON(1), OFF(0)
    }

    data class Coordinate(val x: Int, val y: Int, val z: Int)

    data class Cuboid(val corner1: Coordinate, val corner2: Coordinate) {
        val volume = calculateVolume()

        fun calculateVolume(): Long {
            if (corner1.x > corner2.x) {
                throw RuntimeException("invalid cuboid $corner1 to $corner2")
            }
            if (corner1.y > corner2.y) {
                throw RuntimeException("invalid cuboid $corner1 to $corner2")
            }
            if (corner1.z > corner2.z) {
                throw RuntimeException("invalid cuboid $corner1 to $corner2")
            }
            val vol =
                (corner2.x.toLong() - corner1.x.toLong() + 1) * (corner2.y.toLong() - corner1.y.toLong() + 1) * (corner2.z.toLong() - corner1.z.toLong() + 1)
            if (vol < 0) {
                throw RuntimeException("volume should not be negative ($corner1 $corner2)")
            }
//            println("volume for $corner1 to $corner2 is $vol")
            return vol
        }
    }

    data class RangeManager<T>(val ranges: List<Range<T>> = listOf()) {

        fun applyToRange(from: Int, to: Int, applyFn: (T?) -> T): RangeManager<T> {
            val sortedRanges = ranges.sortedBy { it.start }
            val matchPred: (Range<T>) -> Boolean = { it.start in from until to || it.end in from until to }

            val overlapping = sortedRanges.filter(matchPred)
            val notOverlapping = sortedRanges.filterNot(matchPred)

            return RangeManager(notOverlapping + applyToOverlapping(overlapping, from, to, applyFn))
        }

        fun applyToOverlapping(overlapping: List<Range<T>>, from: Int, to: Int, applyFn: (T?) -> T): List<Range<T>> {
            if (overlapping.isEmpty()) {
                return listOf(Range(from, to, applyFn(null)))
            }
            ///        start-------------------end
            /// from           to
            /// from                                   to
            ///                 from   to
            ///                 from                   to
            /// BEFORE          OVERLAP          AFTER
            return overlapping.flatMap {
                val before = if (from < it.start) {
                    Range(from, it.start, applyFn(null))
                } else null
                val after = if (to > it.end) {
                    Range(it.end, to, applyFn(null))
                } else null

                val before2 = if (from > it.start) {
                    Range(it.start, from, it.value)
                } else null
                val after2 = if (to < it.end) {
                    Range(to, it.end, it.value)
                } else null

                val overlap = Range(max(it.start, from), min(it.end, to), applyFn(it.value))
//                println("overlap $overlap")

                listOf(before, before2, overlap, after2, after)
                    .filterNotNull()
            }
        }

    }

    data class Range<T>(val start: Int, val end: Int, val value: T) {
        fun split(splitPoint: Int): List<Range<T>> {
            // splitPoint is the first value of the new range
            if (splitPoint <= start || splitPoint >= end) {
                return listOf(this)
            }

            return listOf(
                Range(start, splitPoint, value),
                Range(splitPoint, end, value)
            )
        }
    }

}

