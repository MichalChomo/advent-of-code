package eu.michalchomo.adventofcode.year2022

fun main() {
    fun getListOfSummedElfCalories(input: String) =
        input.split("\n\n")
            .map {
                it.split("\n")
                    .sumOf { itemCaloriesStr -> itemCaloriesStr.trim().toInt() }
            }

    fun part1(input: String): Int {
        return getListOfSummedElfCalories(input).max()
    }

    fun part2(input: String): Int {
        return getListOfSummedElfCalories(input).sortedDescending().take(3).sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24_000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
