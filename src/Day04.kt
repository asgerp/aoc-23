import kotlin.system.measureTimeMillis

fun main() {


    fun part1(input: List<String>): Int {
        var result = 0
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        return result
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day04")
            val part1Result = part1(input)
            part1Result.println()
            //check(part1Result == 533784)
        }
    println(elapsed1)
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day04_02")
            val part2Result = part2(input2)
            part2Result.println()
            //check(part2Result == 78826761)
        }
    println(elapsed2)
}
