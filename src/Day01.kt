import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { s ->
            val digits = s.filter { it.isDigit() }.let { d -> "${d.first()}${d.last()}" }
            result += digits.toInt()
        }
        return result
    }

    val textToNumberMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part2(input: List<String>): Int {
        val sum = input.fold(0) { accumulator,  line ->
            val foundNumbersAtIndex = mutableListOf<Pair<Int, Int>>()
            line.firstOrNull { it.isDigit() }.let {
                val firstIndex = line.indexOfFirst { it.isDigit() }
                foundNumbersAtIndex.add(it.toString().toInt() to firstIndex)
            }
            line.lastOrNull { it.isDigit() }.let {
                val lastIndex = line.indexOfLast { it.isDigit() }
                foundNumbersAtIndex.add(it.toString().toInt() to lastIndex)
            }

            for ((index, _) in line.withIndex()) {
                if (index + 3 > line.length) continue
                textToNumberMap[line.substring(index, index + 3)]?.let {
                    foundNumbersAtIndex.add(it to index)
                }
                if (index + 4 > line.length) continue
                textToNumberMap[line.substring(index, index + 4)]?.let {
                    foundNumbersAtIndex.add(it to index)
                }
                if (index + 5 > line.length) continue
                textToNumberMap[line.substring(index, index + 5)]?.let {
                    foundNumbersAtIndex.add(it to index)
                }
            }
            accumulator + (foundNumbersAtIndex.minBy { it.second }.first.toString() + "" +
                    foundNumbersAtIndex.maxBy { it.second }.first.toString()).toInt()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    val elapsed = measureTimeMillis {
        val input2 = readInput("Day01_02")
        part2(input2).println()
        check(part2(input2) == 54087)
    }
    println(elapsed)
}
