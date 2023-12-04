import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun main() {

    fun powerOfTwo(n: Int): Int {
        return 2.0.pow(n).toInt()
    }
    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { s ->
            val line = s.split(":")
            val numbers = line[1].split("|")
            val winners = numbers[0].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val ticket = numbers[1].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            var power = 0
            winners.forEach {
                if(ticket.contains(it)) {
                    power++
                }
            }
            if (power > 0) {
                result += powerOfTwo(power - 1)
            }
        }
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
            check(part1Result == 20855)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day04_02")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 78826761)
        }
    println("part2 took: $elapsed2")
}
