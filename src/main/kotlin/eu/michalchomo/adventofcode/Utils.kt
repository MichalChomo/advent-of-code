package eu.michalchomo.adventofcode

import java.io.File

fun readInput(year: Int, name: String) =
    File("src/main/kotlin/eu/michalchomo/adventofcode/year$year", "$name.txt").readText()

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(year: Int, name: String) =
    File("src/main/kotlin/eu/michalchomo/adventofcode/year$year", "$name.txt").readLines()

fun linesAsInts(lines: List<String>) = lines.map { l -> l.toInt() }

fun Boolean.toInt() = if (this) 1 else 0
