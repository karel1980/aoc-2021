package info.vervaeke

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.Stack


class Day23Test {

    val PART1_EXAMPLE = "BA,CD,BC,DA"
    val PART1_INPUT = "BC,DD,CB,AA"
    val PART2_EXAMPLE = "BDDA,CCBD,BBAC,DACA"
    val PART2_INPUT = "BDDC,DCBD,CBAB,AACA"

    @Test
    fun `parse`() {
        assertThat(Day23.parse(PART2_INPUT).toString())
            .isEqualTo("""
#############
#...........#
###B#D#C#A###
  #D#C#B#A#
  #D#B#A#C#
  #C#D#B#A#""")
    }

    @Test
    fun `part1 example`() {
        val world = Day23.parse(PART1_EXAMPLE)

        println(world)

        assertThat(Day23.part1(world))
            .isEqualTo(12521L)
    }

    @Test
    fun `part1`() {
        val world = Day23.parse(PART1_INPUT)

        println(world)

        assertThat(Day23.part1(world))
            .isEqualTo(15322L)
    }

    @Test
    fun `calculateMoveCost`() {
        val world = Day23.parse(PART1_EXAMPLE)

        assertThat(Day23.calculateMoveCost(Day23.Move(Day23.Direction.TO_ROOM, 0, "A", "A"), world.rooms["A"]!!))
            .isEqualTo(2)
        assertThat(Day23.calculateMoveCost(Day23.Move(Day23.Direction.TO_HALL, 0, "A", "A"), world.rooms["A"]!!))
            .isEqualTo(3)

        world.applyMove(Day23.Move(Day23.Direction.TO_HALL, 0, "A", "X"))

        assertThat(Day23.calculateMoveCost(Day23.Move(Day23.Direction.TO_ROOM, 0, "A", "A"), world.rooms["A"]!!))
            .isEqualTo(3)
        assertThat(Day23.calculateMoveCost(Day23.Move(Day23.Direction.TO_HALL, 0, "A", "A"), world.rooms["A"]!!))
            .isEqualTo(4)

    }

    @Test
    fun `part2 example`() {
        assertThat(Day23.part2(Day23.parse(PART2_EXAMPLE)))
            .isEqualTo(44169)
    }

    @Test
    fun `part2`() {
        assertThat(Day23.part2(Day23.parse(PART2_INPUT)))
            .isGreaterThan(46023L) // accidentally used move.room instead of move.pod to multiply by 1,10,100 or 1000
            .isLessThan(56364L) // too high. what now?
            .isEqualTo(42)
    }

    @Test
    fun `applyMove happy path`() {
        val world = Day23.parse(PART2_INPUT)

        world.applyMove(Day23.Move(Day23.Direction.TO_HALL, 0, world.rooms["A"]!!.pods.peek(), "A"))

        println(world)
    }

    @Test
    fun `findPossibleMoves should not move a pod out of its target room if there are no other pods left in the room`() {
        val world = testWorld(".D.DAC.", "A", "", "", "")

        println(world)
        val actual = world.findPossibleMoves()
        println(actual)
        assertThat(actual)
            .isEmpty()
    }

    @Test
    fun `findPossibleMoves another bug`(){
        val world = testWorld("BDD.CBB", "C", "BB", "BCC", "ADDD")

        assertThat(world.findPossibleMoves())
            .containsExactly(Day23.Move(Day23.Direction.TO_HALL, 3, "B", "C"))
    }

    fun testWorld(hall: String, roomA: String, roomB: String, roomC: String, roomD: String): Day23.World {
        val hall = hall.map { if (it=='.') null else it.toString() }.toMutableList()
        val rooms = rooms(roomA, roomB, roomC, roomD)
        return Day23.World(hall, rooms)
    }

    private fun rooms(
        roomA: String,
        roomB: String,
        roomC: String,
        roomD: String
    ) = listOf(
        toRoom("A", roomA),
        toRoom("B", roomB),
        toRoom("C", roomC),
        toRoom("D", roomD),
    ).map { it.id to it }.toMap()

    fun toRoom(id: String, pods: String): Day23.Room {
        val result = Stack<String>()
        pods.reversed().forEach { result.push(it.toString()) }
        return Day23.Room(id, result)
    }

}
