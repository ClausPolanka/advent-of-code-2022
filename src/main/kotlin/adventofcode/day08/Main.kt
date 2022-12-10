package adventofcode.day08

fun main() {
    val sampleInput =
        """
        30373
        25512
        65332
        33549
        35390""".trimIndent()

    val lines = sampleInput.split(System.lineSeparator())
    val trees = lines.map { it.toList().map { it.digitToInt() } }
    trees.forEach {
        println(it)
    }
    /**
     * [3, 0, 3, 7, 3]
     * [2, 5, 5, 1, 2]
     * [6, 5, 3, 3, 2]
     * [3, 3, 5, 4, 9]
     * [3, 5, 3, 9, 0]
     */

    val rowLength = trees[0].size
    val colLength = trees.size
    val rowIndex = 1
    val colIndex = 1

    println()
    println("right neighbours for row: $rowIndex")
    rightNeighbours(trees[rowIndex])

    println()
    println("left neighbours for row: $rowIndex")
    leftNeighbours(trees[rowIndex])

    println()
    println("top neighbours for column: $colIndex")
    rightNeighbours(trees.map { rows -> rows[colIndex] })

    println()
    println("bottom neighbours for column: $colIndex")
    leftNeighbours(trees.map { rows -> rows[colIndex] })
}

private fun rightNeighbours(trees: List<Int>) {
    trees.forEachIndexed { index, tree ->
        println("tree: '$tree' => ${trees.subList(index + 1, trees.size)}")
    }
}

private fun leftNeighbours(trees: List<Int>) {
    trees.forEachIndexed { index, tree ->
        if (index == 0) {
            println("tree: '$tree' => ${emptyList<Int>()}")
        } else {
            println("tree: '$tree' => ${trees.subList(0, index)}")
        }
    }
}
