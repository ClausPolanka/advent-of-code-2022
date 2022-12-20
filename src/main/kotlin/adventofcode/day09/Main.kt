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
        head = head.moveRight()
        println(" => $head")
        updateTailWithDebugging()
    }
    return Pair(head, tail)
}

private fun moveLeft(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    println("Move L")
    repeat(moves) {
        print("H: $head")
        head = head.moveLeft()
        println(" => $head")
        updateTailWithDebugging()
    }
    return Pair(head, tail)
}

private fun moveDown(moves: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    println("Move D")
    repeat(moves) {
        print("H: $head")
        head = head.moveDown()
        println(" => $head")
        updateTailWithDebugging()
    }
    return Pair(head, tail)
}

private fun moveUp(moves: Int) {
    println("Move U")
    repeat(moves) {
        print("H: $head")
        head = head.moveUp()
        println(" => $head")
        updateTailWithDebugging()
    }
}

private fun updateTailWithDebugging() {
    print("T: $tail")
    tail = updateTail(tail, head)
    visited.add(tail)
    println(" => $tail")
}

val visited = mutableSetOf<Pair<Int, Int>>()

fun updateTail(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {

    // Do nothing when distance is max 1
    if (abs(head.first - tail.first) <= 1
        && abs(head.second - tail.second) <= 1) {
        return Pair(tail.first, tail.second)
    }

    if (head isOneColumnAwayFrom tail && head isRightOf tail) {
        return tail.moveRight()
    }

    if (head isOneColumnAwayFrom tail && head isLeftOf tail) {
        return tail.moveLeft()
    }

    if (head isOneRowAwayFrom tail && head isAbove tail) {
        return tail.moveUp()
    }

    if (head isOneRowAwayFrom tail && head isBelow tail) {
        return tail.moveDown()
    }

    if (head isTwoRowsAwayFrom tail
        && head isAbove tail
        && head isRightOf tail) {
        return tail.moveUpRight()
    }

    if (head isTwoRowsAwayFrom tail
        && head isBelow tail
        && head isRightOf tail) {
        return tail.moveDownRight()
    }

    if (head isTwoRowsAwayFrom tail
        && head isBelow tail
        && head isLeftOf tail) {
        return tail.moveDownLeft()
    }

    if (head isTwoRowsAwayFrom tail
        && head isAbove tail
        && head isLeftOf tail) {
        return tail.moveUpLeft()
    }

    if (head isTwoColumnsAwayFrom tail
        && head isRightOf tail
        && head isAbove tail) {
        return tail.moveUpRight()
    }

    if (head isTwoColumnsAwayFrom tail
        && head isLeftOf tail
        && head isAbove tail) {
        return tail.moveUpLeft()
    }

    if (head isTwoColumnsAwayFrom tail
        && head isLeftOf tail
        && head isBelow tail) {
        return tail.moveDownLeft()
    }

    if (head isTwoColumnsAwayFrom tail
        && head isRightOf tail
        && head isBelow tail) {
        return tail.moveDownRight()
    }

    return Pair(-1, -1)
}

private infix fun Pair<Int, Int>.isTwoRowsAwayFrom(tail: Pair<Int, Int>) =
    abs(first - tail.first) == 2

private infix fun Pair<Int, Int>.isTwoColumnsAwayFrom(tail: Pair<Int, Int>) =
    abs(second - tail.second) == 2

private fun Pair<Int, Int>.moveUpLeft() = Pair(first + 1, second - 1)
private fun Pair<Int, Int>.moveDownLeft() = Pair(first - 1, second - 1)
private fun Pair<Int, Int>.moveDownRight() = Pair(first - 1, second + 1)
private fun Pair<Int, Int>.moveUpRight() = Pair(first + 1, second + 1)
private fun Pair<Int, Int>.moveDown() = Pair(first - 1, second)
private fun Pair<Int, Int>.moveUp() = Pair(first + 1, second)
private fun Pair<Int, Int>.moveRight() = Pair(first, second + 1)
private fun Pair<Int, Int>.moveLeft() = Pair(first, second - 1)

private infix fun Pair<Int, Int>.isBelow(tail: Pair<Int, Int>) =
    first < tail.first

private infix fun Pair<Int, Int>.isAbove(tail: Pair<Int, Int>) =
    first > tail.first

private infix fun Pair<Int, Int>.isLeftOf(tail: Pair<Int, Int>) =
    second < tail.second

private infix fun Pair<Int, Int>.isRightOf(tail: Pair<Int, Int>) =
    second > tail.second

private infix fun Pair<Int, Int>.isOneRowAwayFrom(tail: Pair<Int, Int>) =
    second == tail.second && abs(first - tail.first) > 1

private infix fun Pair<Int, Int>.isOneColumnAwayFrom(tail: Pair<Int, Int>) =
    first == tail.first && abs(second - tail.second) > 1
