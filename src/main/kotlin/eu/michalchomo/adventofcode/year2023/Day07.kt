package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day07 : Day {

    override val number: Int = 7

    private val cardsOrder = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    private val cardsOrderJoker = cardsOrder.toMutableList().apply { remove('J'); addFirst('J') }

    override fun part1(input: List<String>): String = input.totalWinnings(cardsOrder, ::toType)

    override fun part2(input: List<String>): String = input.totalWinnings(cardsOrderJoker, ::toTypeJoker)

    private fun List<String>.totalWinnings(cardsOrder: List<Char>, toTypeFun: (String) -> Type) =
        this.map { it.toHand(toTypeFun) }
            .sortedWith { a, b ->
                val typeCompare = a.type.compareTo(b.type)
                if (typeCompare != 0) {
                    typeCompare
                } else {
                    a.cards.zip(b.cards).asSequence()
                        .map { (ac, bc) -> cardsOrder.indexOf(ac).compareTo(cardsOrder.indexOf(bc)) }
                        .filter { it != 0 }.firstOrNull() ?: 0
                }
            }
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
            .toString()

    data class Hand(val cards: String, val bid: Int, val type: Type)

    private fun String.toHand(toTypeFun: (String) -> Type): Hand =
        this.split(" ").let { Hand(it[0], it[1].toInt(), toTypeFun(it[0])) }

    enum class Type {
        HIGH_CARD, ONE_PAIR, TWO_PAIRS, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
    }

    private fun toType(cards: String): Type {
        val cardsCount = cards.groupBy { it }.values.map { it.size }.sortedDescending()
        return when (cardsCount) {
            listOf(5) -> Type.FIVE_OF_A_KIND
            listOf(4, 1) -> Type.FOUR_OF_A_KIND
            listOf(3, 2) -> Type.FULL_HOUSE
            listOf(3, 1, 1) -> Type.THREE_OF_A_KIND
            listOf(2, 2, 1) -> Type.TWO_PAIRS
            listOf(2, 1, 1, 1) -> Type.ONE_PAIR
            else -> Type.HIGH_CARD
        }
    }

    private fun toTypeJoker(cards: String): Type {
        if ('J' !in cards) return toType(cards)
        val cardsCount = cards.groupingBy { it }.eachCount().values.sortedDescending()
        return if (cardsCount.size < 3) {
            // [5], [4, 1], [3, 2]
            Type.FIVE_OF_A_KIND
        } else {
            when (cardsCount) {
                listOf(3, 1, 1) -> Type.FOUR_OF_A_KIND
                listOf(2, 2, 1) -> if (cards.jokersCount() == 2) Type.FOUR_OF_A_KIND else Type.FULL_HOUSE
                listOf(2, 1, 1, 1) -> Type.THREE_OF_A_KIND
                else -> Type.ONE_PAIR
            }
        }
    }

    private fun String.jokersCount(): Int = this.count { it == 'J' }

}

fun main() {
    main(Day07)
}
