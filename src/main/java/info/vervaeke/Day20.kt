package info.vervaeke

class Day20 {

    companion object {
        fun parse(lines: List<String>): Pair<String, List<String>> {
            return lines[0] to lines.drop(2)
        }

        fun part1(algorithm: String, grid: List<String>): Int{
            val padded = addPadding(grid, 5)
            println("grid\n${grid.joinToString("\n")}")

            val step1 = enhance(algorithm, padded)
            println("step1\n${step1.joinToString("\n")}")

            val step2 = enhance(algorithm, step1)
            println("step2\n${step2.joinToString("\n")}")

            return step2.sumOf { line -> line.count { it == '#' }}
        }

        fun part2(algorithm: String, grid: List<String>): Int{
            val padded = addPadding(grid, 50 * 3)

            var enhanced = padded
            (0 until 50).forEach {
                enhanced = enhance(algorithm, enhanced)
            }

            return enhanced.sumOf { line -> line.count { it == '#' }}
        }

        private fun addPadding(grid: List<String>, amount: Int): List<String> {
            val emptyLine = ".".repeat(grid.first().length + (4 * amount))
            val padRows = (0 until amount).map { emptyLine }
            return padRows + grid.map { "${"..".repeat(amount)}$it${"..".repeat(amount)}" } + padRows
        }

        private fun enhance(algorithm: String, grid: List<String>): List<String> {
            val result = mutableListOf<String>()
//            println(grid.joinToString("\n"))
            (0 until grid.size-2).map { rowNum ->
                var row = ""
                (0 until grid[rowNum].length-2).map { colNum ->
                    val pattern = grid[rowNum].substring(colNum, colNum+3) + grid[rowNum + 1].substring(colNum, colNum+3) + grid[rowNum + 2].substring(colNum, colNum+3)

                    val num = pattern.replace(".", "0").replace("#", "1").toInt(2)
                    row += algorithm[num]
                }
                result += row
            }

//            println(result.joinToString("\n"))
            return result.toList()
        }
    }

}

