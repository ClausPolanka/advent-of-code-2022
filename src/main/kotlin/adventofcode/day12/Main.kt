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

fun String.toGrid(): List<List<Char>> = split(lineSeparator())
    .map { it.toList() }

fun <E> List<List<E>>.neighboursOf(row: Int, column: Int): List<Cell> {
    val leftNeighbour = Cell(
        value = this[row][column - 1].toString(),
        row = row,
        column = column - 1,
        name = "leftNeighbour")
    val belowNeighbour = Cell(
        value = this[row + 1][column].toString(),
        row = row + 1,
        column = column,
        name = "belowNeighbour")
    val rightNeighbour = Cell(
        value = this[row][column + 1].toString(),
        row = row,
        column = column + 1,
        name = "rightNeighbour")
    val aboveNeighbour = Cell(
        value = this[row - 1][column].toString(),
        row = row - 1,
        column = column,
        name = "aboveNeighbour")
    return listOf(
        leftNeighbour,
        belowNeighbour,
        rightNeighbour,
        aboveNeighbour)
}

data class Cell(val value: String, val row: Int, val column: Int,
                val name: String)
