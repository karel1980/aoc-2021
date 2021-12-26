package info.vervaeke

class Day22 {

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
            val limits = -50..50
            val instructions = instructions
                .filter { it.cuboid.corner1.x in limits }
                .filter { it.cuboid.corner2.x in limits }
                .filter { it.cuboid.corner1.y in limits }
                .filter { it.cuboid.corner2.y in limits }
                .filter { it.cuboid.corner1.z in limits }
                .filter { it.cuboid.corner2.z in limits }
            var result = totalOnCubes(instructions)
            return result
        }

        fun part2(instructions: List<Instruction>): Long {
            return totalOnCubes(instructions)
        }

        private fun totalOnCubes(instructions: List<Instruction>): Long {
            val xs = (instructions.map { it.cuboid.corner1.x } + instructions.map { it.cuboid.corner2.x + 1 }).toSortedSet().toList().sorted()
            val ys = (instructions.map { it.cuboid.corner1.y } + instructions.map { it.cuboid.corner2.y + 1 }).toSortedSet().toList().sorted()
            val zs = (instructions.map { it.cuboid.corner1.z } + instructions.map { it.cuboid.corner2.z + 1 }).toSortedSet().toList().sorted()

            // on  10------20
            // off     15-------25
            // end: 10-14 is on (size 5)

            // xs = 10,15,21,26
            // xs zip with next: 10-15, 15-21, 21-26

            println("xs ${xs.size}")
            println("ys ${ys.size}")
            println("zs ${zs.size}")

            var result = 0L
            xs.zipWithNext().forEachIndexed { index, dx ->
                println("$index")
                ys.zipWithNext().forEach { dy ->
                    zs.zipWithNext().forEach { dz ->
                        val volume = (dx.second - dx.first) * (dy.second - dy.first) * (dz.second - dz.first)
        //                        println("cubelet $dx $dy $dz")
        //                        println("volume $volume")
                        val lastInstruction = instructions.lastOrNull() {
                            dx.first in it.cuboid.corner1.x..it.cuboid.corner2.x && dy.first in it.cuboid.corner1.y..it.cuboid.corner2.y && dz.first in it.cuboid.corner1.z..it.cuboid.corner2.z
                        }
        //                        println("last matching instruction: $lastInstruction")
                        if (lastInstruction != null && lastInstruction.state == State.ON) {
                            result += volume
                        }
                    }
                }
            }
            return result
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

}

