package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day10Test {

    @Test
    fun `part 1 sample`() {
        assertThat(Day10(sampleLinesOfDay(10)).part1()).isEqualTo(26397)
    }

    @Test
    fun `firstIllegalChar`() {
        val given = "[}"
        assertThat(Day10(sampleLinesOfDay(10)).firstInvalidChar(given)).isEqualTo('}')
    }

    @Test
    fun `part 2 sample`() {
        assertThat(Day10(sampleLinesOfDay(10)).part2()).isEqualTo(288957)
    }

//    @Test
//    fun `day 9 supporting method`() {
//        assertThat(Day10(sampleLinesOfDay(10)).foo("foo")).isEqualTo("bar")
//    }

    @Test
    fun `part 1`() {
        assertThat(Day10(inputLinesOfDay(10)).part1()).isEqualTo(266301)
    }

    @Test
    fun ` part 2`() {
        //300253122698 too high
        //3404870164
        assertThat(Day10(inputLinesOfDay(10)).part2()).isEqualTo(0)
    }

}