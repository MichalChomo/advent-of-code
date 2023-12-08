package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day02 : Day {

    override val number: Int = 2

    override fun part1(input: List<String>): String = input.sumOf { line ->
        val (game, cubes) = line.split(':')
        val gameNumber = game.split(' ')[1].toInt()
        val isPossible = cubes.trim().split(", ", "; ").all { countAndColorString ->
            countAndColorString.split(' ').let { countAndColorList ->
                val countInt = countAndColorList[0].toInt()
                when (countAndColorList[1]) {
                    "red" -> countInt <= 12
                    "green" -> countInt <= 13
                    "blue" -> countInt <= 14
                    else -> false
                }
            }
        }
        if (isPossible) gameNumber else 0
    }.toString()

    override fun part2(input: List<String>): String = input.sumOf { line ->
        line.split(':')[1].trim().split(", ", "; ").map {
            it.split(' ').let { countAndColorList -> countAndColorList[1] to countAndColorList[0].toInt() }
        }.groupBy { it.first }.mapValues { it.value.maxOf { it.second } }.values.reduce(Int::times)
    }.toString()
}

fun main() {
    main(Day02)
}
