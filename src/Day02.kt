import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val redMax = 12
        val greenMax = 13
        val blueMax = 14

        var result = 0
        input.forEach { s ->
            val reg = "Game (?<gameNo>\\d+):".toRegex()
            val gameNo = reg.find(s)?.groups?.get("gameNo")?.value?.toInt() ?: 0
            val regRed = "(?<red>\\d+) red".toRegex()
            val regBlue = "(?<blue>\\d+) blue".toRegex()
            val regGreen = "(?<green>\\d+) green".toRegex()
            val red = regRed.findAll(s).map { it.groups["red"]?.value?.toInt() }.filterNotNull().maxBy { it }
            val blue = regBlue.findAll(s).map { it.groups["blue"]?.value?.toInt() }.filterNotNull().maxBy { it }
            val green = regGreen.findAll(s).map { it.groups["green"]?.value?.toInt() }.filterNotNull().maxBy { it }
            if (red <= redMax && green <= greenMax && blue <= blueMax) {
                result += gameNo
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEach { s ->
            val regRed = "(?<red>\\d+) red".toRegex()
            val regBlue = "(?<blue>\\d+) blue".toRegex()
            val regGreen = "(?<green>\\d+) green".toRegex()
            val red = regRed.findAll(s).map { it.groups["red"]?.value?.toInt() }.filterNotNull().maxBy { it }
            val blue = regBlue.findAll(s).map { it.groups["blue"]?.value?.toInt() }.filterNotNull().maxBy { it }
            val green = regGreen.findAll(s).map { it.groups["green"]?.value?.toInt() }.filterNotNull().maxBy { it }
            result += red * blue * green
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day02")
            part1(input).println()
        }
    println(elapsed1)
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day02_02")
            part2(input2).println()
        }
    println(elapsed2)
}
