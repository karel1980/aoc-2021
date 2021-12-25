package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger


class Day21Test {

    @Test
    fun playOneRound() {
        val state = Day21.LiveGame(listOf(0, 0), listOf(4, 8))

        val actual = Day21.playOneRound(mapOf(state to BigInteger.ONE), 0)

        assertThat(actual).isEqualTo(
            mapOf(
                Day21.LiveGame(scores = listOf(7, 0), positions = listOf(7, 8)) to BigInteger.valueOf(1),
                Day21.LiveGame(scores = listOf(8, 0), positions = listOf(8, 8)) to BigInteger.valueOf(3),
                Day21.LiveGame(scores = listOf(9, 0), positions = listOf(9, 8)) to BigInteger.valueOf(6),
                Day21.LiveGame(scores = listOf(10, 0), positions = listOf(10, 8)) to BigInteger.valueOf(7),
                Day21.LiveGame(scores = listOf(1, 0), positions = listOf(1, 8)) to BigInteger.valueOf(6),
                Day21.LiveGame(scores = listOf(2, 0), positions = listOf(2, 8)) to BigInteger.valueOf(3),
                Day21.LiveGame(scores = listOf(3, 0), positions = listOf(3, 8)) to BigInteger.valueOf(1)
            )
        )

        assertThat(actual.values.sumOf { it })
            .isEqualTo(BigInteger.valueOf(27))
    }

   @Test
    fun `sanity check sum of big integers`() {
       val baseAmount = BigInteger("123456789123456789123456789")
       val state = mapOf<Day21.GameState, BigInteger>(Day21.LiveGame(listOf(0, 0), listOf(4, 8)) to baseAmount)

        val actual = Day21.applyRoll(0, state)

        assertThat(actual).isEqualTo(
            mapOf(
                Day21.LiveGame(scores = listOf(7, 0), positions = listOf(7, 8)) to BigInteger.valueOf(1).multiply(baseAmount),
                Day21.LiveGame(scores = listOf(8, 0), positions = listOf(8, 8)) to BigInteger.valueOf(3).multiply(baseAmount),
                Day21.LiveGame(scores = listOf(9, 0), positions = listOf(9, 8)) to BigInteger.valueOf(6).multiply(baseAmount),
                Day21.LiveGame(scores = listOf(10, 0), positions = listOf(10, 8)) to BigInteger.valueOf(7).multiply(baseAmount),
                Day21.LiveGame(scores = listOf(1, 0), positions = listOf(1, 8)) to BigInteger.valueOf(6).multiply(baseAmount),
                Day21.LiveGame(scores = listOf(2, 0), positions = listOf(2, 8)) to BigInteger.valueOf(3).multiply(baseAmount),
                Day21.LiveGame(scores = listOf(3, 0), positions = listOf(3, 8)) to BigInteger.valueOf(1).multiply(baseAmount)
            )
        )

        assertThat(actual.values.sumOf { it })
            .isEqualTo(baseAmount.multiply(BigInteger.valueOf(27)))
    }


    @Test
    fun `playOneRound increments scores`() {
        val state = Day21.LiveGame(listOf(10, 20), listOf(4, 8))

        val actual = Day21.playOneRound(mapOf(state to BigInteger.ONE), 0)

        assertThat(actual).isEqualTo(
            mapOf(
                Day21.LiveGame(scores = listOf(17, 20), positions = listOf(7, 8)) to BigInteger.valueOf(1),
                Day21.LiveGame(scores = listOf(18, 20), positions = listOf(8, 8)) to BigInteger.valueOf(3),
                Day21.LiveGame(scores = listOf(19, 20), positions = listOf(9, 8)) to BigInteger.valueOf(6),
                Day21.LiveGame(scores = listOf(20, 20), positions = listOf(10, 8)) to BigInteger.valueOf(7),
                Day21.LiveGame(scores = listOf(11, 20), positions = listOf(1, 8)) to BigInteger.valueOf(6),
                Day21.LiveGame(scores = listOf(12, 20), positions = listOf(2, 8)) to BigInteger.valueOf(3),
                Day21.LiveGame(scores = listOf(13, 20), positions = listOf(3, 8)) to BigInteger.valueOf(1)
            )
        )
    }

    @Test
    fun `playOneRound starting with count gt 1`() {
        val state = Day21.LiveGame(listOf(0, 0), listOf(4, 8))

        val actual = Day21.playOneRound(mapOf(state to BigInteger.TEN), 0)

        assertThat(actual).isEqualTo(
            mapOf(
                Day21.LiveGame(scores = listOf(7, 0), positions = listOf(7, 8)) to BigInteger.valueOf(10),
                Day21.LiveGame(scores = listOf(8, 0), positions = listOf(8, 8)) to BigInteger.valueOf(30),
                Day21.LiveGame(scores = listOf(9, 0), positions = listOf(9, 8)) to BigInteger.valueOf(60),
                Day21.LiveGame(scores = listOf(10, 0), positions = listOf(10, 8)) to BigInteger.valueOf(70),
                Day21.LiveGame(scores = listOf(1, 0), positions = listOf(1, 8)) to BigInteger.valueOf(60),
                Day21.LiveGame(scores = listOf(2, 0), positions = listOf(2, 8)) to BigInteger.valueOf(30),
                Day21.LiveGame(scores = listOf(3, 0), positions = listOf(3, 8)) to BigInteger.valueOf(10)
            )
        )
    }

