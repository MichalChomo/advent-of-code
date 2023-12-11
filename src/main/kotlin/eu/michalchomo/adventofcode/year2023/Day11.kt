package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main
import eu.michalchomo.adventofcode.toCharMatrix
import kotlin.math.max
import kotlin.math.min

object Day11 : Day {

    override val number: Int = 11

    override fun part1(input: List<String>): String = solve(input)

    override fun part2(input: List<String>): String = solve(input, emptySpaceExpansionFactor = 1_000_000)

    private fun solve(input: List<String>, emptySpaceExpansionFactor: Int = 2): String =
        input.toCharMatrix().let { matrix ->
            val emptyRowsIndices = matrix.asSequence()
                .mapIndexedNotNull { i, row -> if (row.toSet().size == 1) i else null }
                .toList()
            val emptyColsIndices = (0..<matrix[0].size).asSequence()
                .mapNotNull { j -> if (matrix.indices.all { row -> matrix[row][j] == '.' }) j else null }
                .toList()
            val galaxies = matrix.asSequence()
                .flatMapIndexed { i, row ->
                    val rowOffset = emptyRowsIndices.count { it < i } * (emptySpaceExpansionFactor - 1)
                    row.asSequence().mapIndexedNotNull { j, c ->
                        if (c == '#') {
                            val colOffset = emptyColsIndices.count { it < j } * (emptySpaceExpansionFactor - 1)
                            (i + rowOffset).toLong() to (j + colOffset).toLong()
                        } else null
                    }.toList()
                }
                .mapIndexed { index, list -> index to list }
                .toMap()
            galaxies.values.indices.sumOf { i ->
                ((i + 1)..<galaxies.size).sumOf { j -> galaxies[i]!!.distanceTo(galaxies[j]!!) }
            }
        }.toString()

    private fun Pair<Long, Long>.distanceTo(other: Pair<Long, Long>) =
        (max(this.first, other.first) - min(this.first, other.first)) +
                (max(this.second, other.second) - min(this.second, other.second))

}

fun main() {
    main(Day11)
}
