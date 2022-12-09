fun main() {
    fun String.getPairOfRanges() =
        this.split(",")
            .map { r -> r.split("-") }
            .map { r -> IntRange(r[0].toInt(), r[1].toInt()) }
            .zipWithNext()
            .single()

    fun IntRange.fullyContains(other: IntRange) = other.first in this && other.last in this

    fun IntRange.containsOrIsContained(other: IntRange) = this.fullyContains(other) || other.fullyContains(this)

    fun part1(input: List<String>): Int = input.sumOf {
        it.getPairOfRanges().let { p -> p.first.containsOrIsContained(p.second).toInt() }
    }

    fun part2(input: List<String>): Int = input.sumOf {
        it.getPairOfRanges().let { p -> p.first.intersect(p.second).isNotEmpty().toInt() }
    }

    val testInput = readInputLines("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInputLines("Day04")
    println(part1(input))
    println(part2(input))
}
