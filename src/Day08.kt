import kotlin.system.measureTimeMillis

fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day08")
            val part1Result = part1(input)
            part1Result.println()
            // check(part1Result == 20855)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day08_02")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 251735672)
        }
    println("part2 took: $elapsed2")
}
