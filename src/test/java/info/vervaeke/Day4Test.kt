package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day4Test {

    @Test
    fun `day4 board`() {
        val board = Day4.Board(listOf(
            mutableListOf(1,2,3,4,5),
            mutableListOf(1,2,3,4,5),
            mutableListOf(1,2,3,4,5),
            mutableListOf(1,2,3,4,5),
            mutableListOf(1,2,3,4,5),
        ))

        assertThat(board.isWinner()).isFalse();

        board.mark(1)

        assertThat(board.isWinner()).isTrue();
    }

    @Test
    fun `readFile`() {
        println(Day4(sampleFileOfDay(4)).readFile())
    }

    @Test
    fun `day4 part 1`() {
        assertThat(Day4(sampleFileOfDay(4)).part1()).isEqualTo(4512)
    }

    @Test
    fun `day4 part 2`() {
        assertThat(Day4(sampleFileOfDay(4)).part2()).isEqualTo(1924)
    }

}