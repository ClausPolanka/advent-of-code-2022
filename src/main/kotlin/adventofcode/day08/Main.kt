package adventofcode.day08

import java.io.*

fun main() {
    val sampleInput =
        """
        30373
        25512
        65332
        33549
        35390""".trimIndent()
    val lines = sampleInput.split(System.lineSeparator())

    val inputLines =
        File("requirements/day08/input.txt").useLines { it.toList() }

    val sampleResult = part1(lines)
    val inputResult = part1(inputLines)

    // sample => 21, input => 1695
    println("Part 1 Visible trees: '" + sampleResult.distinct().size + "'")
    println("Part 1 Visible trees: '" + inputResult.distinct().size + "'")
}

private fun part1(inputLines: List<String>): MutableList<String> {
    val trees = inputLines
        .filter { it.isNotEmpty() }
        .map { it.toList().map { tree -> tree.digitToInt() } }

    val result = mutableListOf<String>()

    trees.forEachIndexed { rowIndex, _ ->
        val reightNeighbours = rightNeighbours(trees[rowIndex], rowIndex)
        result.addAll(reightNeighbours)
        val leftNeighbours = leftNeighbours(trees[rowIndex], rowIndex)
        result.addAll(leftNeighbours)
    }

    trees[0].forEachIndexed { colIndex, _ ->
        val topNeighbours = topNeighbours(trees, colIndex)
        result.addAll(topNeighbours)
        val bottomNeighbours = bottomNeighbours(trees, colIndex)
        result.addAll(bottomNeighbours)
    }
    return result
}

private fun rightNeighbours(
    trees: List<Int>,
    index: Int,
    isRow: Boolean = true): List<String> {
    return trees.mapIndexed { treeIndex, tree ->
        val neighbours = trees.subList(treeIndex + 1, trees.size)
        val isHighest =
            if (neighbours.isEmpty()) true else tree > neighbours.max()
        if (isHighest) {
            if (isRow) "$index,$treeIndex" else "$treeIndex,$index"
        } else ""
    }.filter { it.isNotEmpty() }
}

private fun leftNeighbours(
    trees: List<Int>,
    index: Int,
    isRow: Boolean = true): List<String> {
    return trees.mapIndexed { treeIndex, tree ->
        if (treeIndex == 0) {
            if (isRow) "$index,0" else "0,$index"
        } else {
            val neighbours = trees.subList(0, treeIndex)
            val isHighest =
                if (neighbours.isEmpty()) true else tree > neighbours.max()
            if (isHighest) {
                if (isRow) "$index,$treeIndex" else "$treeIndex,$index"
            } else ""
        }
    }.filter { it.isNotEmpty() }
}

private fun topNeighbours(trees: List<List<Int>>, colIndex: Int) =
    rightNeighbours(
        trees.map { rows -> rows[colIndex] },
        colIndex,
        isRow = false)

private fun bottomNeighbours(trees: List<List<Int>>, colIndex: Int) =
    leftNeighbours(
        trees.map { rows -> rows[colIndex] },
        colIndex,
        isRow = false)
