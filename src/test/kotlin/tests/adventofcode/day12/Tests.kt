package tests.adventofcode.day12

import adventofcode.day12.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

class Tests {
    @Test
    fun `return cell's neighbours in a 3x3 grid`() {
        val grid: List<List<Char>> = """
            ada
            aac
            aba
        """.trimIndent().toGrid()
        val neighbours = grid.neighboursOf(1, 1)
        assertEquals(listOf(
            Cell(value = "a", row = 1, column = 0, name = "leftNeighbour"),
            Cell(value = "b", row = 2, column = 1, name = "belowNeighbour"),
            Cell(value = "c", row = 1, column = 2, name = "rightNeighbour"),
            Cell(value = "d", row = 0, column = 1, name = "aboveNeighbour"),
        ), neighbours, "neighbours")
    }

    @Test
    fun `return top left cell's neighbours in a 3x3 grid`() {
        val grid: List<List<Char>> = """
            aca
            baa
            aaa
        """.trimIndent().toGrid()
        val neighbours = grid.neighboursOf(0, 0)
        assertEquals(listOf(
            Cell(value = "b", row = 1, column = 0, name = "belowNeighbour"),
            Cell(value = "c", row = 0, column = 1, name = "rightNeighbour"),
        ), neighbours, "neighbours")
    }
}

