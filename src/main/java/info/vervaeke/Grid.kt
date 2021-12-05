package info.vervaeke

abstract class Grid<T>(val rows: List<MutableList<T>>) {
    fun update(row: Int, col: Int, fn: (T) -> T) {
        rows[row][col] = fn(rows[row][col])
    }
}

data class Coordinate(val row: Int, val col: Int)


