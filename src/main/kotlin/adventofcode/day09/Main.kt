package adventofcode.day09

import kotlin.math.*

var head = Pair(0, 0)
var tail = Pair(0, 0)

fun main() {
    val grid = """
        ......
        ......
        ......
        ......
        H.....
    """.trimIndent()

    val commands = """
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
        .map { row -> row.split(" ") }

    commands.forEach { println(it) }

//    moveRight(4)
//    moveLeft(4)
    moveDown(4)
    moveUp(4)
}

private fun moveRight(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    repeat(moves) {
        head = Pair(head.first, head.second + 1)
        println("H: $head")
        tail = updateTail(tail, head)
        println("T: $tail")
    }
    return Pair(head, tail)
}

private fun moveLeft(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    repeat(moves) {
        head = Pair(head.first, head.second - 1)
        println("H: $head")
        tail = updateTail(tail, head)
        println("T: $tail")
    }
    return Pair(head, tail)
}

private fun moveDown(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    repeat(moves) {
        head = Pair(head.first + 1, head.second)
        println("H: $head")
        tail = updateTail(tail, head)
        println("T: $tail")
    }
    return Pair(head, tail)
}

private fun moveUp(moves: Int) {
    repeat(moves) {
        head = Pair(head.first - 1, head.second)
        println("H: $head")
        tail = updateTail(tail, head)
        println("T: $tail")
    }
}

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

    // Move D
    if (head.second == tail.second && abs(head.first - tail.first) > 1 && head.first > tail.first) {
        return Pair(tail.first + 1, tail.second)
    }

    // Move D
    if (head.second == tail.second && abs(head.first - tail.first) > 1 && head.first < tail.first) {
        return Pair(tail.first - 1, tail.second)
    }

    return Pair(-1, -1)
}
