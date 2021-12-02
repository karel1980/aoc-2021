package info.vervaeke

import info.vervaeke.Day2.Companion.REAL_INPUT
import info.vervaeke.Day2.Companion.SAMPLE_INPUT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day2Test {

    @Test
    fun `day2 sample`() {
        assertThat(Day2(SAMPLE_INPUT).part1()).isEqualTo(150)
    }

    @Test
    fun `day2b sample`() {
        assertThat(Day2(SAMPLE_INPUT).part2()).isEqualTo(900)
    }

    @Test
    fun `day2b real deal so I don't fuck up the solution while cleaning up`() {
        assertThat(Day2(REAL_INPUT).part2()).isEqualTo(1903644897)
    }

}