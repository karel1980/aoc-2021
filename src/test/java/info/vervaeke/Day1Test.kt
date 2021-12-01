package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

const val SAMPLE_FILE = "p1.sample.txt"

class Day1Test {

    @Test
    fun `day1 sample`() {
        assertThat(day1(SAMPLE_FILE)).isEqualTo(7)
    }

    @Test
    fun `day1b sample`() {
        assertThat(day1b(SAMPLE_FILE)).isEqualTo(5)
    }

    @Test
    fun `readFile returns list of ints`() {
        assertThat(readFile(SAMPLE_FILE))
            .isEqualTo(listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263))
    }

}