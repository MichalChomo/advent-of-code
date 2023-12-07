package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day01 : Day {

    override val number = 1

    private val stringDigitsToIntegers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    override fun part1(input: List<String>): Int = input.filter { it.isNotEmpty() }
        .sumOf { line ->
            (line.dropWhile { !it.isDigit() }.first().toString() + line.reversed().dropWhile { !it.isDigit() }
                .first().toString()).toInt()
        }

    override fun part2(input: List<String>): Int = part1(
        input.map { line ->
            val foundIndexedDigitStrings = mutableListOf(line.findAnyOf(stringDigitsToIntegers.keys)?.toDigitString())
            while (foundIndexedDigitStrings.lastOrNull() != null) {
                val startIndex = foundIndexedDigitStrings.lastOrNull()!!.index + 1
                val foundPair = line.substring(startIndex)
                    .findAnyOf(stringDigitsToIntegers.keys)?.let { it.copy(first = it.first + startIndex) }
                foundIndexedDigitStrings.add(foundPair?.toDigitString())
            }
            foundIndexedDigitStrings.filterNotNull()
                .foldRight(line) { foundDigitString, acc ->
                    val endIndexA = if (foundDigitString.isInString(acc)) {
                        // Non overlapping
                        foundDigitString.string.length
                    } else {
                        // Overlapping
                        acc.substring(foundDigitString.index).indexOfFirst { it.isDigit() }
                    }
                    acc.replaceRange(
                        foundDigitString.index,
                        foundDigitString.index + endIndexA,
                        stringDigitsToIntegers[foundDigitString.string].toString()
                    )
                }
        }
    )

    data class IndexedDigitString(val index: Int, val string: String) {
        fun isInString(str: String): Boolean = str.substring(index).startsWith(string)
    }

    private fun Pair<Int, String>.toDigitString() = IndexedDigitString(first, second)

}

fun main() {
    main(Day01)
}

