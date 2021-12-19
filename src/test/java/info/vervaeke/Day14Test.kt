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
    fun `createPairCounts`() {
        val sample = Day14.parse(SAMPLE1)
        assertThat(Day14().createPairCounts(sample.first))
            .isEqualTo(
                mapOf(
                    "NN" to 1L,
                    "NC" to 1L,
                    "CB" to 1L,
                )
            )
    }

    @Test
    fun `insert2`() {
        val sample = Day14.parse(SAMPLE1)
        assertThat(Day14().insert2(Day14().createPairCounts("NNCB"), sample.second))
            .isEqualTo(
                Day14().createPairCounts("NCNBCHB")
            )
        assertThat(Day14().insert2(Day14().createPairCounts("NCNBCHB"), sample.second))
            .isEqualTo(
                Day14().createPairCounts("NBCCNBBBCBHCB")
            )
        assertThat(Day14().insert2(Day14().createPairCounts("NBCCNBBBCBHCB"), sample.second))
            .isEqualTo(
                Day14().createPairCounts("NBBBCNCCNBBNBNBBCHBHHBCHB")
            )
        assertThat(Day14().insert2(Day14().createPairCounts("NBBBCNCCNBBNBNBBCHBHHBCHB"), sample.second))
            .isEqualTo(
                Day14().createPairCounts("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB")
            )
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
    fun `part2 sample`() {
        val parsed = Day14.parse(SAMPLE1)
        assertThat(Day14().part2(parsed.first, parsed.second))
            .isEqualTo(2188189693529)
    }

    @Test
    fun `part1`() {
        val parsed = Day14.parse(inputLinesOfDay(14))
        assertThat(Day14().part1(parsed.first, parsed.second))
            .isEqualTo(4517)
    }

    @Test
    fun `part2`() {
        val parsed = Day14.parse(inputLinesOfDay(14))
        assertThat(Day14().part2(parsed.first, parsed.second))
            .isEqualTo(1588)
    }


}
