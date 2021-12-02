package info.vervaeke

import info.vervaeke.Day1.Companion.SAMPLE_INPUT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun `day1 sample`() {
        assertThat(Day1(SAMPLE_INPUT).part1()).isEqualTo(7)
    }

    @Test
    fun `day1b sample`() {
        assertThat(Day1(SAMPLE_INPUT).part2()).isEqualTo(5)
    }

    @Test
    fun `readFile returns list of ints`() {
        assertThat(Day1(SAMPLE_INPUT).readFile())
            .isEqualTo(listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263))
    }

}