import kotlin.system.measureTimeMillis

fun main() {
    fun calculateLCM(numbers: List<Long>): Long {
        require(numbers.isNotEmpty()) { "List must not be empty" }

        // Function to calculate the GCD of two numbers
        fun calculateGCD(
            a: Long,
            b: Long,
        ): Long {
            return if (b == 0L) a else calculateGCD(b, a % b)
        }

        // Function to calculate the LCM of two numbers
        fun calculateLCMOfTwo(
            a: Long,
            b: Long,
        ): Long {
            return if (a == 0L || b == 0L) 0 else (a * b) / calculateGCD(a, b)
        }

        // Calculate LCM for the entire list
        return numbers.reduce { acc, next -> calculateLCMOfTwo(acc, next) }
    }

    fun part1(input: List<String>): Int {
        val directions = input[0].toCharArray().toList()
        val nodes = mutableMapOf<String, Pair<String, String>>()
        input.drop(2).forEach {
            val node = it.split("=")[0].trim()
            val stepLst =
                it.split("=")[1]
                    .replace("(", "")
                    .replace(")", "")
                    .split(",")
            val nextSteps = Pair(stepLst[0].trim(), stepLst[1].trim())
            nodes[node] = nextSteps
        }
        var start = "AAA"
        var steps = 0
        var notFound = true
        while (notFound) {
            directions.forEach { direction ->
                if (!notFound) return@forEach
                val node = nodes[start]
                if (node != null) {
                    if (start == "ZZZ") {
                        notFound = false
                        return@forEach
                    }
                    val (l, r) = node
                    if (direction == 'L') start = l
                    if (direction == 'R') start = r
                    steps++
                }
            }
        }
        println(nodes)
        return steps
    }

    fun part2(input: List<String>): Long {
        val directions = input[0].toCharArray().toList()
        val nodes = mutableMapOf<String, Pair<String, String>>()
        input.drop(2).forEach {
            val node = it.split("=")[0].trim()
            val stepLst =
                it.split("=")[1]
                    .replace("(", "")
                    .replace(")", "")
                    .split(",")
            val nextSteps = Pair(stepLst[0].trim(), stepLst[1].trim())
            nodes[node] = nextSteps
        }
        val allSteps = mutableListOf<Int>()
        val listOfStarts = listOf("BBA", "BLA", "AAA", "NFA", "DRA", "PSA")
        listOfStarts.forEach {
            var start = it
            var steps = 0
            var notFound = true
            while (notFound) {
                directions.forEach inner@{ direction ->
                    val node = nodes[start]
                    if (node != null) {
                        if (start.endsWith("Z")) {
                            notFound = false
                            allSteps.add(steps)
                            return@forEach
                        }
                        val (l, r) = node
                        if (direction == 'L') start = l
                        if (direction == 'R') start = r
                        steps++
                    }
                }
            }
            allSteps.add(steps)
        }
        println(nodes)
        println(allSteps)
        val yeah = calculateLCM(allSteps.map { it.toLong() })
        return yeah
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
