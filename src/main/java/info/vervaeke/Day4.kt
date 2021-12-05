package info.vervaeke

import java.io.File
import java.util.regex.Pattern
import kotlin.text.Charsets.UTF_8

class Day4(val fileName: String) {

    fun part1(): Int {
        val bingoGame = readFile()
        while (!bingoGame.boards.any { it.isWinner() }) {
            bingoGame.drawNext()
        }
        return bingoGame.lastDrawn * bingoGame.boards.first { it .isWinner() }.sumRemaining()
    }

    fun part2(): Int {
        val bingoGame = readFile()
        var lastWinners = listOf<Board>()
        while (!bingoGame.boards.all { it.isWinner() }) {
            lastWinners = bingoGame.drawNext()
        }

        return bingoGame.lastDrawn * lastWinners.first().sumRemaining()
    }

    fun readFile(): BingoGame {
        val lines = File(fileName)
            .readLines(UTF_8)

        val draws = lines.get(0).split(",").map { it.toInt() }
        (draws)
        val boards = lines.drop(1).chunked(6)
            .map { boardLines ->
                Board(boardLines.drop(1)
                    .map { line -> line.trim().split(Pattern.compile(" +")).map { it.toInt() }.toMutableList() })
            }
        return BingoGame(draws, boards)
    }

    class BingoGame(val draws: List<Int>, val boards: List<Board>) {
        var nextDrawPos = 0
        var lastDrawn = -1

        fun drawNext(): List<Board> {
            val previousWinners = winners().toList()

            val drawnValue = draws[nextDrawPos++]
            lastDrawn = drawnValue
            boards.forEach {
                it.mark(drawnValue) }

            return winners().filter {  it !in previousWinners }
        }

        fun winners() = boards.filter { it.isWinner() }
    }

    class Board(val rows: List<MutableList<Int>>) {
        fun mark(value: Int) {
            rows.forEach { row ->
                row.forEachIndexed { index, cellValue ->
                    if (cellValue == value) {
                        row[index] = -1
                    }
                }
            }
        }

        fun isWinner() = hasCompleteRow() || hasCompleteColumn()

        private fun hasCompleteRow() = rows.any { row -> row.all { it == -1 }}

        private fun hasCompleteColumn() = (0 .. rows[0].size - 1).any { colNum ->
            rows.all { row -> row[colNum] == -1 }
        }

        fun sumRemaining(): Int {
            return rows.flatMap { it }
                .filter { it != -1 }
                .sum()
        }
    }

    class Coordinate(val row: Int, val col: Int)
}

fun main() {
    println(Day3(DAY4_INPUT).part1())
    println(Day3(DAY4_INPUT).part2())
}
