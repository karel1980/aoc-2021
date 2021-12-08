package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day8Test {

    @Test
    fun `day8 part 1 sample`() {
        assertThat(Day8(sampleLinesOfDay(8)).part1()).isEqualTo(26)
    }

    @Test
    fun `day8 part 2 sample`() {
        assertThat(Day8(sampleLinesOfDay(8)).part2()).isEqualTo(61229)
    }

    @Test
    fun `day8 buildDigitMap`() {
        val actual = Day8(sampleLinesOfDay(8))
            .buildDigitMap(listOf("be", "cfbegad", "cbdgef", "fgaecd", "cgeb", "fdcge", "agebfd", "fecdb", "fabcd", "edb")
                .map { Day8.Digit(it) })
        assertThat(actual)
            .isEqualTo(
                mapOf(
                    Pair(Day8.Digit("abdefg"), 0),
                    Pair(Day8.Digit("be"), 1),
                    Pair(Day8.Digit("abcdf"), 2),
                    Pair(Day8.Digit("bcdef"), 3),
                    Pair(Day8.Digit("bceg"), 4),
                    Pair(Day8.Digit("cdefg"), 5),
                    Pair(Day8.Digit("acdefg"), 6),
                    Pair(Day8.Digit("bde"), 7),
                    Pair(Day8.Digit("abcdefg"), 8),
                    Pair(Day8.Digit("bcdefg"), 9),
                )
            )
    }

    @Test
    fun `day8 part 1`() {
        assertThat(Day8(inputLinesOfDay(8)).part1()).isEqualTo(543)
    }

    @Test
    fun `day8 part 2`() {
        assertThat(Day8(inputLinesOfDay(8)).part2()).isEqualTo(994266)
    }

}