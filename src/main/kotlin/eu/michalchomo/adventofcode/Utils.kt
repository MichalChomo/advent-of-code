package eu.michalchomo.adventofcode

import java.io.File

private const val ROOT_PATH = "src/main/kotlin/eu/michalchomo/adventofcode/year"

fun readInput(year: Int, name: String) =
    File("$ROOT_PATH$year", "$name.txt").readText()

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(year: Int, name: String) =
    File("$ROOT_PATH$year", "$name.txt").readLines()

fun linesAsInts(lines: List<String>) = lines.map { l -> l.toInt() }

fun Boolean.toInt() = if (this) 1 else 0

fun main(day: Day) {
    val testInput = eu.michalchomo.adventofcode.year2023.readInputLines("${day.name()}_test")
    println(day.part1(testInput))
    println(day.part2(testInput))

    val input = eu.michalchomo.adventofcode.year2023.readInputLines(day.name())
    println(day.part1(input))
    println(day.part2(input))
}
