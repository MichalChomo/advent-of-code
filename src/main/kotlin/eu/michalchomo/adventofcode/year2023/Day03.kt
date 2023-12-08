package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day03 : Day {

    override val number: Int = 3

    override fun part1(input: List<String>): String = input.addFirstAndLastBlank()
        .windowed(3, 1)
        .sumOf { window ->
            val upperLine = window[0]
            val currentLine = window[1]
            val bottomLine = window[2]
            val indexedNumbers = currentLine.indices.fold(mutableListOf<Pair<Int, String>>()) { acc, index ->
                if (currentLine[index].isDigit() && (index == 0 || !currentLine[index - 1].isDigit())) {
                    val number = currentLine.substring(index).takeWhile { it.isDigit() }
                    acc.add(index to number)
                }
                acc
            }
            indexedNumbers.filter { (index, number) ->
                val startIndex = (index - 1).coerceAtLeast(0)
                val endIndex = (index + number.length + 1).coerceAtMost(currentLine.length)
                upperLine.substring(startIndex, endIndex).any { it.isSymbol() }
                        ||
                        // Look at the start of line only if the number is not at the start
                        (index > 0 && currentLine[startIndex].isSymbol())
                        ||
                        // Look at the end of line only if the number is not at the end
                        (index < (currentLine.length - number.length) && currentLine[endIndex - 1].isSymbol())
                        ||
                        bottomLine.substring(startIndex, endIndex).any { it.isSymbol() }
            }
                .sumOf { it.second.toInt() }
        }.toString()

    override fun part2(input: List<String>): String = input.addFirstAndLastBlank()
        .windowed(3, 1) { window ->
            val upperLine = window[0]
            val currentLine = window[1]
            val bottomLine = window[2]
            val gearIndices = currentLine.mapIndexedNotNull { index, c -> if (c == '*') index else null }
            gearIndices.sumOf { index ->
                val adjacentNumbers = upperLine.findAdjacentNumbersAtIndex(index) +
                        currentLine.findAdjacentNumbersAtIndex(index) + bottomLine.findAdjacentNumbersAtIndex(index)
                if (adjacentNumbers.size == 2) adjacentNumbers.reduce(Int::times) else 0
            }
        }.sumOf { it }.toString()

    private fun Char.isSymbol() = this != '.' && !this.isDigit()

    private fun List<String>.addFirstAndLastBlank() = this.toMutableList()
        .apply {
            val dots = generateSequence { '.' }.take(this[0].length).joinToString("")
            add(0, dots)
            add(dots)
        }

    private fun String.findAdjacentNumbersAtIndex(index: Int): List<Int> =
        (
            (if (index > 0) this.substring(0, index).reversed().takeWhile { it.isDigit() }.reversed() else "") +
            (this[index].digitToIntOrNull()?.toString() ?: ".") +
            (if (index < this.length - 1) this.substring(index + 1).takeWhile { it.isDigit() } else "")
        )
            .split('.').filter { it.isNotEmpty() }.map { it.toInt() }

}

fun main() {
    main(Day03)
}
