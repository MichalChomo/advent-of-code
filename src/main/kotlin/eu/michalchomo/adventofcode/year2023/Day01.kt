package eu.michalchomo.adventofcode.year2023

val stringDigitsToIntegers = mapOf(
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

data class IndexedDigitString(val index: Int, val string: String) {
    fun isInString(str: String): Boolean = str.substring(index).startsWith(string)
}
fun Pair<Int, String>.toDigitString() = IndexedDigitString(first, second)

fun main() {
    fun part1(input: List<String>): Int =
        input.filter { it.isNotEmpty() }
            .sumOf { line ->
                (line.dropWhile { !it.isDigit() }.first().toString() + line.reversed().dropWhile { !it.isDigit() }
                    .first().toString()).toInt()
            }

    fun part2(input: List<String>): Int = part1(
        input.map { line ->
            val foundIndexedDigitStrings = mutableListOf(line.findAnyOf(stringDigitsToIntegers.keys)?.toDigitString())
            while (foundIndexedDigitStrings.lastOrNull() != null) {
                val startIndex = foundIndexedDigitStrings.lastOrNull()!!.index + 1
                val foundPair = line.substring(startIndex)
                    .findAnyOf(stringDigitsToIntegers.keys)?.let { it.copy(first = it.first + startIndex) }
                    foundIndexedDigitStrings.add(foundPair?.toDigitString())
            }
            foundIndexedDigitStrings.filterNotNull()
                .foldRight(line) { foundDigitString, acc ->
                    val endIndexA = if (foundDigitString.isInString(acc)) {
                        // Non overlapping
                        foundDigitString.string.length
                    } else {
                        // Overlapping
                        acc.substring(foundDigitString.index).indexOfFirst { it.isDigit() }
                    }
                    acc.replaceRange(
                        foundDigitString.index,
                        foundDigitString.index + endIndexA,
                        stringDigitsToIntegers[foundDigitString.string].toString()
                    )
                }
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
