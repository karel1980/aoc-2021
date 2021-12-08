package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day9Test {

    @Test
    fun `day9 part 1 sample`() {
        assertThat(Day9(sampleLinesOfDay(9)).part1()).isEqualTo(0)
    }

    @Test
    fun `day9 part 2 sample`() {
        assertThat(Day9(sampleLinesOfDay(9)).part2()).isEqualTo(0)
    }

//    @Test
//    fun `day 9 supporting method`() {
//        assertThat(Day9(sampleLinesOfDay(9)).foo("foo")).isEqualTo("bar")
//    }

    @Test
    fun `day9 part 1`() {
        assertThat(Day9(inputLinesOfDay(9)).part1()).isEqualTo(0)
    }

    @Test
    fun `day9  part 2`() {
        assertThat(Day9(inputLinesOfDay(9)).part2()).isEqualTo(0)
    }

}