   @Test
    fun `playOneRound preserves winners`() {
        val state = Day21.LiveGame(listOf(0, 0), listOf(4, 8))

        val actual = Day21.playOneRound(mapOf(
            state to BigInteger.TEN,
            Day21.WonGameState(1) to BigInteger.valueOf(123)
        ), 0)

        assertThat(actual).isEqualTo(
            mapOf(
                Day21.LiveGame(scores = listOf(7, 0), positions = listOf(7, 8)) to BigInteger.valueOf(10),
                Day21.LiveGame(scores = listOf(8, 0), positions = listOf(8, 8)) to BigInteger.valueOf(30),
                Day21.LiveGame(scores = listOf(9, 0), positions = listOf(9, 8)) to BigInteger.valueOf(60),
                Day21.LiveGame(scores = listOf(10, 0), positions = listOf(10, 8)) to BigInteger.valueOf(70),
                Day21.LiveGame(scores = listOf(1, 0), positions = listOf(1, 8)) to BigInteger.valueOf(60),
                Day21.LiveGame(scores = listOf(2, 0), positions = listOf(2, 8)) to BigInteger.valueOf(30),
                Day21.LiveGame(scores = listOf(3, 0), positions = listOf(3, 8)) to BigInteger.valueOf(10),
                Day21.WonGameState(1) to BigInteger.valueOf(123)
            )
        )
    }

    @Test
    fun `playOneRound adds winners`() {
        val state = Day21.LiveGame(listOf(100, 0), listOf(4, 8))

        val actual = Day21.playOneRound(mapOf(
            state to BigInteger.TEN,
            Day21.WonGameState(0) to BigInteger.valueOf(11),
            Day21.WonGameState(1) to BigInteger.valueOf(123)
        ), 0)

        assertThat(actual).isEqualTo(
            mapOf(
                Day21.WonGameState(1) to BigInteger.valueOf(123),
                Day21.WonGameState(0) to BigInteger.valueOf(281),
            )
        )
    }

    @Test
    fun `playOneRound combines states`() {

        val actual = Day21.playOneRound(mapOf(
            Day21.LiveGame(listOf(10, 20), listOf(4, 8)) to BigInteger.TEN,
            Day21.LiveGame(listOf(10, 20), listOf(1, 8)) to BigInteger.TEN,
        ), 0)

        assertThat(actual).isEqualTo(
            mapOf(
                Day21.LiveGame(scores = listOf(17, 20), positions = listOf(7, 8)) to BigInteger.valueOf(10 + 70),
                Day21.LiveGame(scores = listOf(18, 20), positions = listOf(8, 8)) to BigInteger.valueOf(30 + 60),
                Day21.LiveGame(scores = listOf(19, 20), positions = listOf(9, 8)) to BigInteger.valueOf(60 + 30),
                Day21.LiveGame(scores = listOf(20, 20), positions = listOf(10, 8)) to BigInteger.valueOf(70 + 10),
                Day21.LiveGame(scores = listOf(11, 20), positions = listOf(1, 8)) to BigInteger.valueOf(60),
                Day21.LiveGame(scores = listOf(12, 20), positions = listOf(2, 8)) to BigInteger.valueOf(30),
                Day21.LiveGame(scores = listOf(13, 20), positions = listOf(3, 8)) to BigInteger.valueOf(10),

                Day21.LiveGame(scores = listOf(14, 20), positions = listOf(4, 8)) to BigInteger.valueOf(10),
                Day21.LiveGame(scores = listOf(15, 20), positions = listOf(5, 8)) to BigInteger.valueOf(30),
                Day21.LiveGame(scores = listOf(16, 20), positions = listOf(6, 8)) to BigInteger.valueOf(60),
            )
        )
    }


//    @Test
//    fun part1() {
//        assertThat(Day21.part1(8, 9)).isEqualTo(100)
//    }

    @Test
    fun `part2 sample`() {
        assertThat(Day21.part2(4, 8)).isEqualTo(444356092776315)
    }

    @Test
    fun `part2`() {
        assertThat(Day21.part2(8, 9)).isEqualTo(100)
    }

    @Test
    fun `sanity check list of pair to map`() {
        assertThat(listOf(1 to 5, 1 to 6).toMap())
            .isEqualTo(mapOf(1 to 6))
    }

}
