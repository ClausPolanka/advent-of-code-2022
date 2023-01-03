package tests.adventofcode.day12

import adventofcode.day12.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

class Tests {
    @Test
    fun `return cell's neighbours in a 3x3 grid`() {
        val grid = """
            ada
            aSc
            aba
        """.toGrid()
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
        val grid = """
            Sca
            baa
            aaa
        """.toGrid()
        val neighbours = grid.neighboursOf(0, 0)
        assertEquals(listOf(
            Cell(value = "b", row = 1, column = 0, name = "belowNeighbour"),
            Cell(value = "c", row = 0, column = 1, name = "rightNeighbour"),
        ), neighbours, "neighbours")
    }

    @Test
    fun `return bottom left cell's neighbours in a 3x3 grid`() {
        val grid = """
            aaa
            caa
            Sba
        """.toGrid()
        val neighbours = grid.neighboursOf(2, 0)
        assertEquals(listOf(
            Cell(value = "b", row = 2, column = 1, name = "rightNeighbour"),
            Cell(value = "c", row = 1, column = 0, name = "aboveNeighbour"),
        ), neighbours, "neighbours")
    }

    @Test
    fun `return bottom right cell's neighbours in a 3x3 grid`() {
        val grid = """
            aaa
            cac
            abS
        """.toGrid()
        val neighbours = grid.neighboursOf(2, 2)
        assertEquals(listOf(
            Cell(value = "b", row = 2, column = 1, name = "leftNeighbour"),
            Cell(value = "c", row = 1, column = 2, name = "aboveNeighbour"),
        ), neighbours, "neighbours")
    }

    @Test
    fun `return top right cell's neighbours in a 3x3 grid`() {
        val grid = """
            abS
            aac
            aaa
        """.toGrid()
        val neighbours = grid.neighboursOf(0, 2)
        assertEquals(listOf(
            Cell(value = "b", row = 0, column = 1, name = "leftNeighbour"),
            Cell(value = "c", row = 1, column = 2, name = "belowNeighbour"),
        ), neighbours, "neighbours")
    }

    @Test
    fun `return cell's higher or equally high neighbours in a 3x3 grid`() {
        val grid = """
            ada
            aSc
            aba
        """.toGrid()
        val neighbours = grid.nextPossibleStepsFrom(1, 1)
        assertEquals(listOf(
            Cell(value = "a", row = 1, column = 0, name = "leftNeighbour"),
            Cell(value = "b", row = 2, column = 1, name = "belowNeighbour"),
        ), neighbours, "higher or equally high neighbours")
    }

    @Test
    fun `depth first search from S to E with one possible path and two steps`() {
        val grid = """
            zzz
            zSz
            zbE
        """.toGrid()
        val visited = grid.dfs(1, 1)
        assertEquals(listOf(
            Cell(value = "b", row = 2, column = 1, name = "belowNeighbour"),
            Cell(value = "E", row = 2, column = 2, name = "rightNeighbour"),
        ), visited.drop(1), "visited cells")
    }

}
