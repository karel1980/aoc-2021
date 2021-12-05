package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day6Test {

    @Test
    fun `day6 part 1`() {
        assertThat(Day6(DAY6_SAMPLE).part1()).isEqualTo(42)
    }

    @Test
    fun `day6 part 2`() {
        assertThat(Day6(DAY6_SAMPLE).part2()).isEqualTo(42)
    }

}