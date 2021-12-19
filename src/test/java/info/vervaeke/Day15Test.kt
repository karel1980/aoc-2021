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
            .isEqualTo(40)
    }

}
