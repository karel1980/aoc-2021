package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day14Test {

    val SAMPLE1 = """
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C""".trimIndent().lines()

    @Test
    fun `parse`() {
        assertThat(Day14.parse(SAMPLE1).first)
            .isEqualTo("NNCB")
        assertThat(Day14.parse(SAMPLE1).second)
            .containsEntry("CH", "B")
        assertThat(Day14.parse(SAMPLE1).second)
            .containsEntry("CN", "C")
    }

    @Test
    fun `insert`() {
        val sample = Day14.parse(SAMPLE1)
        assertThat(Day14().insert(sample.first, sample.second))
            .isEqualTo("NCNBCHB")
    }

    @Test
    fun `insert again`() {
        val sample = Day14.parse(SAMPLE1)
        assertThat(Day14().insert("NCNBCHB", sample.second))
            .isEqualTo("NBCCNBBBCBHCB")
    }

    @Test
    fun `part1 sample`() {
        val parsed = Day14.parse(SAMPLE1)
        assertThat(Day14().part1(parsed.first, parsed.second))
            .isEqualTo(1588)
    }

    @Test
    fun `part1`() {
        val parsed = Day14.parse(inputLinesOfDay(14))
        assertThat(Day14().part1(parsed.first, parsed.second))
            .isEqualTo(1588)
    }


}
