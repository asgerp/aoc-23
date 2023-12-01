fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { s ->
            val digs = s.filter { it.isDigit() }
            val digits = "${digs.first()}${digs.last()}"
            result += digits.toInt()
            println("found ${digs.first()}${digs.last()}")
            println("orig $digs")
        }
        return result
    }

    val textToNumberMap = mapOf(
        "One" to 1,
        "Two" to 2,
        "Three" to 3,
        "Four" to 4,
        "Five" to 5,
        "Six" to 6,
        "Seven" to 7,
        "Eight" to 8,
        "Nine" to 9
    )

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    val input2 = readInput("Day01_02")
    part2(input2).println()
}
