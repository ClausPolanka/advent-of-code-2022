package adventofcode.day04

import java.io.*

typealias SectionIdRange = List<Int>

fun main() {
    val input = File("requirements/day04/input.txt")
        .useLines { it.toList() }
    println("Part 1 Assignment Pairs where one range fully contains the other: "
            + part1(input))
    println("Part 2 Assignment pairs where ranges overlap: "
            + part2(input))
}

private fun part1(input1: List<String>) = sectionIdRanges(input1)
    .findAssignmentPairsFullyContainingOthers()
    .size

private fun part2(input1: List<String>) = sectionIdRanges(input1)
    .findSectionIdRangeOverlaps()
    .size

/** ["2-4,6-8", "2-3,4-5"] => [[[2,3,4], [6,7,8]], [[2,3], [4,5]]] */
private fun sectionIdRanges(input: List<String>): List<List<SectionIdRange>> =
    input
        .map { it.split(",") } // "2-4,6-8" =>  ["2-4", "6-8"]
        .map { raingeStrPairs -> // ["2-4", "6-8"] => [2..4, 6..8]
            raingeStrPairs.map { rangeStr ->
                val rangeBorders = rangeStr.split("-")
                IntRange(rangeBorders[0].toInt(), rangeBorders[1].toInt())
            }
        }
        // [2..4, 6..8] => [[2,3,4], [6,7,8]]
        .map { rangePairs -> rangePairs.map { range -> range.toList() } }

fun List<List<SectionIdRange>>.findAssignmentPairsFullyContainingOthers() =
    map { listOf(it[0].containsAll(it[1]), it[1].containsAll(it[0])) }
        .filter { it.contains(true) }

fun List<List<SectionIdRange>>.findSectionIdRangeOverlaps() =
    map { it[0].intersect(it[1].toSet()) }
        .filter { it.isNotEmpty() }
