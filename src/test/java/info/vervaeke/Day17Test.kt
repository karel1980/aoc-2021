package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day17Test {

    val SAMPLE = "target area: x=20..30, y=-10..-5"

    @Test
    fun `parse`() {
        assertThat(Day17.parse(SAMPLE))
            .isEqualTo(Day17.Coordinate(20, -10) to Day17.Coordinate(30, -5))
    }

    @Test
    fun `part1 sample`() {
        assertThat(Day17().part1(Day17.parse(SAMPLE)))
            .isEqualTo(45)
    }
    @Test
    fun `part1`() {
        assertThat(Day17().part1(Day17.parse(inputLinesOfDay(17).first())))
            .isGreaterThan(5050)
            .isEqualTo(8646)
    }

    @Test
    fun `part2 sample`() {
        assertThat(Day17().part2(Day17.parse(SAMPLE)))
            .isEqualTo(112)
    }

    @Test
    fun `part2`() {
        assertThat(Day17().part2(Day17.parse(inputLinesOfDay(17).first())))
            .isEqualTo(0)
    }

}
