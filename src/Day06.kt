fun main() {
    fun String.findPositionAfterDistinctSubstringOfSize(size: Int) = this.windowed(size, 1)
        .asSequence()
        .mapIndexed { index, s -> index to s }
        .filter { it.second.toSet().size == size }
        .first().first + size

    fun part1(input: String): Int = input.findPositionAfterDistinctSubstringOfSize(4)

    fun part2(input: String): Int = input.findPositionAfterDistinctSubstringOfSize(14)
    
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}