package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main
import java.math.BigInteger

typealias CountMatchingToOccurrences = Pair<Int, Int>

object Day04 : Day {

    override val number: Int = 4

    override fun part1(input: List<String>): Int = input.map { line ->
        BigInteger.ONE.shl(line.getMatchingNumbers().size - 1)
    }.reduce(BigInteger::plus).toInt()


    override fun part2(input: List<String>): Int =
        input.foldIndexed(mutableMapOf<Int, CountMatchingToOccurrences>()) { i, acc, line ->
            val currentCardIndex = i + 1
            val matchingNumbers = line.getMatchingNumbers()
            val currentCardCopies = acc[currentCardIndex]?.second?.plus(1) ?: 1
            acc[currentCardIndex] = matchingNumbers.size to currentCardCopies
            matchingNumbers.indices.forEach { j ->
                val otherCardIndex = currentCardIndex + j + 1
                acc.merge(otherCardIndex, 0 to currentCardCopies) { (a, b), _ ->
                    a to b + currentCardCopies
                }
            }
            acc
        }.values.sumOf { it.second }

    private fun String.getMatchingNumbers(): Set<Int> = this.split(':', '|')
        .drop(1)
        .map {
            it.trim().split(' ')
                .filter { numberString -> numberString.isNotEmpty() }
                .map { numberString -> numberString.trim().toInt() }
        }
        .let { it[0].toSet().intersect(it[1].toSet()) }

}

fun main() {
    main(Day04)
}
