package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day06 : Day {

    override val number: Int = 6

    override fun part1(input: List<String>): Int = input.let {
        val times = it[0].getNumbers()
        val distances = it[1].getNumbers()
        val zip = times.zip(distances)
        zip.map { (raceTime, recordDistance) ->
            (1..<raceTime).map { hold ->
                hold * (raceTime - hold)
            }.count { totalDistance -> totalDistance > recordDistance }
        }
            .reduce(Int::times)
    }

    override fun part2(input: List<String>): Int = input.let {
        val raceTime = it[0].getNumber()
        val recordDistance = it[1].getNumber()
        (1..<raceTime).map { hold ->
            hold * (raceTime - hold)
        }.count { totalDistance -> totalDistance > recordDistance }
    }

    private fun String.getNumbers(): List<Int> =
        this.split(":", " ").drop(1).filter { it.isNotEmpty() }.map { it.trim().toInt() }

    private fun String.getNumber(): Long =
        this.split(":", " ").drop(1).filter { it.isNotEmpty() }.reduce { acc, s -> acc + s }.toLong()

}

fun main() {
    main(Day06)
}
