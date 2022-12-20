package adventofcode.day09

import java.io.*
import kotlin.math.*

var head = Pair(0, 0)
var tail = Pair(0, 0)

fun main() {
    val sampleFileCommands =
        File("requirements/day09/sample.txt").useLines { it.toList() }

    val inputFileCommands =
        File("requirements/day09/input.txt").useLines { it.toList() }

    val inMemoryCommands = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()
        .split(System.lineSeparator())

    val commands = inputFileCommands
        .map { row -> row.split(" ") }

    commands.forEach { cmd ->
        println(cmd)
        when (cmd.first()) {
            "R" -> moveRight(cmd.last().toInt())
            "L" -> moveLeft(cmd.last().toInt())
            "U" -> moveUp(cmd.last().toInt())
            "D" -> moveDown(cmd.last().toInt())
        }
    }

    println()
    println("How many positions does the tail of the rope visit at least once?")
    println(visited.size) // Part 1: 5683 (input) Part 1: 13 (sample)
}

private fun moveRight(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    println("Move R")
    repeat(moves) {
        print("H: $head")
        head = Pair(head.first, head.second + 1)
        println(" => $head")
        print("T: $tail")
        tail = updateTail(tail, head)
        visited.add(tail)
        println(" => $tail")
    }
    return Pair(head, tail)
}

private fun moveLeft(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    println("Move L")
    repeat(moves) {
        print("H: $head")
        head = Pair(head.first, head.second - 1)
        println(" => $head")
        print("T: $tail")
        tail = updateTail(tail, head)
        visited.add(tail)
        println(" => $tail")
    }
    return Pair(head, tail)
}

private fun moveDown(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    println("Move D")
    repeat(moves) {
        print("H: $head")
        head = Pair(head.first - 1, head.second)
        println(" => $head")
        print("T: $tail")
        tail = updateTail(tail, head)
        visited.add(tail)
        println(" => $tail")
    }
    return Pair(head, tail)
}

private fun moveUp(moves: Int) {
    println("Move U")
    repeat(moves) {
        print("H: $head")
        head = Pair(head.first + 1, head.second)
        println(" => $head")
        print("T: $tail")
        tail = updateTail(tail, head)
        visited.add(tail)
        println(" => $tail")
    }
}

val visited = mutableSetOf<Pair<Int, Int>>()

fun updateTail(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {

    // Do nothing when distance is max 1
    if (abs(head.first - tail.first) <= 1 && abs(head.second - tail.second) <= 1) {
        return Pair(tail.first, tail.second)
    }

    // Move R
    if (head.first == tail.first && abs(head.second - tail.second) > 1 && head.second > tail.second) {
        return Pair(tail.first, tail.second + 1)
    }

    // Move L
    if (head.first == tail.first && abs(head.second - tail.second) > 1 && head.second < tail.second) {
        return Pair(tail.first, tail.second - 1)
    }

    // Move U
    if (head.second == tail.second && abs(head.first - tail.first) > 1 && head.first > tail.first) {
        return Pair(tail.first + 1, tail.second)
    }

    // Move D
    if (head.second == tail.second && abs(head.first - tail.first) > 1 && head.first < tail.first) {
        return Pair(tail.first - 1, tail.second)
    }

    // ROWS

    // Move diagonal up/right
    if (abs(head.first - tail.first) == 2 && head.first > tail.first && head.second > tail.second) {
        return Pair(tail.first + 1, tail.second + 1)
    }

    // Move diagonal down/right
    if (abs(head.first - tail.first) == 2 && head.first < tail.first && head.second > tail.second) {
        return Pair(tail.first - 1, tail.second + 1)
    }

    // Move diagonal down/left
    if (abs(head.first - tail.first) == 2 && head.first < tail.first && head.second < tail.second) {
        return Pair(tail.first - 1, tail.second - 1)
    }

    // Move diagonal up/left
    if (abs(head.first - tail.first) == 2 && head.first > tail.first && head.second < tail.second) {
        return Pair(tail.first + 1, tail.second - 1)
    }

    // Cols

    // Up/Right
    if (abs(head.second - tail.second) == 2 && head.second > tail.second && head.first > tail.first) {
        return Pair(tail.first + 1, tail.second + 1)
    }

    // Up/Left
    if (abs(head.second - tail.second) == 2 && head.second < tail.second && head.first > tail.first) {
        return Pair(tail.first + 1, tail.second - 1)
    }

    // Down/Left
    if (abs(head.second - tail.second) == 2 && head.second < tail.second && head.first < tail.first) {
        return Pair(tail.first - 1, tail.second - 1)
    }

    // Down/Right
    if (abs(head.second - tail.second) == 2 && head.second > tail.second && head.first < tail.first) {
        return Pair(tail.first - 1, tail.second + 1)
    }

    return Pair(-1, -1)
}
