package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day15Test {

    val SAMPLE1 = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581""".trimIndent().lines()

    @Test
    fun `part1 sample`() {
        assertThat(Day15(Day15.parse(SAMPLE1)).part1())
            .isEqualTo(40)
    }
    @Test
    fun `part1`() {
        assertThat(Day15(Day15.parse(inputLinesOfDay(15))).part1())
            .isEqualTo(720)
    }

    @Test
    fun `part2 sample`() {
        assertThat(Day15(Day15.parse2(SAMPLE1)).part2())
            .isEqualTo(315)
    }

    @Test
    fun `part2`() {
        assertThat(Day15(Day15.parse2(inputLinesOfDay(15))).part2())
            .isEqualTo(3025)
    }

    @Test
    fun `parse2`() {
        assertThat(Day15.parse2(listOf("1")))
            .isEqualTo("""
                12345
                23456
                34567
                45678
                56789
            """.trimIndent().lines().map { it -> it.map { it.toString().toInt() }})

        assertThat(Day15.parse2(listOf("2")))
            .isEqualTo("""
                23456
                34567
                45678
                56789
                67891
            """.trimIndent().lines().map { it -> it.map { it.toString().toInt() }})
    }

    @Test
    fun `parse2 more`() {
        assertThat(Day15.parse2(listOf("13", "57")).take(4))
            .isEqualTo("""
                1324354657
                5768798192
                2435465768
                6879819213
            """.trimIndent().lines().map { it -> it.map { it.toString().toInt() }})
    }

}
