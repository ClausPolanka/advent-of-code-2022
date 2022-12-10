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
    val treesSample = treesFor(lines)
    val treesInput = treesFor(inputLines)
    val sampleResult = part1(treesSample)
    val inputResult = part1(treesInput)
    val sampleResult2 = part2(treesSample)
    val inputResult2 = part2(treesInput)
    // sample => 21, input => 1695
    println("Part 1 Visible trees: '" + sampleResult
        .distinct().size + "'")
    println("Part 1 Visible trees: '" + inputResult
        .distinct().size + "'")
    println()
//    println("Part 2 Visible trees: '" + sampleResult2.distinct().size + "'")
//    println("Part 2 Visible trees: '" + inputResult2
//        .distinct().size + "'")
//
//    sampleResult2
//        .groupBy { it.first }.forEach {
//            println(it.key + " => " + it.value + " => " + it.value.map { it.second }
//                .reduce { acc, i -> acc * i })
//        }
}

private fun part1(trees: List<List<Int>>): List<String> {
    val treesVisibleInRows = trees.flatMapIndexed { rowIndex, _ ->
        rightNeighbours(trees[rowIndex], rowIndex) +
                leftNeighbours(trees[rowIndex], rowIndex)
    }
    val treesVisibleInColumns = trees[0].flatMapIndexed { colIndex, _ ->
        topNeighbours(trees, colIndex) +
                bottomNeighbours(trees, colIndex)
    }
    return treesVisibleInRows + treesVisibleInColumns
}

private fun part2(trees: List<List<Int>>): List<Pair<String, Int>> {
    val treesVisibleInRows = trees.flatMapIndexed { rowIndex, _ ->
        rightNeighboursPart2(trees[rowIndex], rowIndex) +
                leftNeighboursPart2(trees[rowIndex], rowIndex)
    }
    val treesVisibleInColumns = trees[0].flatMapIndexed { colIndex, _ ->
        topNeighboursPart2(trees, colIndex) +
                bottomNeighboursPart2(trees, colIndex)
    }
    return treesVisibleInRows + treesVisibleInColumns
}

private fun treesFor(inputLines: List<String>): List<List<Int>> {
    return inputLines
        .filter { it.isNotEmpty() }
        .map { it.toList().map { tree -> tree.digitToInt() } }
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

private fun rightNeighboursPart2(
    trees: List<Int>,
    index: Int,
    isRow: Boolean = true): List<Pair<String, Int>> {
    return trees.mapIndexed { treeIndex, tree ->
        val neighbours = trees.subList(treeIndex + 1, trees.size)
        val scenicScore: Int =
            scenicScoreFor(tree, neighbours, treeIndex, index)
        if (isRow)
            Pair("$index,$treeIndex", scenicScore)
        else
            Pair("$treeIndex,$index", scenicScore)
    }.filter { it.first.isNotEmpty() }
}

fun scenicScoreFor(tree: Int, neighbours: List<Int>, treeIndex: Int, index: Int): Int {
    if (treeIndex == 0 || index == 0) {
        return 0
    }
    return neighbours.takeWhile { tree >= it }.size
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

private fun leftNeighboursPart2(
    trees: List<Int>,
    index: Int,
    isRow: Boolean = true): List<Pair<String, Int>> {
    return trees.mapIndexed { treeIndex, tree ->
        if (treeIndex == 0) {
            if (isRow) Pair("$index,0", 0) else Pair("0,$index", 0)
        } else {
            val neighbours = trees.subList(0, treeIndex)
            val scenicScore: Int =
                scenicScoreFor(tree, neighbours, treeIndex, index)
            if (isRow)
                Pair("$index,$treeIndex", scenicScore)
            else
                Pair("$treeIndex,$index", scenicScore)
        }
    }.filter { it.first.isNotEmpty() }
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

private fun topNeighboursPart2(trees: List<List<Int>>, colIndex: Int) =
    rightNeighboursPart2(
        trees.map { rows -> rows[colIndex] },
        colIndex,
        isRow = false)

private fun bottomNeighboursPart2(trees: List<List<Int>>, colIndex: Int) =
    leftNeighboursPart2(
        trees.map { rows -> rows[colIndex] },
        colIndex,
        isRow = false)
