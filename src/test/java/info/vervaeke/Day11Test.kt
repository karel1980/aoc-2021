package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day11Test {

    @Test
    fun `part 1 parse`() {
        assertThat(
            Day11.parse(
                """
                    11111
                    19991
                    19191
                    19991
                    11111""".trimIndent().lines()
            )
        ).isEqualTo(
            listOf(
                listOf(1, 1, 1, 1, 1),
                listOf(1, 9, 9, 9, 1),
                listOf(1, 9, 1, 9, 1),
                listOf(1, 9, 9, 9, 1),
                listOf(1, 1, 1, 1, 1),
            )
        )
    }

    @Test
    fun `simple step`() {
        val step0 = Day11.parse(
            """
                11111
                19991
                19191
                19991
                11111""".trimIndent().lines()
        )
        val step1 = listOf(
            listOf(3, 4, 5, 4, 3),
            listOf(4, 0, 0, 0, 4),
            listOf(5, 0, 0, 0, 5),
            listOf(4, 0, 0, 0, 4),
            listOf(3, 4, 5, 4, 3),
        )
        val step2 = listOf(
            listOf(4, 5, 6, 5, 4),
            listOf(5, 1, 1, 1, 5),
            listOf(6, 1, 1, 1, 6),
            listOf(5, 1, 1, 1, 5),
            listOf(4, 5, 6, 5, 4),
        )
        assertThat(Day11().step(step0))
            .isEqualTo(step1)
        assertThat(Day11().step(step1))
            .isEqualTo(step2)
    }

    @Test
    fun `bigger steps`() {
        val step0 = Day11.parse(
            """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526""".trimIndent().lines()
        )

        val step1 = Day11.parse(
            """
            6594254334
            3856965822
            6375667284
            7252447257
            7468496589
            5278635756
            3287952832
            7993992245
            5957959665
            6394862637""".trimIndent().lines()
        )

        val step2 = Day11.parse(
            """
            8807476555
            5089087054
            8597889608
            8485769600
            8700908800
            6600088989
            6800005943
            0000007456
            9000000876
            8700006848""".trimIndent().lines()
        )
        val step3 = Day11.parse(
            """
            0050900866
            8500800575
            9900000039
            9700000041
            9935080063
            7712300000
            7911250009
            2211130000
            0421125000
            0021119000""".trimIndent().lines()
        )

        assertThat(Day11().step(step0)).isEqualTo(step1)
        assertThat(Day11().step(step1)).isEqualTo(step2)
        assertThat(Day11().step(step2)).isEqualTo(step3)

    }

    @Test
    fun `part 1 sample`() {
        val actual = Day11().part1(Day11.parse(sampleLinesOfDay(11)))
        assertThat(actual)
            .isEqualTo(1656)
    }

    @Test
    fun `part 1`() {
        val actual = Day11().part1(Day11.parse(inputLinesOfDay(11)))
        assertThat(actual)
            .isEqualTo(1773)
    }

    @Test
    fun `part 2 sample`() {
        val actual = Day11().part2(Day11.parse(sampleLinesOfDay(11)))
        assertThat(actual)
            .isEqualTo(42)
    }

}