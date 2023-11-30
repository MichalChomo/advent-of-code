package eu.michalchomo.adventofcode.year2022




fun main() {
    fun List<String>.toInstructionValuePairs() = asSequence()
        .map { it.split(" ") }
        .map { (it[0] to it.getOrNull(1)?.run { toInt() }) }
        .map { if (it.first == "addx") listOf(it, "noop" to null) else listOf(it) }
        .flatten()

    fun part1(input: List<String>): Int =
        input.toInstructionValuePairs()
            .let {
                it.plus("noop" to null).plus("noop" to null)
                    .zip(listOf("noop" to null).plus(it).asSequence())
                    .zip(listOf("noop" to null, "noop" to null).plus(it).asSequence())

                    .mapIndexed { index, pair -> (pair.second.second ?: 0) to index + 1 }
                    .runningFold(1 to 1) { (x, _), (instructionValue, cycleIndex) ->
                        (x + instructionValue) to cycleIndex
                    }
                    .filter { it.second in listOf(20, 60, 100, 140, 180, 220) }
                    .map { it.first * it.second }
                    .sum()
            }

    fun part2(input: List<String>): String =
        input.toInstructionValuePairs()
            .mapIndexed { index, pair -> (pair.second ?: 0) to index + 1 }
            .runningFold(1 to 0) { acc, pair -> (acc.first + pair.first) to pair.second }
            .fold(mutableListOf<String>() to 0) { (row, spritePos), (x, pos) ->
                (if ((pos % 40) in (spritePos - 1..spritePos + 1)) "#" else ".").let { row.add(it) }
                row to x
            }
            .first
            .dropLast(1)
            .chunked(40)
            .joinToString("\n") {
                it.joinToString("")
            }

    val testInput = readInputLines("Day10_test")
    check(part1(testInput) == 13140)
    val testOutputPart2 = readInput("Day10_test2")
    check(part2(testInput) == testOutputPart2)

    val input = readInputLines("Day10")
    println(part1(input))
    println(part2(input))
}
