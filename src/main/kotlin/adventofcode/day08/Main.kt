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

    val trees = inputLines
        .filter { it.isNotEmpty() }
        .map { it.toList().map { it.digitToInt() } }
    trees.forEach {
        println(it)
    }

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

    println("Part 1 Visible trees: '" + result.distinct().size + "'")
    result.distinct().sorted().forEach {
        println("'" + it + "'")
    }
}

private fun rightNeighbours(trees: List<Int>, rowIndex: Int): List<String> {
    return trees.mapIndexed { columnIndex, tree ->
        val neighbours = trees.subList(columnIndex + 1, trees.size)
//        print("tree: '$tree' => $neighbours")
        val isHighest =
            if (neighbours.isEmpty()) true else tree > neighbours.max()
//        println(" Is Highest tree? => $isHighest")
        if (isHighest) "$rowIndex,$columnIndex" else ""
    }.filter { it.isNotEmpty() }
}

private fun rightNeighboursCol(trees: List<Int>, colIndex: Int): List<String> {
    return trees.mapIndexed { rowIndex, tree ->
        val neighbours = trees.subList(rowIndex + 1, trees.size)
//        print("tree: '$tree' => $neighbours")
        val isHighest =
            if (neighbours.isEmpty()) true else tree > neighbours.max()
//        println(" Is Highest tree? => $isHighest")
        if (isHighest) "$rowIndex,$colIndex" else ""
    }.filter { it.isNotEmpty() }
}

private fun leftNeighbours(trees: List<Int>, rowIndex: Int): List<String> {
    return trees.mapIndexed { columnIndex, tree ->
        if (columnIndex == 0) {
//            print("tree: '$tree' => ${emptyList<Int>()}")
//            println(" Is Highest tree? => true")
            "$rowIndex,0"
        } else {
            val neighbours = trees.subList(0, columnIndex)
//            print("tree: '$tree' => $neighbours")
            val isHighest =
                if (neighbours.isEmpty()) true else tree > neighbours.max()
//            println(" Is Highest tree? => $isHighest")
            if (isHighest) "$rowIndex,$columnIndex" else ""
        }
    }.filter { it.isNotEmpty() }
}

private fun leftNeighboursCol(trees: List<Int>, colIndex: Int): List<String> {
    return trees.mapIndexed { rowIndex, tree ->
        if (rowIndex == 0) {
//            print("tree: '$tree' => ${emptyList<Int>()}")
//            println(" Is Highest tree? => true")
            "0,$colIndex"
        } else {
            val neighbours = trees.subList(0, rowIndex)
//            print("tree: '$tree' => $neighbours")
            val isHighest =
                if (neighbours.isEmpty()) true else tree > neighbours.max()
//            println(" Is Highest tree? => $isHighest")
            if (isHighest) "$rowIndex,$colIndex" else ""
        }
    }.filter { it.isNotEmpty() }
}

private fun topNeighbours(trees: List<List<Int>>, colIndex: Int) =
    rightNeighboursCol(trees.map { rows -> rows[colIndex] }, colIndex)

private fun bottomNeighbours(trees: List<List<Int>>, colIndex: Int) =
    leftNeighboursCol(trees.map { rows -> rows[colIndex] }, colIndex)
