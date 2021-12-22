package info.vervaeke

import info.vervaeke.Day18.Companion.pair
import info.vervaeke.Day18.Companion.parseNumber
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day18Test {

    @Test
    fun parseNumber() {
        assertThat(parseNumber("[1,2]").toString())
            .isEqualTo(pair(1, 2).toString())
    }

    @Test
    fun parseNumber2() {
        assertThat(parseNumber("[[1,2],3]").toString())
            .isEqualTo(pair(pair(1, 2), 3).toString())
    }

    @Test
    fun add() {
        assertThat(parseNumber("[1,2]").add(parseNumber("[[3,4],5]")).toString())
            .isEqualTo(parseNumber("[[1,2],[[3,4],5]]").toString())
    }

    @Test
    fun `reduce`() {
        assertThat(parseNumber("[[[[[9,8],1],2],3],4]").reduce().toString())
            .isEqualTo(parseNumber("[[[[0,9],2],3],4]").toString())
        assertThat(parseNumber("[7,[6,[5,[4,[3,2]]]]]").reduce().toString())
            .isEqualTo(parseNumber("[7,[6,[5,[7,0]]]]").toString())
        assertThat(parseNumber("[[6,[5,[4,[3,2]]]],1]").reduce().toString())
            .isEqualTo(parseNumber("[[6,[5,[7,0]]],3]").toString())

        assertThat(parseNumber("[[[[4,3],4],4],[7,[[8,4],9]]]").add(pair(1, 1)).toString())
            .isEqualTo(parseNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").toString())
    }

    @Test
    fun `split`() {
        assertThat(parseNumber("[[[[0,7],4],[15,[0,13]]],[1,1]]").split().toString())
            .isEqualTo(parseNumber("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]").toString())
    }

    @Test
    fun `part1 samples`() {
        assertThat((1..4).map { pair(it, it) }.reduce { a, b -> a.add(b) }.toString())
            .isEqualTo(parseNumber("[[[[1,1],[2,2]],[3,3]],[4,4]]").toString())
        assertThat((1..5).map { pair(it, it) }.reduce { a, b -> a.add(b) }.toString())
            .isEqualTo(parseNumber("[[[[3,0],[5,3]],[4,4]],[5,5]]").toString())
        assertThat((1..6).map { pair(it, it) }.reduce { a, b -> a.add(b) }.toString())
            .isEqualTo(parseNumber("[[[[5,0],[7,4]],[5,5]],[6,6]]").toString())
    }

    @Test
    fun `add big example`() {
        val numbers = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]""".trimIndent().lines().map { parseNumber(it) }

        assertThat(numbers.reduce { a,b -> a.add(b) }.toString())
            .isEqualTo("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
    }

    @Test
    fun `part1`() {
        val numbers = inputLinesOfDay(18).map { Day18.parseNumber(it) }

        assertThat(Day18.part1(numbers))
            .isEqualTo(3734)
    }

    @Test
    fun `part2 sample`() {
        val numbers = """
                [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                [[[5,[2,8]],4],[5,[[9,9],0]]]
                [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                [[[[5,4],[7,7]],8],[[8,3],8]]
                [[9,3],[[9,9],[6,[4,9]]]]
                [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]""".trimIndent().lines()

        assertThat(Day18.part2(numbers))
            .isEqualTo(3993)
    }

    @Test
    fun `part2`() {
        val numbers = inputLinesOfDay(18)

        assertThat(Day18.part2(numbers))
            .isGreaterThan(4536)
            .isLessThan(4875)
            .isEqualTo(0)
    }

}
