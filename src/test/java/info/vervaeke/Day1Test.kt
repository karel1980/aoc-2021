package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun `day1 sample`() {
        assertThat(Day1(sampleFileOfDay(1)).part1()).isEqualTo(7)
    }

    @Test
    fun `day1b sample`() {
        assertThat(Day1(sampleFileOfDay(1)).part2()).isEqualTo(5)
    }

    @Test
    fun `readFile returns list of ints`() {
        assertThat(Day1(sampleFileOfDay(1)).readFile())
            .isEqualTo(listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263))
    }

}