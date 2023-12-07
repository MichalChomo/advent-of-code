package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

typealias Card = Char

object Day07 : Day {

    override val number: Int = 7

    override fun part1(input: List<String>): Int = input.map {
        it.toHand()
    }.sorted().mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()

    override fun part2(input: List<String>): Int = input.map {
        it.toHand(true).toHandPart2()
    }.sorted().mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()

    open class Hand(
        val cards: List<Card>,
        val bid: Int,
        val type: Type,
    ) : Comparable<Hand> {

        override fun compareTo(other: Hand): Int = compareTo(other, false)

        fun compareTo(other: Hand, joker: Boolean = false): Int = when {
            this.type != other.type -> this.type.ordinal.compareTo(other.type.ordinal)
            else -> this.cards.zip(other.cards).asSequence()
                .map { (thisCard, otherCard) ->
                    if (joker) thisCard.compareJoker(otherCard) else thisCard.compare(otherCard)
                }.firstOrNull { it != 0 } ?: 0
        }

    }

    class HandPart2(cards: List<Card>, bid: Int, type: Type) : Hand(cards, bid, type) {
        override fun compareTo(other: Hand): Int = super.compareTo(other, true)
    }
    private fun Hand.toHandPart2() = HandPart2(this.cards, this.bid, this.type)

    private fun String.toHand(joker: Boolean = false): Hand = this.split(" ")
        .let {
            val cards = it[0].toCharArray().toList()
            val jokerCount = if (joker) cards.count { it == 'J' } else 0
            val bid = it[1].toInt()
            val cardsSet = cards.toSet()
            return when (cardsSet.size) {
                1 -> Hand(cards, bid, Type.FIVE_OF_A_KIND)
                2 -> if (jokerCount > 0) {
                    Hand(cards, bid, Type.FIVE_OF_A_KIND)
                } else if (cards.count { it == cardsSet.first() }.let { it == 1 || it == 4 }) {
                    Hand(cards, bid, Type.FOUR_OF_A_KIND)
                } else {
                    Hand(cards, bid, Type.FULL_HOUSE)
                }

                5 -> if (jokerCount == 1) {
                    Hand(cards, bid, Type.ONE_PAIR)
                } else {
                    Hand(cards, bid, Type.HIGH_CARD)
                }

                else -> when (jokerCount) {
                    0 -> when (cards.countPairs()) {
                            0 -> Hand(cards, bid, Type.THREE_OF_A_KIND)
                            1 -> Hand(cards, bid, Type.ONE_PAIR)
                            2 -> Hand(cards, bid, Type.TWO_PAIRS)
                            else -> throw IllegalStateException("Invalid input")
                        }

                    1 -> when (cardsSet.size) {
                        3 -> when (cards.countPairs()) {
                            0 -> if (cards.countThrees() == 0) {
                                Hand(cards, bid, Type.ONE_PAIR)
                            } else {
                                Hand(cards, bid, Type.FOUR_OF_A_KIND)
                            }
                            1 -> Hand(cards, bid, Type.FOUR_OF_A_KIND)
                            2 -> Hand(cards, bid, Type.FULL_HOUSE)
                            else -> throw IllegalStateException("Invalid input")
                        }
                        4 -> Hand(cards, bid, Type.THREE_OF_A_KIND)
                        else -> throw IllegalStateException("Invalid input")
                    }

                    2 -> when (cardsSet.size) {
                        3 -> when (cards.countPairs()) {
                            1 -> Hand(cards, bid, Type.FOUR_OF_A_KIND)
                            2 -> Hand(cards, bid, Type.FOUR_OF_A_KIND)
                            else -> throw IllegalStateException("Invalid input")
                        }
                        4 -> Hand(cards, bid, Type.THREE_OF_A_KIND)
                        else -> throw IllegalStateException("Invalid input")
                    }

                    3 -> Hand(cards, bid, Type.FOUR_OF_A_KIND)
                    else -> throw IllegalStateException("Invalid input")
                }
            }
        }

    enum class Type {
        HIGH_CARD, ONE_PAIR, TWO_PAIRS, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
    }

    private fun List<Card>.countPairs() = this.groupBy { it }.values.sumOf { if (it.size % 2 == 0) it.size / 2 else 0 }
    private fun List<Card>.countThrees() = this.groupBy { it }.values.count { it.size == 3 }

    private fun Card.compare(other: Card) = if (this.isDigit() && other.isDigit()) {
        this.code.compareTo(other.code)
    } else if (this.isDigit() && !other.isDigit()) {
        -1
    } else if (!this.isDigit() && other.isDigit()) {
        1
    } else {
        when (this) {
            'A' -> if (other == 'A') 0 else 1
            'K' -> if (other == 'A') -1 else if (other == 'K') 0 else 1
            'Q' -> if (other == 'A' || other == 'K') -1 else if (other == 'Q') 0 else 1
            'J' -> if (other == 'A' || other == 'K' || other == 'Q') -1 else if (other == 'J') 0 else 1
            'T' -> if (other == 'A' || other == 'K' || other == 'Q' || other == 'J') -1 else 0
            else -> throw IllegalStateException("Invalid input")
        }
    }

    private fun Card.compareJoker(other: Card) = if (this == 'J' || other == 'J') {
        if (this == 'J' && other == 'J') 0 else if (this == 'J') -1 else 1
    } else this.compare(other)

}

fun main() {
    main(Day07)
}
