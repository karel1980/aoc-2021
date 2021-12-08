package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day3Test {

    @Test
    fun `day3 part1 sample`() {
        assertThat(Day3(sampleFileOfDay(3)).part1()).isEqualTo(198)
    }

    @Test
    fun `day3 part 2 sample`() {
        assertThat(Day3(sampleFileOfDay(3)).part2()).isEqualTo(42)
    }

}