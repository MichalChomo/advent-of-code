package eu.michalchomo.adventofcode.year2023

val stringNumbersToIntegers = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun main() {
    fun part1(input: List<String>): Int =
        input.filter { it.isNotEmpty() }
            .sumOf { line ->
                (line.dropWhile { !it.isDigit() }.first().toString() + line.reversed().dropWhile { !it.isDigit() }
                    .first().toString()).toInt()
            }

    fun part2(input: List<String>): Int = part1(
        input.map { line ->
            var newLine = line
            while (true) {
                val foundDigitString = newLine.findAnyOf(stringNumbersToIntegers.keys)?.second
                if (foundDigitString != null) {
                    newLine = newLine.replace(foundDigitString, stringNumbersToIntegers[foundDigitString].toString())
                } else {
                    newLine = newLine
                        .replace("2ne", "21")
                        .replace("8wo", "82")
                        .replace("1ight", "18")
                        .replace("3ight", "38")
                        .replace("5ight", "58")
                        .replace("9ight", "98")
                    return@map newLine
                }
            }
            "" // to satisfy compiler, map must return String
        }
    )

    val testInput = readInputLines("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInputLines("Day01_test_part2")
    check(part2(testInput2) == 281)

    val input = readInputLines("Day01")
    println(part1(input))
    println(part2(input))
}
