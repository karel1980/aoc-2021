package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day20Test {

    @Test
    fun parse() {
        val puzzleInput = Day20.parse(sampleLinesOfDay(20))
        println(puzzleInput)
    }

    @Test
    fun `part1 sample`() {
        val puzzleInput = Day20.parse(sampleLinesOfDay(20))

        assertThat(Day20.part1(puzzleInput.first, puzzleInput.second))
            .isEqualTo(35)
    }

    @Test
    fun `part1`() {
        val puzzleInput = Day20.parse(inputLinesOfDay(20))

        assertThat(Day20.part1(puzzleInput.first, puzzleInput.second))
            .isEqualTo(5682)
    }

    @Test
    fun `part2`() {
        val puzzleInput = Day20.parse(inputLinesOfDay(20))

        assertThat(Day20.part2(puzzleInput.first, puzzleInput.second))
            .isEqualTo(5682)
    }



}
