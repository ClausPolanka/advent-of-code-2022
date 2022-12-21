package adventofcode.day09

import java.io.*
import kotlin.math.*

var head = Pair(0, 0)
var one = Pair(0, 0)
var two = Pair(0, 0)
var three = Pair(0, 0)
var four = Pair(0, 0)
var five = Pair(0, 0)
var six = Pair(0, 0)
var seven = Pair(0, 0)
var eight = Pair(0, 0)
var nine = Pair(0, 0)
var tail = Pair(0, 0)

val visited = mutableSetOf<Pair<Int, Int>>()

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
            "R" -> move(cmd.last().toInt(), "R", ::moveRight)
            "L" -> move(cmd.last().toInt(), "L", ::moveLeft)
            "U" -> move(cmd.last().toInt(), "U", ::moveUp)
            "D" -> move(cmd.last().toInt(), "D", ::moveDown)
        }
    }

    println()
    println("How many positions does the tail of the rope visit at least once?")
    println(visited.size) // Part 1: 5683 (input) Part 1: 13 (sample)
}

private fun move(
    moves: Int,
    direction: String,
    moveDirection: (p: Pair<Int, Int>) -> Pair<Int, Int>):
        Pair<Pair<Int, Int>, Pair<Int, Int>> {
    println("Move $direction")
    repeat(moves) {
        updateHeadWithDebugging(moveDirection)
        updateTailWithDebugging()
    }
    return Pair(head, tail)
}

private fun updateHeadWithDebugging(
    moveDirection: (p: Pair<Int, Int>) -> Pair<Int, Int>) {
    print("H: $head")
    head = moveDirection(head)
    println(" => $head")
}

private fun updateTailWithDebugging() {
    tail = one
    print("1: $tail")
    one = updateTail(tail, head)
    println(" => $tail")

    tail = two
    print("2: $tail")
    two = updateTail(tail, one)
    println(" => $tail")

    tail = three
    print("3: $tail")
    three = updateTail(tail, two)
    println(" => $tail")

    tail = four
    print("4: $tail")
    four = updateTail(tail, three)
    println(" => $tail")

    tail = five
    print("5: $tail")
    five = updateTail(tail, four)
    println(" => $tail")

    tail = six
    print("6: $tail")
    six = updateTail(tail, five)
    println(" => $tail")

    tail = seven
    print("7: $tail")
    seven = updateTail(tail, six)
    println(" => $tail")

    tail = eight
    print("8: $tail")
    eight = updateTail(tail, seven)
    println(" => $tail")

    tail = nine
    print("9: $tail")
    nine = updateTail(tail, eight)
    visited.add(nine)
    println(" => $tail")
}

fun updateTail(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {
    // Do nothing when distance is max 1
    if (abs(head.first - tail.first) <= 1
        && abs(head.second - tail.second) <= 1) {
        return Pair(tail.first, tail.second)
    }
    if (head isOneColumnAwayFrom tail && head isRightOf tail) {
        return moveRight(tail)
    }
    if (head isOneColumnAwayFrom tail && head isLeftOf tail) {
        return moveLeft(tail)
    }
    if (head isOneRowAwayFrom tail && head isAbove tail) {
        return moveUp(tail)
    }
    if (head isOneRowAwayFrom tail && head isBelow tail) {
        return moveDown(tail)
    }
    if (head isTwoRowsAwayFrom tail && head isAboveRightOf tail) {
        return tail.moveUpRight()
    }
    if (head isTwoRowsAwayFrom tail && head isBelowRightOf tail) {
        return tail.moveDownRight()
    }
    if (head isTwoRowsAwayFrom tail && head isBelowLeftOf tail) {
        return tail.moveDownLeft()
    }
    if (head isTwoRowsAwayFrom tail && head isAboveLeftOf tail) {
        return tail.moveUpLeft()
    }
    if (head isTwoColumnsAwayFrom tail && head isAboveRightOf tail) {
        return tail.moveUpRight()
    }
    if (head isTwoColumnsAwayFrom tail && head isAboveLeftOf tail) {
        return tail.moveUpLeft()
    }
    if (head isTwoColumnsAwayFrom tail && head isBelowLeftOf tail) {
        return tail.moveDownLeft()
    }
    if (head isTwoColumnsAwayFrom tail && head isBelowRightOf tail) {
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
private fun moveDown(p: Pair<Int, Int>) = Pair(p.first - 1, p.second)
private fun moveUp(p: Pair<Int, Int>) = Pair(p.first + 1, p.second)
private fun moveRight(p: Pair<Int, Int>) = Pair(p.first, p.second + 1)
private fun moveLeft(p: Pair<Int, Int>) = Pair(p.first, p.second - 1)

private infix fun Pair<Int, Int>.isBelow(tail: Pair<Int, Int>) =
    first < tail.first

private infix fun Pair<Int, Int>.isAbove(tail: Pair<Int, Int>) =
    first > tail.first

private infix fun Pair<Int, Int>.isAboveRightOf(tail: Pair<Int, Int>) =
    this isAbove tail && this isRightOf tail

private infix fun Pair<Int, Int>.isAboveLeftOf(tail: Pair<Int, Int>) =
    this isAbove tail && this isLeftOf tail

private infix fun Pair<Int, Int>.isBelowLeftOf(tail: Pair<Int, Int>) =
    this isBelow tail && this isLeftOf tail

private infix fun Pair<Int, Int>.isBelowRightOf(tail: Pair<Int, Int>) =
    this isBelow tail && this isRightOf tail

private infix fun Pair<Int, Int>.isLeftOf(tail: Pair<Int, Int>) =
    second < tail.second

private infix fun Pair<Int, Int>.isRightOf(tail: Pair<Int, Int>) =
    second > tail.second

private infix fun Pair<Int, Int>.isOneRowAwayFrom(tail: Pair<Int, Int>) =
    second == tail.second && abs(first - tail.first) > 1

private infix fun Pair<Int, Int>.isOneColumnAwayFrom(tail: Pair<Int, Int>) =
    first == tail.first && abs(second - tail.second) > 1
