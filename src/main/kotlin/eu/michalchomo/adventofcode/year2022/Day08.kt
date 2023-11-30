package eu.michalchomo.adventofcode.year2022

import eu.michalchomo.adventofcode.toInt


fun main() {

    fun part1(input: List<String>): Int = input.map { it.map { it.digitToInt() } }
        .let { rows ->
            rows.mapIndexed { rowIndex, cols ->
                cols.mapIndexed cols@{ colIndex, height ->
                    if (rowIndex !in 1 until rows.size - 1 || colIndex !in 1 until cols.size - 1) return@cols 0
                    listOf(
                        (cols.slice(0 until colIndex).all { it < height }),
                        (cols.slice(colIndex + 1 until cols.size).all { it < height }),
                        ((0 until rowIndex).all { rows[it][colIndex] < height }),
                        ((rowIndex + 1 until rows.size).all { rows[it][colIndex] < height })
                    ).any { it }.toInt()
                }.sum()
            }.sum()
        }.plus(input.size * 4 - 4)

    fun part2(input: List<String>): Int = input.map { it.map { it.digitToInt() } }
        .let { rows ->
            rows.mapIndexed { rowIndex, cols ->
                cols.mapIndexed cols@{ colIndex, height ->
                    if (rowIndex !in 1 until rows.size - 1 || colIndex !in 1 until cols.size - 1) return@cols 0
                    listOf(
                        (colIndex - 1 downTo 0).filter { cols[it] >= height }
                            .maxOrNull()?.let { colIndex - it } ?: colIndex,
                        (colIndex + 1 until cols.size).filter { cols[it] >= height }
                            .minOrNull()?.let { it - colIndex } ?: (cols.size - 1 - colIndex),
                        (rowIndex - 1 downTo 0).filter { rows[it][colIndex] >= height }
                            .maxOrNull()?.let { rowIndex - it } ?: rowIndex,
                        (rowIndex + 1 until rows.size).filter { rows[it][colIndex] >= height }
                            .minOrNull()?.let { it - rowIndex } ?: (rows.size - 1 - rowIndex),
                    ).reduce { a, b -> a * b }
                }.max()
            }.max()
        }

    val testInput = readInputLines("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInputLines("Day08")
    println(part1(input))
    println(part2(input))
}
