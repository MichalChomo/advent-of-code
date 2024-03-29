package eu.michalchomo.adventofcode

interface Day {
    val number: Int

    fun name() = "Day%02d".format(number)

    fun part1(input: List<String>): String
    fun part2(input: List<String>): String
}
