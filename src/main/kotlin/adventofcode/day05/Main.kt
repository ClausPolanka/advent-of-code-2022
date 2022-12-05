package adventofcode.day05

import java.io.*

fun main() {
    val input = File("requirements/day05/sample.txt").readText()
        .split("${System.lineSeparator()}${System.lineSeparator()}")
    val stacksOfCrates = input[0]
    val moves = input[1]
    println(stacksOfCrates)
    println(moves)

    val nrOfStacks =
        stacksOfCrates.lines().last().split(" ").filter { it.isNotBlank() }
            .map { it.toInt() }.size

    val stackLines = stacksOfCrates.lines().dropLast(1)
        .map { Pair(it, indicesFor(it.length - 1)) }
        .map { pair ->
            pair.second.map { idx ->
                mutableListOf(pair.first[idx].toString())
            }
        }
        .reversed()

//    println(stackLines)

    val stacks = stackRows(nrOfStacks, stackLines)
    println(stacks)
}

private fun stackRows(nrOfStacks: Int, stackLines: List<List<MutableList<String>>>) =
    IntRange(0, nrOfStacks - 1).map { idx ->
        stackLines.map {
            if (idx < it.size) {
                it[idx]
            } else {
                emptyList()
            }
        }
    }

private fun indicesFor(lineLength: Int): List<Int> {
    val indices = mutableListOf<Int>()
    for (i in 1..lineLength step 4) {
        indices.add(i)
    }
    return indices
}
