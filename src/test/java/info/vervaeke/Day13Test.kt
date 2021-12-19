package info.vervaeke

import info.vervaeke.Day13.Coordinate
import info.vervaeke.Day13.Direction.HORIZONTAL
import info.vervaeke.Day13.Direction.VERTICAL
import info.vervaeke.Day13.FoldInstruction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day13Test {

    val SAMPLE1 = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
            
            fold along y=7
            fold along x=5""".trimIndent().lines()

    @Test
    fun `parse`() {
        assertThat(Day13.parse(SAMPLE1))
            .isEqualTo(
                Day13.Paper(
                    setOf(
                        Coordinate(6, 10),
                        Coordinate(0, 14),
                        Coordinate(9, 10),
                        Coordinate(0, 3),
                        Coordinate(10, 4),
                        Coordinate(4, 11),
                        Coordinate(6, 0),
                        Coordinate(6, 12),
                        Coordinate(4, 1),
                        Coordinate(0, 13),
                        Coordinate(10, 12),
                        Coordinate(3, 4),
                        Coordinate(3, 0),
                        Coordinate(8, 4),
                        Coordinate(1, 10),
                        Coordinate(2, 14),
                        Coordinate(8, 10),
                        Coordinate(9, 0),
                    )
                ) to listOf(FoldInstruction(HORIZONTAL, 7), FoldInstruction(VERTICAL, 5))
            )
    }

    @Test
    fun `pretty`() {
        val actual = Day13.parse(SAMPLE1).first.pretty()

        assertThat(
            actual
        )
            .isEqualTo(
                """
                    ...#..#..#.
                    ....#......
                    ...........
                    #..........
                    ...#....#.#
                    ...........
                    ...........
                    ...........
                    ...........
                    ...........
                    .#....#.##.
                    ....#......
                    ......#...#
                    #..........
                    #.#........
                """.trimIndent()
            )
    }

    @Test
    fun `foldHorizontal`() {
        val grid = Day13.parse(SAMPLE1).first.fold(FoldInstruction(HORIZONTAL, 7)).pretty()
        assertThat(grid)
            .isEqualTo(
                """
    #.##..#..#.
    #...#......
    ......#...#
    #...#......
    .#.#..#.###
""".trimIndent()
            )
    }

    @Test
    fun `foldVertical`() {
        val grid = Day13.parse(SAMPLE1).first
            .fold(FoldInstruction(HORIZONTAL, 7))
            .fold(FoldInstruction(VERTICAL, 5))
            .pretty()
        assertThat(grid)
            .isEqualTo(
                """
#####
#...#
#...#
#...#
#####""".trimIndent()
            )
    }

    @Test
    fun `count dots`() {
        val folded = Day13.parse(SAMPLE1).first.fold(FoldInstruction(HORIZONTAL, 7))
        assertThat(folded.points.size)
            .isEqualTo(17)
    }

    @Test
    fun `part1 sample`() {
        assertThat(Day13.part1(Day13.parse(SAMPLE1)))
            .isEqualTo(16)
    }

    @Test
    fun `check initial paper size`() {
        val input = Day13.parse(inputLinesOfDay(13))
        assertThat(input.first.points.minOf { it.x })
            .isEqualTo(0)
        assertThat(input.first.points.maxOf { it.x })
            .isEqualTo(1310)
        assertThat(input.first.points.minOf { it.y })
            .isEqualTo(0)
        assertThat(input.first.points.maxOf { it.y })
            .isEqualTo(894)
    }

    @Test
    fun `more horizontal folding`() {
        // ....## -> ##...
        assertThat(
            Day13.Paper(setOf(Coordinate(0, 4), Coordinate(0, 5)))
                .fold(FoldInstruction(HORIZONTAL, 0))
        )
            .isEqualTo(Day13.Paper(setOf(Coordinate(0, 0), Coordinate(0, 1))))

        // .....# -> .|...# -> #...
        assertThat(
            Day13.Paper(setOf(Coordinate(0, 4), Coordinate(0, 5)))
                .fold(FoldInstruction(HORIZONTAL, 1))
        )
            .isEqualTo(Day13.Paper(setOf(Coordinate(0, 0), Coordinate(0, 1))))

        // ....## -> ..|.## -> ##
        assertThat(
            Day13.Paper(setOf(Coordinate(0, 4), Coordinate(0, 5)))
                .fold(FoldInstruction(HORIZONTAL, 2))
        )
            .isEqualTo(Day13.Paper(setOf(Coordinate(0, 0), Coordinate(0, 1))))

        // ....## -> ...|## -> .##
        assertThat(
            Day13.Paper(setOf(Coordinate(0, 4), Coordinate(0, 5)))
                .fold(FoldInstruction(HORIZONTAL, 3))
        )
            .isEqualTo(Day13.Paper(setOf(Coordinate(0, 1), Coordinate(0, 2))))

        // .#|#. -> .#
        assertThat(
            Day13.Paper(setOf(Coordinate(0, 1), Coordinate(0, 3)))
                .fold(FoldInstruction(HORIZONTAL, 2))
        )
            .isEqualTo(Day13.Paper(setOf(Coordinate(0, 1))))
    }

    @Test
    fun `part1`() {
        // 1020 is too high :( -> Dammit I didn't read to only do the first fild
        // 102 is too low :( -> Same problem
        assertThat(Day13.part1(Day13.parse(inputLinesOfDay(13))))
            .isEqualTo(0)
    }

    @Test
    fun `part2`() {
        // 1020 is too high :( -> Dammit I didn't read to only do the first fild
        // 102 is too low :( -> Same problem
        assertThat(Day13.part2(Day13.parse(inputLinesOfDay(13))))
            .isEqualTo("""
                .##..#..#..##...##..###...##...##..#..#
                #..#.#..#.#..#.#..#.#..#.#..#.#..#.#..#
                #..#.####.#....#....#..#.#....#..#.#..#
                ####.#..#.#.##.#....###..#.##.####.#..#
                #..#.#..#.#..#.#..#.#....#..#.#..#.#..#
                #..#.#..#..###..##..#.....###.#..#..##.""".trimIndent())
    }

}
