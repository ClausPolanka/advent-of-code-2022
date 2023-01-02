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
    val grid = heightMap.split(lineSeparator())
        .map { it.toList() }

    val x: Int = 1
    val y: Int = 1
    val neighbours = grid.neighboursOf(1, 1)
}

private fun <E> List<E>.neighboursOf(x: Int, y: Int): List<Char> {
    TODO("Not yet implemented")
}
