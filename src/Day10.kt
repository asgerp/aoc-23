import kotlin.system.measureTimeMillis

fun main() {
    fun getNextCoord(
        prev: Char,
        currentCoord: Pair<Int, Int>,
        prevCoord: Pair<Int, Int>,
    ): Pair<Int, Int> {
        return when (prev) {
            'J' -> {
                if (currentCoord.second > prevCoord.second) {
                    currentCoord.first - 1 to currentCoord.second
                } else if (currentCoord.first > prevCoord.first) {
                    currentCoord.first to currentCoord.second - 1
                } else {
                    currentCoord
                }
            }
            '7' -> {
                if (currentCoord.second > prevCoord.second) {
                    currentCoord.first + 1 to currentCoord.second
                } else if (currentCoord.first < prevCoord.first) {
                    currentCoord.first to currentCoord.second - 1
                } else {
                    currentCoord
                }
            }
            'F' -> {
                if (currentCoord.first < prevCoord.first) {
                    currentCoord.first to currentCoord.second + 1
                } else if (currentCoord.second < prevCoord.second) {
                    currentCoord.first + 1 to currentCoord.second
                } else {
                    currentCoord
                }
            }
            'L' -> {
                if (currentCoord.first > prevCoord.first) {
                    currentCoord.first to currentCoord.second + 1
                } else if (currentCoord.second < prevCoord.second) {
                    currentCoord.first - 1 to currentCoord.second
                } else {
                    currentCoord
                }
            }
            '|' -> {
                if (currentCoord.first < prevCoord.first) {
                    currentCoord.first - 1 to currentCoord.second
                } else if (currentCoord.first > prevCoord.first) {
                    currentCoord.first + 1 to currentCoord.second
                } else {
                    currentCoord
                }
            }
            '-' ->
                if (currentCoord.second < prevCoord.second) {
                    currentCoord.first to currentCoord.second - 1
                } else if (currentCoord.second > prevCoord.second) {
                    currentCoord.first to currentCoord.second + 1
                } else {
                    currentCoord
                }

            else -> currentCoord
        }
    }

    fun getCharAt(
        x: Int,
        y: Int,
        input: List<String>,
    ): Char {
        return input[x][y]
    }

    fun part1(input: List<String>): Int {
        var start = 0 to 0
        input.forEachIndexed { index, s ->
            if (s.contains("S")) {
                val sIndex = s.indexOf('S')
                start = index to sIndex
                return@forEachIndexed
            }
        }
        input.forEachIndexed { index, s ->
            s.toCharArray().forEachIndexed { i, c ->
                // print("[{$c}: $index,$i] ")
            }
            // println()
        }
        // println(path)
        // start.println()
        val startingPoint = start.first - 1 to start.second
        var nextChar = getCharAt(startingPoint.first, startingPoint.second, input)
        var prev = start
        var currentCoord = startingPoint
        var steps = 1
        while (nextChar != 'S') {
            val nextCoord = getNextCoord(nextChar, currentCoord, prev)
            nextChar = getCharAt(nextCoord.first, nextCoord.second, input)
            prev = currentCoord
            currentCoord = nextCoord
            // println("current char: $nextChar, at $prev going to $currentCoord step: $steps")
            steps++
        }
        return steps / 2
    }

    fun part2(input: List<String>): Int {
        var start = 0 to 0
        input.forEachIndexed { index, s ->
            if (s.contains("S")) {
                val sIndex = s.indexOf('S')
                start = index to sIndex
                return@forEachIndexed
            }
        }
        val path = mutableListOf<MutableList<Char>>()
        input.forEachIndexed { index, s ->
            val h = mutableListOf<Char>()
            s.toCharArray().forEachIndexed { i, c ->
                // print("[{$c}: $index,$i] ")
                h.add(i, 'Ø')
            }
            path.add(index, h)
            // println()
        }
        // println(path)
        // start.println()
        path[start.first][start.second] = 'S'
        val startingPoint = start.first + 1 to start.second
        var nextChar = getCharAt(startingPoint.first, startingPoint.second, input)
        var prev = start
        var currentCoord = startingPoint
        var steps = 1
        path[startingPoint.first][startingPoint.second] = nextChar
        while (nextChar != 'S') {
            val nextCoord = getNextCoord(nextChar, currentCoord, prev)
            nextChar = getCharAt(nextCoord.first, nextCoord.second, input)
            prev = currentCoord
            currentCoord = nextCoord
            path[nextCoord.first][nextCoord.second] = nextChar
            // println("current char: $nextChar, at $prev going to $currentCoord step: $steps")
            steps++
        }
        path[start.first][start.second] = '|'
        var inside: Boolean
        var counter = 0
        path.forEach {
            var prevPipe = 'Ø'
            inside = false
            it.forEach { c ->
                when (c) {
                    '|' -> {
                        inside = !inside
                        prevPipe = c
                    }
                    'L' -> {
                        prevPipe = c
                    }
                    'F' -> {
                        prevPipe = c
                    }
                    'J' -> {
                        if (prevPipe == 'F') {
                            inside = !inside
                        }
                        prevPipe = c
                    }
                    '-' -> {
                    }
                    '7' -> {
                        if (prevPipe == 'L') {
                            inside = !inside
                        }
                        prevPipe = c
                    }
                    'Ø' -> {
                        if (inside) {
                            counter++
                        }
                    }
                }
            }
        }
        println("c = $counter")
        return counter
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day10")
            val part1Result = part1(input)
            part1Result.println()
            check(part1Result == 6738)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day10")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 908)
        }
    println("part2 took: $elapsed2")
}
