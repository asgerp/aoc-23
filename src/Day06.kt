import kotlin.system.measureTimeMillis

fun main() {
    fun holdLetGo(
        hold: Int,
        time: Int,
    ): Int {
        return hold * (time - hold)
    }

    fun part1(input: List<String>): Int {
        val list = mutableListOf<Int>()
        val time = input[0].split(":")[1].trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val distance = input[1].split(":")[1].trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        time.forEachIndexed { i, t ->
            var localTime = 0
            var found = 0
            while (localTime < t) {
                val r = holdLetGo(localTime, t)
                if (r > distance[i]) {
                    found++
                }
                localTime++
            }
            if (found > 0) list.add(found)
        }
        println(list)
        return list.reduce(Int::times)
    }

    fun holdLetGoLong(
        hold: Int,
        time: Long,
    ): Long {
        return hold * (time - hold)
    }

    fun part2(input: List<String>): Int {
        val list = mutableListOf<Int>()
        val time = input[0].split(":")[1].trim().filterNot { it.isWhitespace() }.toLong()
        val distance = input[1].split(":")[1].trim().filterNot { it.isWhitespace() }.toLong()

        var localTime = 0
        var found = 0
        while (localTime < time) {
            val r = holdLetGoLong(localTime, time)
            if (r > distance) {
                found++
            }
            localTime++
        }
        if (found > 0) list.add(found)
        println(list)
        return list.reduce(Int::times)
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day06")
            val part1Result = part1(input)
            part1Result.println()
            // check(part1Result == 20855)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day06_02")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 5489600)
        }
    println("part2 took: $elapsed2")
}
