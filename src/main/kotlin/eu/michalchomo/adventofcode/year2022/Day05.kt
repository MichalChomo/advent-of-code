package eu.michalchomo.adventofcode.year2022



fun main() {
    fun List<ArrayDeque<String>>.printStacks() {
        val copy = this.toList().map { ArrayDeque(it) }
        println(
            (0 until (copy.maxOfOrNull { it.size } ?: 0)).joinToString("\n") {
                copy.joinToString { s -> if (s.isNotEmpty()) s.removeLast() else "   " }
            }
        )
    }

    fun List<ArrayDeque<String>>.toMessage() = this.joinToString("") { it.first().trim('[', ']') }

    fun List<String>.toStacks(): List<ArrayDeque<String>> =
        this.asSequence()
            .takeWhile { !it.contains("1") }
            .fold(mutableListOf<ArrayDeque<String>>().also { listOfStacks ->
                repeat(this.first().length / 4 + 1) { listOfStacks.add(ArrayDeque()) }
            }) { acc, it ->
                it.chunked(4)
                    .asSequence()
                    .mapIndexed { index, s -> index to s }
                    .filter { it.second.isNotBlank() }
                    .forEach { (i, s) -> acc[i].add(s.trim()) }
                acc
            }

    fun List<String>.toProcedures(): List<Triple<Int, Int, Int>> =
        this.asSequence()
            .dropWhile { !it.contains("move") }
            .map {
                it.split(" ")
                    .asSequence()
                    .filter { it.toIntOrNull() != null }
                    .map { it.toInt() }
            }
            .map { it.take(3).toList().let { Triple(it[0], it[1] - 1, it[2] - 1) } }
            .toList()

    fun part1(input: List<String>): String {
        val stacks = input.toStacks()
        input.toProcedures().forEach { (count, from, to) ->
            repeat(count) {
                if (stacks[from].isNotEmpty()) {
                    stacks[to].addFirst(stacks[from].removeFirst())
                }
            }
        }

        return stacks.toMessage()
    }

    fun part2(input: List<String>): String {
        val stacks = input.toStacks()
        input.toProcedures().forEach { (count, from, to) ->
            if (count < 2) {
                stacks[to].addFirst(stacks[from].removeFirst())
            } else {
                stacks[from].slice(0 until count).reversed().forEach {
                    stacks[to].addFirst(it)
                    stacks[from].removeFirst()
                }
            }
        }

        return stacks.toMessage()
    }

    val testInput = readInputLines("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputLines("Day05")
    println(part1(input))
    println(part2(input))
}
