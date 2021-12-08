package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day6Test {

    @Test
    fun `day6 part 1`() {
        assertThat(Day6(sampleFileOfDay(6)).part1()).isEqualTo(5934)
    }
    @Test
    fun `day6 iterate`() {
//        assertThat(Day6(DAY6_SAMPLE).iteration2(listOf(3,4,3,1,2))).isEqualTo(listOf(2,3,2,0,1))
//        assertThat(Day6(DAY6_SAMPLE).iteration2(listOf(2,3,2,0,1))).isEqualTo(listOf(1,2,1,6,0,8))
    }

    @Test
    fun `day6 part 2`() {
        assertThat(Day6(sampleFileOfDay(6)).part2()).isEqualTo(26984457539L)
    }

}