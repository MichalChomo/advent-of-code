package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day09 : Day {

    override val number: Int = 9

    override fun part1(input: List<String>): String = solve(input) { histories ->
        histories.foldRight(0) { ints, acc -> ints.last() + acc }
    }

    override fun part2(input: List<String>): String = solve(input) { histories ->
        histories.foldRight(0) { ints, acc -> ints.first() - acc }
    }

    private fun solve(input: List<String>, extrapolate: (List<List<Int>>) -> Int): String = input.sumOf { line ->
        line.splitToIntList().predictNext(extrapolate)
    }.toString()

    private fun String.splitToIntList(): List<Int> = this.split(' ').map { it.toInt() }

    private fun List<Int>.predictNext(extrapolate: (List<List<Int>>) -> Int): Int {
        val extrapolation = mutableListOf(this)
        while (extrapolation.last().toSet().size > 1) {
            extrapolation.add(
                extrapolation.last().drop(1)
                    .mapIndexed { i, it -> it.diff(extrapolation.last()[i]) }
                    .toList()
            )
        }
        return extrapolate(extrapolation)
    }

    private fun Int.diff(other: Int) =
        (kotlin.math.max(this, other) - kotlin.math.min(this, other)) * if (this < other) -1 else 1

}

fun main() {
    main(Day09)
}
