import kotlin.system.measureTimeMillis

private const val FIVE_OF_A_KIND = 1
private const val FOUR_OF_A_KIND = 2
private const val FULL_HOUSE = 3
private const val THREE_OF_A_KIND = 4
private const val TWO_PAIR = 5
private const val ONE_PAIR = 6
private const val HIGH_CARD = 7

fun main() {
    fun cardToNumber(c: Char): Int {
        return when (c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            '9' -> 9
            '8' -> 8
            '7' -> 7
            '6' -> 6
            '5' -> 5
            '4' -> 4
            '3' -> 3
            '2' -> 2
            else -> {
                0
            }
        }
    }

    fun cardToNumberJoker(c: Char): Int {
        return when (c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            '9' -> 9
            '8' -> 8
            '7' -> 7
            '6' -> 6
            '5' -> 5
            '4' -> 4
            '3' -> 3
            '2' -> 2
            else -> {
                0
            }
        }
    }

    fun whatHand(hand: String): Int {
        val grouped = hand.toList().groupingBy { it }.eachCount()
        if (grouped.size == 1) {
            return 1
        }
        if (grouped.values.contains(4)) {
            return 2
        }
        if (grouped.values.contains(3)) {
            if (grouped.values.contains(2)) {
                return 3
            }
            return 4
        }

        if (grouped.values.contains(2)) {
            if (grouped.size == 3) {
                return 5
            }
            return 6
        }
        return 7
    }

    fun whatHandJoker(hand: String): Int {
        val grouped = hand.toList().groupingBy { it }.eachCount()
        val jokers = grouped.getOrDefault('J', 0)
        if (grouped.size == 1) {
            return FIVE_OF_A_KIND
        }
        if (grouped.values.contains(4)) {
            if (jokers > 0) {
                return FIVE_OF_A_KIND
            }
            return FOUR_OF_A_KIND
        }
        if (grouped.values.contains(3)) {
            if (grouped.values.contains(2)) {
                if (jokers != 0) {
                    return FIVE_OF_A_KIND
                }
                return FULL_HOUSE
            }
            if (jokers == 1) {
                return FOUR_OF_A_KIND
            }
            if (jokers == 3 )return FOUR_OF_A_KIND
            return THREE_OF_A_KIND
        }

        if (grouped.values.contains(2)) {
            if (grouped.size == 3) {
                if (jokers == 1) {
                    return FULL_HOUSE
                }
                if (jokers == 2) {
                    return FOUR_OF_A_KIND
                }
                return TWO_PAIR
            }
            if (jokers == 1) {
                return THREE_OF_A_KIND
            }
            if (jokers == 2) {
                return THREE_OF_A_KIND
            }
            return ONE_PAIR
        }
        if (hand.contains("J")) {
            return ONE_PAIR
        }
        return HIGH_CARD
    }

    fun sortHands(list: MutableList<Pair<String, Int>>) =
        list.sortedWith(
            compareBy<Pair<String, Int>> { h ->
                cardToNumber(h.first[0])
            }.thenBy { h ->
                cardToNumber(h.first[1])
            }.thenBy { h ->
                cardToNumber(h.first[2])
            }.thenBy { h ->
                cardToNumber(h.first[3])
            }.thenBy { h -> cardToNumber(h.first[4]) },
        )

    fun sortHandsJoker(list: MutableList<Pair<String, Int>>) =
        list.sortedWith(
            compareBy<Pair<String, Int>> { h ->
                cardToNumberJoker(h.first[0])
            }.thenBy { h ->
                cardToNumberJoker(h.first[1])
            }.thenBy { h ->
                cardToNumberJoker(h.first[2])
            }.thenBy { h ->
                cardToNumberJoker(h.first[3])
            }.thenBy { h -> cardToNumberJoker(h.first[4]) },
        )

    fun totalWinnings(listHigh: MutableList<Pair<String, Int>>) =
        listHigh.mapIndexed { index, pair ->
            println("${pair.first} : ${(index + 1) * pair.second}")
            (index + 1) * pair.second
        }.reduce(Int::plus)

    fun part1(input: List<String>): Int {
        val listFive = mutableListOf<Pair<String, Int>>() // 1
        val listFour = mutableListOf<Pair<String, Int>>() // 2
        val listFullHouse = mutableListOf<Pair<String, Int>>() // 3
        val listThree = mutableListOf<Pair<String, Int>>() // 4
        val listTwoPair = mutableListOf<Pair<String, Int>>() // 5
        val listOnePair = mutableListOf<Pair<String, Int>>() // 6
        val listHigh = mutableListOf<Pair<String, Int>>() // 7

        input.forEach {
            val hand = it.split(" ")[0]

            val price = it.split(" ")[1].toInt()
            val handMapping = whatHand(hand)
            when (handMapping) {
                1 -> listFive.add(hand to price)
                2 -> listFour.add(hand to price)
                3 -> listFullHouse.add(hand to price)
                4 -> listThree.add(hand to price)
                5 -> listTwoPair.add(hand to price)
                6 -> listOnePair.add(hand to price)
                7 -> listHigh.add(hand to price)
                else -> {
                    println("nooo")
                }
            }
            /*hand.println()
            price.println()*/
        }
        val l1 = sortHands(listHigh).toMutableList()
        l1.addAll(sortHands(listOnePair))
        l1.addAll(sortHands(listTwoPair))
        l1.addAll(sortHands(listThree))
        l1.addAll(sortHands(listFullHouse))
        l1.addAll(sortHands(listFour))
        l1.addAll(sortHands(listFive))
        return totalWinnings(l1)
    }

    fun part2(input: List<String>): Int {
        val listFive = mutableListOf<Pair<String, Int>>() // 1
        val listFour = mutableListOf<Pair<String, Int>>() // 2
        val listFullHouse = mutableListOf<Pair<String, Int>>() // 3
        val listThree = mutableListOf<Pair<String, Int>>() // 4
        val listTwoPair = mutableListOf<Pair<String, Int>>() // 5
        val listOnePair = mutableListOf<Pair<String, Int>>() // 6
        val listHigh = mutableListOf<Pair<String, Int>>() // 7

        input.forEach {
            val hand = it.split(" ")[0]

            val price = it.split(" ")[1].toInt()
            val handMapping = whatHandJoker(hand)
            // if (hand.contains("J")) println("$hand + $handMapping")
            when (handMapping) {
                1 -> listFive.add(hand to price)
                2 -> listFour.add(hand to price)
                3 -> listFullHouse.add(hand to price)
                4 -> listThree.add(hand to price)
                5 -> listTwoPair.add(hand to price)
                6 -> listOnePair.add(hand to price)
                7 -> listHigh.add(hand to price)
                else -> {
                    println("nooo")
                }
            }
            /*hand.println()
            price.println()*/
        }
        val l1 = sortHandsJoker(listHigh).toMutableList()
        sortHandsJoker(listFive).println()
        l1.addAll(sortHandsJoker(listOnePair))
        l1.addAll(sortHandsJoker(listTwoPair))
        l1.addAll(sortHandsJoker(listThree))
        l1.addAll(sortHandsJoker(listFullHouse))
        l1.addAll(sortHandsJoker(listFour))
        l1.addAll(sortHandsJoker(listFive))
        l1.println()
        return totalWinnings(l1)
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day07")
            val part1Result = part1(input)
            part1Result.println()
            // check(part1Result == 20855)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day07_02")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 251735672)
        }
    println("part2 took: $elapsed2")
}
