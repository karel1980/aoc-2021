package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day6Test {

    @Test
    fun `day6 part 1 sample`() {
        assertThat(Day6(sampleLinesOfDay(6)).part1()).isEqualTo(5934)
    }

    @Test
    fun `day6 part 2 sample`() {
        assertThat(Day6(sampleLinesOfDay(6)).part2()).isEqualTo(26984457539L)
    }

    @Test
    fun `day6 part 1`() {
        assertThat(Day6(inputLinesOfDay(6)).part1()).isEqualTo(374994L)
    }

    @Test
    fun `day6 part 2`() {
        assertThat(Day6(inputLinesOfDay(6)).part2()).isEqualTo(1686252324092L)
    }

}