package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day5Test {

    @Test
    fun `segment`() {
        val p1 = Day5.Coordinate(listOf(2, 0))
        val p2 = Day5.Coordinate(listOf(2, 1))
        val p3 = Day5.Coordinate(listOf(2, 2))
        val p4 = Day5.Coordinate(listOf(2, 3))
        val p5 = Day5.Coordinate(listOf(2, 4))
        val p6 = Day5.Coordinate(listOf(2, 5))

        assertThat(Day5.Segment(p1, p6).orthogonalCells)
            .isEqualTo(listOf(p1, p2, p3, p4, p5, p6))
    }

    @Test
    fun `day5 part 1`() {
        assertThat(Day5(sampleFileOfDay(5)).part1()).isEqualTo(5)
    }

    @Test
    fun `day5 part 2`() {
        assertThat(Day5(sampleFileOfDay(5)).part2()).isEqualTo(12)
    }

}