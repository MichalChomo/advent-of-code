package eu.michalchomo.adventofcode.year2022



data class Monkey(
    private val items: MutableList<Long>,
    private val operation: (Long) -> Long,
    private val modulus: Long,
    private val monkeyIfTrue: Int,
    private val monkeyIfFalse: Int
) {
    var itemsInspected: Long = 0

    fun turn(monkeys: List<Monkey>, secondOperation: (Long) -> Long) {
        items.map(operation)
            .map(secondOperation)
            .forEach { worryLevel ->
                itemsInspected++
                monkeys[test(worryLevel)].items.add(worryLevel)
            }
        items.clear()
    }

    private fun test(worryLevel: Long): Int = if (worryLevel % modulus == 0L) monkeyIfTrue else monkeyIfFalse

}

fun main() {

    fun List<String>.toLongFunction(): (Long, Long) -> Long =
        if (this[1] == "*") { a, b -> a.times(b) }
        else { a, b -> a.plus(b) }

    fun List<String>.toOperation(): (Long) -> Long =
        { a ->
            toLongFunction()(
                a,
                if (this[2] != "old") this[2].toLong() else a
            )
        }

    fun String.lowestCommonMultipleOfModulo(): Long = split("\n")
        .asSequence()
        .filter { it.contains("divisible by") }
        .map { it.split(" ").last().toLong() }
        .reduce(Long::times)

    fun String.toMonkeys(): List<Monkey> = split("\n\n").map {
        it.split("\n")
            .filter { it.isNotEmpty() }
            .let { monkeyString ->
                Monkey(
                    monkeyString[1].substringAfter(":").split(",").map { it.trim().toLong() }.toMutableList(),
                    monkeyString[2].substringAfter("=").trim().split(" ").let { it.toOperation() },
                    monkeyString[3].substringAfterLast(" ").toLong(),
                    monkeyString[4].substringAfterLast(" ").toInt(),
                    monkeyString[5].substringAfterLast(" ").toInt()
                )
            }
    }

    fun List<Monkey>.round(secondOperation: (Long) -> Long): List<Monkey> =
        apply { forEach { it.turn(this, secondOperation) } }

    fun List<Monkey>.monkeyBusiness(): Long = map { it.itemsInspected }
        .sortedDescending()
        .take(2)
        .reduce(Long::times)

    fun part1(input: String): Long = input.toMonkeys()
        .apply { repeat(20) { round { it / 3 } } }
        .monkeyBusiness()

    fun part2(input: String): Long = input.toMonkeys()
        .apply {
            input.lowestCommonMultipleOfModulo().let { lcm ->
                repeat(10000) {
                    round { it.mod(lcm) }
                }
            }
        }
        .monkeyBusiness()

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
