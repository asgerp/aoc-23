import kotlin.system.measureTimeMillis

fun main() {
    fun getDiffs(startingList: List<Int>): MutableList<Int> {
        val diffList = mutableListOf<Int>()
        startingList.forEachIndexed { index, i ->
            if (index + 1 == startingList.size) return@forEachIndexed
            diffList.add(
                startingList[index + 1] - startingList[index],
            )
        }
        return diffList
    }

    fun allZero(list: List<Int>): Boolean {
        return list.all { it == 0 }
    }

    fun calculateNextValue(historyValues: List<List<Int>>): Int {
        return historyValues.map { it.last() }.reduce { acc, i -> i + acc }
    }

    fun calculatePrevValue(historyValues: List<List<Int>>): Int {
        return historyValues.map { it.first() }.reversed().reduce { acc, i -> i - acc }
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val history = line.split(" ").map { it.toInt() }
            val differences = mutableListOf(history)
            var difference = getDiffs(history)
            differences.add(difference)
            while (!allZero(difference)) {
                difference = getDiffs(difference)
                differences.add(difference)
            }
            result += calculateNextValue(differences)
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val history = line.split(" ").map { it.toInt() }
            val differences = mutableListOf(history)
            var difference = getDiffs(history)
            differences.add(difference)
            while (!allZero(difference)) {
                difference = getDiffs(difference)
                differences.add(difference)
            }
            result += calculatePrevValue(differences)
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day09")
            val part1Result = part1(input)
            part1Result.println()
            check(part1Result == 1637452029)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day09_02")
            val part2Result = part2(input2)
            part2Result.println()
            check(part2Result == 908)
        }
    println("part2 took: $elapsed2")
}
