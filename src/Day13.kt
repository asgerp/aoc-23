import kotlin.system.measureTimeMillis

fun main() {
    fun transpose(list: List<String>): List<String> {
        val transposed = mutableListOf<String>()
        list[0].forEach { _ ->
            transposed.add("")
        }

        list.forEachIndexed { index, chars ->
            chars.toCharArray().forEachIndexed { charIndex, c ->
                transposed[charIndex] = transposed[charIndex].plus(c)
            }
        }
        return transposed
    }

    fun findReflection(
        input: List<String>,
        horizontal: Boolean = false,
    ): Int {
        var prevLine = ""
        var foundAtIndex = 0
        input.forEachIndexed { index, line ->
            if (line == prevLine) {
                var offSet = 1
                foundAtIndex = index
                while (true) {
                    // if we enter here a reflection has been found
                    if (index - offSet - 1 < 0 || index + offSet >= input.size) {
                        if (foundAtIndex > 0) {
                            return if (horizontal) {
                                (foundAtIndex) * 100
                            } else {
                                foundAtIndex
                            }
                        }
                        return 0
                    }
                    // if the lines differ break
                    if (input[index - offSet - 1] != input[index + offSet]) {
                        break
                    }
                    offSet++
                }
            }
            prevLine = line
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        val pattern = mutableListOf<String>()
        var result = 0
        input.forEach { line ->
            if (line == "") {
                result += findReflection(pattern, true)
                result += findReflection(transpose(pattern))
                pattern.clear()
            } else {
                pattern.add(line)
            }
        }
        result += findReflection(pattern, true)
        result += findReflection(transpose(pattern))

        return result
    }

    fun part2(input: List<String>): Int {
        val pattern = mutableListOf<String>()
        var result = 0
        input.forEach { line ->
            if (line == "") {
                result += findReflection(pattern, true)
                result += findReflection(transpose(pattern))
                pattern.clear()
            } else {
                pattern.add(line)
            }
        }
        result += findReflection(pattern, true)
        result += findReflection(transpose(pattern))

        return result
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day13")
            val part1Result = part1(input)
            part1Result.println()
            // check(part1Result == 9742154)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day13_test")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 908)
        }
    println("part2 took: $elapsed2")
}
