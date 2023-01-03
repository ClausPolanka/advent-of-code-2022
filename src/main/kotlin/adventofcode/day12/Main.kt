package adventofcode.day12

import java.io.*
import java.lang.System.lineSeparator

fun main() {
    val input =
        File("requirements/day12/sample.txt").readText()
    val heightMap = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent()
    val grid = heightMap.toGrid()

    val row: Int = 1
    val column: Int = 1
    val neighbours = grid.neighboursOf(1, 1)
}

fun String.toGrid(): List<List<Char>> = trimIndent()
    .split(lineSeparator())
    .map { it.toList() }

fun <E> List<List<E>>.nextPossibleStepsFrom(
    row: Int,
    column: Int
): List<Cell> {
    val cellValue = when (this[row][column].toString()) {
        "S" -> "a"
        else -> this[row][column].toString()
    }
    return neighboursOf(row, column)
        .filter { neighbour ->
            val diff = neighbour.value[0] - cellValue[0]
            diff == 0 || diff == 1
        }
}

fun <E> List<List<E>>.neighboursOf(row: Int, column: Int): List<Cell> {
    var left: Cell? = null
    if (column > 0)
        left = Cell(
            value = this[row][column - 1].toString(),
            row = row,
            column = column - 1,
            name = "leftNeighbour")
    var below: Cell? = null
    if (row < (this.size - 1))
        below = Cell(
            value = this[row + 1][column].toString(),
            row = row + 1,
            column = column,
            name = "belowNeighbour")
    var right: Cell? = null
    if (column < (this.first().size - 1))
        right = Cell(
            value = this[row][column + 1].toString(),
            row = row,
            column = column + 1,
            name = "rightNeighbour")
    var above: Cell? = null
    if (row > 0)
        above = Cell(
            value = this[row - 1][column].toString(),
            row = row - 1,
            column = column,
            name = "aboveNeighbour")
    return listOfNotNull(left, below, right, above)
}

data class Cell(
    val value: String,
    val row: Int,
    val column: Int,
    val name: String)
