package adventofcode.day09

import java.io.*
import kotlin.math.*

var head = Pair(0, 0)
var tail = Pair(0, 0)

fun main() {
    val fileCommands =
        File("requirements/day09/sample.txt").useLines { it.toList() }

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

    val commands = fileCommands
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
    println(moves.size)
}

private fun moveRight(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    println("Move R")
    repeat(moves) {
        print("H: $head")
        head = Pair(head.first, head.second + 1)
        println(" => $head")
        print("T: $tail")
        tail = updateTail(tail, head)
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
        println(" => $tail")
    }
}

val moves = mutableSetOf<Pair<Int, Int>>()

fun updateTail(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {

    // Do nothing when distance is max 1
    if (abs(head.first - tail.first) <= 1 && abs(head.second - tail.second) <= 1) {
        val t = Pair(tail.first, tail.second)
        moves.add(t)
        return t
    }

    // Move R
    if (head.first == tail.first && abs(head.second - tail.second) > 1 && head.second > tail.second) {
        val t = Pair(tail.first, tail.second + 1)
        moves.add(t)
        return t
    }

    // Move L
    if (head.first == tail.first && abs(head.second - tail.second) > 1 && head.second < tail.second) {
        val t = Pair(tail.first, tail.second - 1)
        moves.add(t)
        return t
    }

    // Move U
    if (head.second == tail.second && abs(head.first - tail.first) > 1 && head.first > tail.first) {
        val t = Pair(tail.first + 1, tail.second)
        moves.add(t)
        return t
    }

    // Move D
    if (head.second == tail.second && abs(head.first - tail.first) > 1 && head.first < tail.first) {
        val t = Pair(tail.first - 1, tail.second)
        moves.add(t)
        return t
    }

    // ROWS

    // Move diagonal up/right
    if (abs(head.first - tail.first) == 2 && head.first > tail.first && head.second > tail.second) {
        val t = Pair(tail.first + 1, tail.second + 1)
        moves.add(t)
        return t
    }

    // Move diagonal down/right
    if (abs(head.first - tail.first) == 2 && head.first < tail.first && head.second > tail.second) {
        val t = Pair(tail.first - 1, tail.second + 1)
        moves.add(t)
        return t
    }

    // Move diagonal down/left
    if (abs(head.first - tail.first) == 2 && head.first < tail.first && head.second < tail.second) {
        val t = Pair(tail.first - 1, tail.second - 1)
        moves.add(t)
        return t
    }

    // Move diagonal up/left
    if (abs(head.first - tail.first) == 2 && head.first > tail.first && head.second < tail.second) {
        val t = Pair(tail.first + 1, tail.second - 1)
        moves.add(t)
        return t
    }

    // Cols

    // Up/Right
    if (abs(head.second - tail.second) == 2 && head.second > tail.second && head.first > tail.first) {
        val t = Pair(tail.first + 1, tail.second + 1)
        moves.add(t)
        return t
    }

    // Up/Left
    if (abs(head.second - tail.second) == 2 && head.second < tail.second && head.first > tail.first) {
        val t = Pair(tail.first + 1, tail.second - 1)
        moves.add(t)
        return t
    }

    // Down/Left
    if (abs(head.second - tail.second) == 2 && head.second < tail.second && head.first < tail.first) {
        val t = Pair(tail.first - 1, tail.second - 1)
        moves.add(t)
        return t
    }

    // Down/Right
    if (abs(head.second - tail.second) == 2 && head.second > tail.second && head.first < tail.first) {
        val t = Pair(tail.first - 1, tail.second + 1)
        moves.add(t)
        return t
    }

    return Pair(-1, -1)
}
