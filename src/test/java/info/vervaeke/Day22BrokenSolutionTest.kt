package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day22BrokenSolutionTest {

    val PART1LINES = """
        on x=-40..8,y=-12..40,z=-44..6
        on x=-34..16,y=-49..5,z=-25..23
        on x=-47..3,y=-35..15,z=-38..7
        on x=-7..47,y=-48..2,z=-46..-2
        on x=-31..13,y=-23..23,z=-32..21
        on x=-20..28,y=-9..35,z=-41..11
        on x=-38..15,y=-43..4,z=-22..31
        on x=-38..10,y=-21..33,z=-16..33
        on x=-7..37,y=-25..26,z=-49..0
        on x=-16..33,y=-27..22,z=-8..46
        off x=-29..-12,y=-13..5,z=-17..-3
        on x=-46..5,y=-9..36,z=-48..-4
        off x=-27..-11,y=2..11,z=36..49
        on x=-3..49,y=-26..28,z=-28..19
        off x=31..46,y=-4..10,z=12..30
        on x=-38..15,y=-32..17,z=-4..40
        off x=10..28,y=15..33,z=-49..-33
        on x=-12..34,y=-45..3,z=-45..1
        off x=11..20,y=-46..-34,z=36..49
        on x=-48..3,y=2..49,z=-18..36""".trimIndent().lines()

    @Test
    fun `part 1 mini sample`() {
        val sample = """
            on x=10..12,y=10..12,z=10..12
            on x=11..13,y=11..13,z=11..13
            off x=9..11,y=9..11,z=9..11
            on x=10..10,y=10..10,z=10..10""".trimIndent().lines()

        val instructions = Day22BrokenSolution.parse(sample)

        assertThat(Day22BrokenSolution.part1(instructions))
            .isEqualTo(39)
    }

    @Test
    fun `part 1 mini sample baby steps`() {
        val sample = """
            on x=10..12,y=10..12,z=10..12
            on x=11..13,y=11..13,z=11..13
            off x=9..11,y=9..11,z=9..11
            on x=10..10,y=10..10,z=10..10""".trimIndent().lines()

        val instructions = Day22BrokenSolution.parse(sample)
        val world = Day22BrokenSolution.RangeManager<Day22BrokenSolution.RangeManager<Day22BrokenSolution.RangeManager<Day22BrokenSolution.State>>>()

        val actual = Day22BrokenSolution.applyInstructions(instructions[0], world)

        assertThat(actual)
            .isEqualTo(
                srm(
                    10, 12, srm(
                        10, 12, srm(
                            10, 12, Day22BrokenSolution.State.ON
                        )
                    )
                )
            )

        val step2 = Day22BrokenSolution.applyInstructions(instructions[1], world)

        assertThat(step2)
            .isEqualTo("lol")

    }

    private fun <T> srm(start: Int, end: Int, value: T) = Day22BrokenSolution.RangeManager<T>(
        listOf(Day22BrokenSolution.Range(start, end, value))
    )

    @Test
    fun `range split does not split if splitpoint outside of range`() {
        val range = Day22BrokenSolution.Range(10, 20, "lol")

        assertThat(range.split(3))
            .isEqualTo(listOf(range))
    }


    @Test
    fun `range split splits range in two`() {
        val range = Day22BrokenSolution.Range(10, 20, "lol")

        assertThat(range.split(15))
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 16, "lol"),
                    Day22BrokenSolution.Range(16, 20, "lol")
                )
            )
    }

    @Test
    fun `range split edge cases`() {
        assertThat(Day22BrokenSolution.Range(10, 20, "lol").split(9))
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 20, "lol")
                )
            )

        assertThat(Day22BrokenSolution.Range(10, 20, "lol").split(10))
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 20, "lol"),
                )
            )

        assertThat(Day22BrokenSolution.Range(10, 20, "lol").split(11))
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 11, "lol"),
                    Day22BrokenSolution.Range(11, 20, "lol"),
                )
            )

        assertThat(Day22BrokenSolution.Range(10, 20, "lol").split(18))
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 18, "lol"),
                    Day22BrokenSolution.Range(18, 20, "lol"),
                )
            )

        assertThat(Day22BrokenSolution.Range(10, 20, "lol").split(19))
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 19, "lol"),
                    Day22BrokenSolution.Range(19, 20, "lol"),
                )
            )

        assertThat(Day22BrokenSolution.Range(10, 20, "lol").split(20))
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 20, "lol"),
                )
            )
    }

    @Test
    fun `rangeManager creation`() {
        val mgr = Day22BrokenSolution.RangeManager<String>()

        assertThat(mgr.ranges).isEmpty()
    }

    @Test
    fun `rangeManager applyToRange on empty range`() {
        val mgr = Day22BrokenSolution.RangeManager<String>()

        mgr.applyToRange(10, 20, { "first" })

        assertThat(mgr.ranges)
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 20, "first")
                )
            )
    }

    @Test
    fun `rangeManager applyRange completely before and after`() {
        val mgr = Day22BrokenSolution.RangeManager<String>()

        mgr.applyToRange(100, 110, { "orig" })
        mgr.applyToRange(10, 20, { "before" })
        mgr.applyToRange(200, 210, { "after" })

        assertThat(mgr.ranges)
            .containsAll(
                listOf(
                    Day22BrokenSolution.Range(100, 110, "orig"),
                    Day22BrokenSolution.Range(10, 20, "before"),
                    Day22BrokenSolution.Range(200, 210, "after")
                )
            )
    }

    @Test
    fun `rangeManager applyRange overlap at start`() {
        val mgr = Day22BrokenSolution.RangeManager<String>()

        mgr.applyToRange(10, 20, { "foo" })
        mgr.applyToRange(5, 15, { it + "bar" })

        assertThat(mgr.ranges)
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(5, 10, "nullbar"),
                    Day22BrokenSolution.Range(10, 15, "foobar"),
                    Day22BrokenSolution.Range(15, 20, "foo")
                )
            )
    }

    @Test
    fun `rangeManager applyRange overlap at end`() {
        val mgr = Day22BrokenSolution.RangeManager<String>()

        mgr.applyToRange(10, 20, { "foo" })
        mgr.applyToRange(15, 25, { it + "bar" })

        assertThat(mgr.ranges)
            .isEqualTo(
                listOf(
                    Day22BrokenSolution.Range(10, 15, "foo"),
                    Day22BrokenSolution.Range(15, 20, "foobar"),
                    Day22BrokenSolution.Range(20, 25, "nullbar")
                )
            )
    }

    @Test
    fun `part 1`() {
        val instructions = Day22BrokenSolution.parse(PART1LINES)

        assertThat(Day22BrokenSolution.part1(instructions))
            .isLessThan(648681L)
            .isEqualTo(-1L)
    }


}
