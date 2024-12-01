package eu.michalchomo.adventofcode.year2024

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main
import kotlin.math.abs

object Day01 : Day {

    override val number: Int = 1

    override fun part1(input: List<String>): String =
        input.getListOfLeftRightPairs()
            .let { pairsOfLocationIds ->
                pairsOfLocationIds.map { it.first }.toList().sorted()
                    .zip(pairsOfLocationIds.map { it.second }.toList().sorted())
                    .map { abs(it.first - it.second) }
            }
            .sum()
            .toString()

    override fun part2(input: List<String>): String =
        input.getListOfLeftRightPairs()
            .let { pairsOfLocationIds ->
                val rightList = pairsOfLocationIds.map { it.second }.toList()
                val numberToOccurrenceCount = rightList.associateWith { n -> rightList.count { it == n} }
                pairsOfLocationIds.sumOf { it.first * numberToOccurrenceCount.getOrDefault(it.first, 0) }
            }
            .toString()

    private fun List<String>.getListOfLeftRightPairs(): List<Pair<Int, Int>> =
        this.map {
            it.split("\\s+".toRegex()).let { it[0].toInt() to it[1].toInt() }
        }

}

fun main() {
    main(Day01)
}
