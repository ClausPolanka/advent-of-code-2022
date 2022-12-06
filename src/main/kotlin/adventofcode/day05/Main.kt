package adventofcode.day05

import java.io.*

fun main() {
    val input = File("requirements/day05/input.txt").readText()
        .split("${System.lineSeparator()}${System.lineSeparator()}")
    val result = part2(input)
    println(result)
}

private fun part1(input: List<String>): String {
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

    val stacks = stackRows(nrOfStacks, stackLines)
    val moves = input[1].lines()
        .map { it.split(" ") }
        .filter { it.size == 6 }
        .map {
            Move(
                quantity = it[1].toInt(),
                from = it[3].toInt() - 1,
                to = it[5].toInt() - 1)
        }
    moves.forEach { m ->
        repeat(m.quantity) {
            stacks[m.to].add(stacks[m.from].removeLast())
        }
    }
    return stacks.joinToString(separator = "") { it.last() }
}

private fun part2(input: List<String>): String {
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

    val stacks = stackRows(nrOfStacks, stackLines)
    val moves = input[1].lines()
        .map { it.split(" ") }
        .filter { it.size == 6 }
        .map {
            Move(
                quantity = it[1].toInt(),
                from = it[3].toInt() - 1,
                to = it[5].toInt() - 1)
        }
    moves.forEach { m ->
        val toMove = stacks[m.from].takeLast(m.quantity)
        repeat(m.quantity) {
            stacks[m.from].removeLast()
        }
        stacks[m.to].addAll(toMove)
    }

    return stacks
        .filter { it.isNotEmpty() }
        .joinToString(separator = "") { it.last() }
}

private fun stackRows(nrOfStacks: Int, stackLines: List<List<String>>) =
    IntRange(0, nrOfStacks - 1).map { idx ->
        stackLines.map {
            if (idx < it.size) {
                it[idx]
            } else {
                ""
            }
        }
    }.map { it.filter { it.isNotEmpty() } }
        .map { it.toMutableList() }

private fun indicesFor(lineLength: Int): List<Int> {
    val indices = mutableListOf<Int>()
    for (i in 1..lineLength step 4) {
        indices.add(i)
    }
    return indices
}
