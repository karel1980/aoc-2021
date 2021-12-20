package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day16Test {


    @Test
    fun `toBitString`() {
        assertThat(Day16.toBitString("D2FE28"))
            .isEqualTo("110100101111111000101000")
    }

    @Test
    fun `parse literal packet`() {
        assertThat(Day16.parse("D2FE28"))
            .isEqualTo(Day16.LiteralPacket(6, 4, 2021, 21))
    }

    @Test
    fun `parse operator packet`() {
        assertThat(Day16.parse("38006F45291200"))
            .isEqualTo(Day16.OperatorPacket(1, 6, listOf(
                Day16.LiteralPacket(6, 4, 10, 11), Day16.LiteralPacket(2,4, 20, 16)
            ), 27))
    }

    @Test
    fun `parse another operator packet`() {
        assertThat(Day16.parse("EE00D40C823060"))
            .isEqualTo(Day16.OperatorPacket(7, 3, listOf(
                Day16.LiteralPacket(2,4, 1, 11),
                Day16.LiteralPacket(4,4, 2, 11),
                Day16.LiteralPacket(1,4, 3, 11),
            ), 51))
    }

    @Test
    fun `part 1 samples`() {
        assertThat(Day16.parse("8A004A801A8002F478").versionSum())
            .isEqualTo(16)
        assertThat(Day16.parse("620080001611562C8802118E34").versionSum())
            .isEqualTo(12)
        assertThat(Day16.parse("C0015000016115A2E0802F182340").versionSum())
            .isEqualTo(23)
        assertThat(Day16.parse("A0016C880162017C3686B18A3D4780").versionSum())
            .isEqualTo(31)
    }

    @Test
    fun `part 1`() {
        assertThat(Day16.parse(inputLinesOfDay(16).get(0)).versionSum())
            .isEqualTo(991)
    }

    @Test
    fun `parseLong`() {
        println("11111111100101001100010111000100".toLong(2))
    }
}
