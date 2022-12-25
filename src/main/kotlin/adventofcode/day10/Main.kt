package adventofcode.day10

import java.io.*

fun main() {
    val inputFileCommands =
        File("requirements/day10/sample.txt").useLines { it.toList() }
    val commands = inputFileCommands
    part1WorkingSolution(commands
    ) { currentCylce: Int -> currentCylce % 40 == 20 }
}

fun part1WorkingSolution(
    commands: List<String>,
    isSigStrengthWanted: (Int) -> Boolean): Data {
    var x = 1
    var currentCycle = 1
    var signalStrength = 0
    commands.forEach {
        currentCycle++
        if (isSigStrengthWanted(currentCycle)) {
            signalStrength += (currentCycle * x)
        }
        if (it != "noop") {
            currentCycle++
            x += it.substringAfter(" ").toInt()
            if (isSigStrengthWanted(currentCycle)) {
                signalStrength += (currentCycle * x)
            }
        }
    }
    return Data(signalStrength, x, currentCycle)
}

data class Data(val signalStrength: Int, val x: Int, val currentCycle: Int)
