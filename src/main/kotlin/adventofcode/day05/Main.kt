package adventofcode.day05

import java.io.*

fun main() {
    val input = File("requirements/day05/input.txt").readText()
        .split("${System.lineSeparator()}${System.lineSeparator()}")
    val result1 = part1(input)
    println("Part 1 crates on top of each stack: $result1")
    val result2 = part2(input)
    println("Part 2 crates on top of each stack: $result2")
}

private fun part1(input: List<String>): String {
    val stacks = createStacksFor(input)
    val moves = createMovesFor(input)
    moves.forEach { m ->
        repeat(m.quantity) {
            stacks[m.to].add(stacks[m.from].removeLast())
        }
    }
    return stacks.joinToString(separator = "") { it.last() }
}

private fun part2(input: List<String>): String {
    val stacks = createStacksFor(input)
    val moves = createMovesFor(input)
    moves.forEach { m ->
        val toMove = stacks[m.from].takeLast(m.quantity)
        repeat(m.quantity) { stacks[m.from].removeLast() }
        stacks[m.to].addAll(toMove)
    }
    return stacks
        .filter { it.isNotEmpty() }
        .joinToString(separator = "") { it.last() }
}

private fun createStacksFor(input: List<String>): List<MutableList<String>> {
    val stacksOfCrates = input[0]

    val nrOfStacks = stacksOfCrates.lines()
        .last()
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .size

    val stackLines = stacksOfCrates.lines()
        .dropLast(1)
        .map { Pair(it, indicesFor(it.length - 1)) }
        .map { pair ->
            pair.second.map { idx ->
                val vcont = pair.first[idx].toString()
                vcont.ifBlank { "" }
            }
        }
        .reversed()

    return stackRows(nrOfStacks, stackLines)
}

private fun stackRows(nrOfStacks: Int, stackLines: List<List<String>>) =
    IntRange(0, nrOfStacks - 1)
        .map { idx ->
            stackLines.map {
                when {
                    idx < it.size -> it[idx]
                    else -> ""
                }
            }
        }
        .map { it.filter { it.isNotEmpty() } }
        .map { it.toMutableList() }

private fun indicesFor(lineLength: Int): List<Int> {
    val indices = mutableListOf<Int>()
    for (i in 1..lineLength step 4) {
        indices.add(i)
    }
    return indices
}

private fun createMovesFor(input: List<String>): List<Move> = input[1].lines()
    .map { it.split(" ") }
    .filter { it.size == 6 }
    .map {
        Move(
            quantity = it[1].toInt(),
            from = it[3].toInt() - 1,
            to = it[5].toInt() - 1)
    }
