package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day12Test {

    val SAMPLE1 = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end""".trimIndent().lines()

    val SAMPLE2 = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc""".trimIndent().lines()



    @Test
    fun `parse`() {
        assertThat(Day12.parse(SAMPLE1))
            .isEqualTo(
                mapOf(
                    "start" to setOf("A", "b"),
                    "A" to setOf("c", "start", "b", "end"),
                    "b" to setOf("start", "A", "d", "end"),
                    "c" to setOf("A"),
                    "d" to setOf("b"),
                    "end" to setOf("A", "b"),
                )
            )
    }

    @Test
    fun `part 1 sample`() {
        assertThat(
            Day12().part1(SAMPLE2)
        )
            .isEqualTo(19)
    }

    @Test
    fun `findDistinctPaths`() {
        val actual = Day12().findDistinctPaths(Day12.parse(SAMPLE1))
        val expected = """
            start,A,b,A,c,A,end
            start,A,b,A,end
            start,A,b,end
            start,A,c,A,b,A,end
            start,A,c,A,b,end
            start,A,c,A,end
            start,A,end
            start,b,A,c,A,end
            start,b,A,end
            start,b,end""".trimIndent().lines().map {
            it.split(",")
        }.toSet()

        assertThat(actual)
            .isEqualTo(
                expected
            )
    }

    @Test
    fun `part 1`() {
        val actual = Day12().part1(inputLinesOfDay(12))
        assertThat(actual)
            .isEqualTo(3856)
    }

    @Test
    fun `part 2 sample`() {
        val actual = Day12().part2(SAMPLE1)
        assertThat(actual)
            .isEqualTo(36)
    }

    @Test
    fun `part 2`() {
        val actual = Day12().part2(inputLinesOfDay(12))
        assertThat(actual)
            .isEqualTo(116692)
    }

    @Test
    fun `findDistinctPaths2`() {
        val actual: List<String> = Day12().findDistinctPaths2(Day12.parse(SAMPLE1))
            .map { it.joinToString(",") }
            .sorted()

        assertThat(actual)
            .isEqualTo("""
                start,A,b,A,b,A,c,A,end
                start,A,b,A,b,A,end
                start,A,b,A,b,end
                start,A,b,A,c,A,b,A,end
                start,A,b,A,c,A,b,end
                start,A,b,A,c,A,c,A,end
                start,A,b,A,c,A,end
                start,A,b,A,end
                start,A,b,d,b,A,c,A,end
                start,A,b,d,b,A,end
                start,A,b,d,b,end
                start,A,b,end
                start,A,c,A,b,A,b,A,end
                start,A,c,A,b,A,b,end
                start,A,c,A,b,A,c,A,end
                start,A,c,A,b,A,end
                start,A,c,A,b,d,b,A,end
                start,A,c,A,b,d,b,end
                start,A,c,A,b,end
                start,A,c,A,c,A,b,A,end
                start,A,c,A,c,A,b,end
                start,A,c,A,c,A,end
                start,A,c,A,end
                start,A,end
                start,b,A,b,A,c,A,end
                start,b,A,b,A,end
                start,b,A,b,end
                start,b,A,c,A,b,A,end
                start,b,A,c,A,b,end
                start,b,A,c,A,c,A,end
                start,b,A,c,A,end
                start,b,A,end
                start,b,d,b,A,c,A,end
                start,b,d,b,A,end
                start,b,d,b,end
                start,b,end
            """.trimIndent().lines().sortedBy { it.toString() })
    }


}