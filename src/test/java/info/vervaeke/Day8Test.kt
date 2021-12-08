package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day8Test {

    @Test
    fun `day8 part 1 sample`() {
        assertThat(Day8(DAY8_SAMPLE).part1()).isEqualTo(26)
    }

    @Test
    fun `day8 part 2 sample`() {
        assertThat(Day8(DAY8_SAMPLE).part2()).isEqualTo(61229)
    }

    @Test
    fun `day8  countPart1Digits`() {
        val numberOfSegments = Pair(listOf<String>(), listOf("aa"))
        assertThat(Day8(DAY8_SAMPLE).countDigitsWithNumberOfSegmentsIn(numberOfSegments.second, setOf(2, 3, 4, 7))).isEqualTo(1)
    }

    @Test
    fun `day8 buildDigitMap`() {
        val actual = Day8(DAY8_SAMPLE).buildDigitMap(listOf("be", "cfbegad", "cbdgef", "fgaecd", "cgeb", "fdcge", "agebfd", "fecdb", "fabcd", "edb"))
        assertThat(actual)
            .isEqualTo(
                mapOf(
                    Pair("abdefg", 0),
                    Pair("be", 1),
                    Pair("abcdf", 2),
                    Pair("bcdef", 3),
                    Pair("bceg", 4),
                    Pair("cdefg", 5),
                    Pair("acdefg", 6),
                    Pair("bde", 7),
                    Pair("abcdefg", 8),
                    Pair("bcdefg", 9),
                )
            )
    }

    @Test
    fun `day8 part 1`() {
        assertThat(Day8(DAY8_INPUT).part1()).isEqualTo(543)
    }

    @Test
    fun `day8 part 2`() {
        assertThat(Day8(DAY8_INPUT).part2()).isEqualTo(994266)
    }

}