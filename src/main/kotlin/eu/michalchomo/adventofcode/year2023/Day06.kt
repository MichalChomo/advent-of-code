package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day06 : Day {

    override val number: Int = 6

    override fun part1(input: List<String>): String = input.let {
        val times = it[0].getNumbers()
        val distances = it[1].getNumbers()
        val zip = times.zip(distances)
        zip.map { (raceTime, recordDistance) ->
            (1..<raceTime).count { hold ->
                hold * (raceTime - hold) > recordDistance
            }
        }
            .reduce(Int::times)
    }.toString()

    override fun part2(input: List<String>): String = input.let {
        val raceTime = it[0].getNumber()
        val recordDistance = it[1].getNumber()
        (1..<raceTime).count { hold ->
            hold * (raceTime - hold) > recordDistance
        }
    }.toString()

    private fun String.getNumbers(): List<Int> =
        this.split(":", " ").drop(1).filter { it.isNotEmpty() }.map { it.trim().toInt() }

    private fun String.getNumber(): Long =
        this.split(":", " ").drop(1).filter { it.isNotEmpty() }.reduce { acc, s -> acc + s }.toLong()

}

fun main() {
    main(Day06)
}
