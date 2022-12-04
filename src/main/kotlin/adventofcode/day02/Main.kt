package adventofcode.day02

import java.io.*

private const val PLAYER_1 = 0
private const val PLAYER_2 = 1
private const val ROCK = "A"
private const val PAPER = "B"
private const val SCISSORS = "C"
private const val LOSES_WITH = "X"
private const val DRAWS_WITH = "Y"
private const val WINS_WITH = "Z"

private val rock = Shape(name = "Rock", beats = "Scissors", score = 1,
    player2 = mapOf(
        LOSES_WITH to SCISSORS,
        DRAWS_WITH to ROCK,
        WINS_WITH to PAPER
    ))
private val paper = Shape(name = "Paper", beats = "Rock", score = 2,
    player2 = mapOf(
        LOSES_WITH to ROCK,
        DRAWS_WITH to PAPER,
        WINS_WITH to SCISSORS
    ))
private val scissors = Shape(name = "Scissors", beats = "Paper", score = 3,
    player2 = mapOf(
        LOSES_WITH to PAPER,
        DRAWS_WITH to SCISSORS,
        WINS_WITH to ROCK
    ))

private val shapes = mutableMapOf(
    ROCK to rock,
    PAPER to paper,
    SCISSORS to scissors,
    // Part 1 specific
    "X" to rock,
    "Y" to paper,
    "Z" to scissors)

fun main() {
    val input1 = File("requirements/day02/input.txt")
        .useLines { it.toList() }.map { it.split(" ") }
    val result1 = part1(input1)
    println("Part 1 score: $result1")
    val input2 = File("requirements/day02/input_part2.txt")
        .useLines { it.toList() }.map { it.split(" ") }
    val result2 = part2(input2)
    println("Part 2 score: $result2")
}

private fun part1(input1: List<List<String>>): Int = input1
    .map { round -> round.map { column -> shapes[column] } }
    .sumOf { round -> round[PLAYER_2]!!.play(round[PLAYER_1]!!) }

private fun part2(input2: List<List<String>>): Int = input2
    .map { round ->
        listOf(
            shapes[round.first()],
            shapeFor(
                oponent = shapes[round.first()]!!,
                expectedGameResultForPlayer2 = round[1]))
    }
    .sumOf { round -> round[PLAYER_2]!!.play(round[PLAYER_1]!!) }

fun shapeFor(oponent: Shape, expectedGameResultForPlayer2: String): Shape =
    shapes[oponent.player2[expectedGameResultForPlayer2]]!!

