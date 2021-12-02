package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day2Test {

    @Test
    fun `day2 sample`() {
        assertThat(Day2(DAY2_SAMPLE).part1()).isEqualTo(150)
    }

    @Test
    fun `day2b sample`() {
        assertThat(Day2(DAY2_SAMPLE).part()).isEqualTo(900)
    }

    @Test
    fun `day2b real deal so I don't fuck up the solution while cleaning up`() {
        assertThat(Day2(DAY2_INPUT).part()).isEqualTo(1903644897)
    }

}