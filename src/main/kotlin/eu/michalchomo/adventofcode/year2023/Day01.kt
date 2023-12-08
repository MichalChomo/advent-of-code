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

    override fun part1(input: List<String>): String = input.filter { it.isNotEmpty() }
        .sumOf { line ->
            (line.dropWhile { !it.isDigit() }.first().toString() + line.reversed().dropWhile { !it.isDigit() }
                .first().toString()).toInt()
        }.toString()

    override fun part2(input: List<String>): String = input.sumOf { line ->
        line.foldIndexed("") { i, acc, c ->
            if (c.isDigit()) {
                acc + c
            } else {
                val num = stringDigitsToIntegers.keys.find { line.substring(i).startsWith(it) }
                if (num != null) {
                    acc + stringDigitsToIntegers[num]
                } else {
                    acc
                }
            }
        }.let { it[0].digitToInt() * 10 + it.last().digitToInt() }
    }.toString()

}

fun main() {
    main(Day01)
}

