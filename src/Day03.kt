fun main() {
    val itemTypeToPriority = CharRange('a', 'z').toList().plus(CharRange('A', 'Z')).zip(IntRange(1, 52).toList()).toMap()

    fun part1(input: List<String>): Int = input.sumOf {
        itemTypeToPriority[
                it.slice(0 until it.length / 2).toSet()
                    .intersect(it.slice((it.length / 2) until it.length).toSet())
                    .first()
        ]!!
    }

    fun part2(input: List<String>): Int = input.chunked(3).sumOf {
        itemTypeToPriority[
                it.map { rucksack -> rucksack.toSet() }
                    .reduce { acc, r -> acc.intersect(r) }
                    .first()
        ]!!
    }

    val testInput = readInputLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInputLines("Day03")
    println(part1(input))
    println(part2(input))
}
