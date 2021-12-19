package info.vervaeke

class Day13() {

    companion object {
        fun parse(lines: List<String>): Pair<Paper, List<FoldInstruction>> {
            val points = lines.takeWhile { !it.isEmpty() }
                .map {
                    val values = it.split(",").map { it.toInt() }
                    Coordinate(values[0], values[1])
                }
            val instructions = lines.drop(points.size + 1)
                .map {
                    "fold along (.)=(\\d*)$".toRegex().find(it)!!
                }
                .filterNotNull()
                .map { it.groups }
                .map {
                    it.get(1)!!.value to it.get(2)!!.value.toInt()
                }
                .map {
                    val direction = if (it.first == "y") {
                        Direction.HORIZONTAL
                    } else {
                        Direction.VERTICAL
                    }
                    FoldInstruction(direction, it.second)
                }
            return Paper(points.toSet()) to instructions
        }

        fun part1(input: Pair<Paper, List<FoldInstruction>>): Int {
            val originalPaper = input.first
            val instructions = input.second

            return originalPaper.fold(instructions.first()).points.size
        }

        fun part2(input: Pair<Paper, List<FoldInstruction>>): String {
            val originalPaper = input.first
            val instructions = input.second

            val result = instructions.fold(originalPaper) { paper, instr -> paper.fold(instr) }

            return result.pretty()
        }
    }

    data class Paper(val points: Set<Coordinate>) {

        fun pretty(): String {
            val grid = (0..points.maxOf { it.y }).map { y ->
                (0..points.maxOf { it.x }).map { x ->
                    Coordinate(x, y) in points
                }
            }

            return grid.joinToString("\n") { row ->
                row.joinToString("") {
                    if (it) {
                        "#"
                    } else {
                        "."
                    }
                }
            }
        }

        fun fold(foldInstruction: FoldInstruction): Paper {
            if (foldInstruction.direction == Direction.HORIZONTAL) {
                return foldHorizontal(foldInstruction.line)
            } else {
                return foldVertical(foldInstruction.line)
            }
        }

        fun foldHorizontal(line: Int): Paper {
            println("Fold horizontal on line $line")
            val maxY = bounds().second.y

            assert(points.all { it.y != line })

            val newPoints = points.map {
                if (it.y < line) {
                    it
                } else {
                    Coordinate(it.x, line - (it.y - line))
                }
            }

//            val newTop = line - (maxY - line)
//            if (newTop < 0) {
//                return Paper(newPoints.map { Coordinate(it.x, it.y - newTop) }.toSet())
//            } else {

            println("old bounds: ${bounds()}")
            val result = Paper(newPoints.toSet())

            println("new bounds: ${result.bounds()}")
            assert(newPoints.all { it.y >= 0})
            return result
//            }
        }

        fun bounds(): Pair<Coordinate, Coordinate> {
            val minX = points.minOf { it.x}
            val maxX = points.maxOf { it.x}
            val minY = points.minOf {it.y}
            val maxY = points.maxOf {it.y}

            return Coordinate(minX, minY) to Coordinate(maxX, maxY)
        }

        fun foldVertical(line: Int): Paper {
            val maxX = points.maxOf { it.x }

            val newPoints = points.map {
                if (it.x < line) {
                    it
                } else {
                    Coordinate(line - (it.x - line), it.y)
                }
            }

            val newLeft = line - (maxX - line)
            if (newLeft < 0) {
                assert(false)
                return Paper(newPoints.map { Coordinate(it.x - newLeft, it.y) }.toSet())
            } else {
                return Paper(newPoints.toSet())
            }
        }
    }

    data class Coordinate(val x: Int, val y: Int)

    enum class Direction {
        HORIZONTAL,
        VERTICAL
    }

    data class FoldInstruction(val direction: Direction, val line: Int)

}


fun main() {
    println(Day11().part1(Day11.parse(inputLinesOfDay(11))))
//    println(Day11(inputLinesOfDay(11)).part2())
}

