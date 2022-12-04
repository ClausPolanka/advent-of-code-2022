package adventofcode.day02

import java.io.*

private const val OPONENT = 0
private const val ME = 1

private val rock = Shape(name = "Rock", score = 1)
private val paper = Shape(name = "Paper", score = 2)
private val scissors = Shape(name = "Scissors", score = 3)

private val shapes = mutableMapOf(
    "A" to rock,
    "B" to paper,
    "C" to scissors,
    "X" to rock,
    "Y" to paper,
    "Z" to scissors)

fun main() {
    val input1 = File("requirements/day02/input.txt")
        .useLines { it.toList() }.map { it.split(" ") }

    println(input1
        .map { round -> round.map { column -> shapes[column] } }
        .sumOf { round -> round[ME]!!.play(round[OPONENT]!!) })
}

