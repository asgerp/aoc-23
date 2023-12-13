import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    fun getCharAt(
        x: Int,
        y: Int,
        input: List<String>,
    ): Char {
        return input[x][y]
    }

    fun calculateGalaxyDistances(galaxies: List<Pair<Int, Int>>): Int {
        val searchList = galaxies.toMutableList()
        var result = 0
        while (searchList.isNotEmpty()) {
            val current = searchList[0]
            searchList.forEachIndexed { index, _ ->

                if (index + 1 == searchList.size) return@forEachIndexed
                val nextGalaxy = searchList[index + 1]
                val distance = abs(nextGalaxy.first - current.first) + abs(nextGalaxy.second - current.second)
                // println(distance)
                result += distance
            }
            // println()
            searchList.removeAt(0)
        }

        return result
    }

    fun getGalaxyCoordinates(list: MutableList<MutableList<Char>>): List<Pair<Int, Int>> {
        val galaxyCoordinates = mutableListOf<Pair<Int, Int>>()
        list.forEachIndexed { index, chars ->
            chars.forEachIndexed { charIndex, c ->
                if (c == '#') {
                    galaxyCoordinates.add(index to charIndex)
                }
            }
        }
        return galaxyCoordinates
    }

    fun verticalExpansion(
        list: MutableList<MutableList<Char>>,
        adds: List<Int>,
    ): MutableList<MutableList<Char>> {
        val added = MutableList(list[1].size) { '.' }

        val newList = list.toMutableList()

        adds.forEachIndexed { index, i ->
            newList.add(i + index, added)
        }

        return newList
    }

    fun horizontalExpansion(
        list: MutableList<MutableList<Char>>,
        adds: List<Int>,
    ): MutableList<MutableList<Char>> {
        val newList = list.toMutableList().map { it.toMutableList() }.toMutableList()

        newList.forEachIndexed { lineIndex, chars ->
            adds.forEachIndexed { index, i ->

                newList[lineIndex].add(i + index, '.')
            }
        }
        return newList
    }

    fun transpose(list: MutableList<MutableList<Char>>): List<List<Char>> {
        val transposed = mutableListOf<MutableList<Char>>()
        list.forEach { _ ->
            val h = mutableListOf<Char>()
            transposed.add(h)
        }

        list.forEachIndexed { index, chars ->
            chars.forEachIndexed { charIndex, c ->
                transposed[charIndex].add(index, c)
            }
        }
        return transposed
    }

    fun part1(input: List<String>): Int {
        val image = mutableListOf<MutableList<Char>>()
        input.forEachIndexed { index, s ->
            val h = mutableListOf<Char>()
            s.toCharArray().forEachIndexed { i, c ->
                // print("$c ")
                h.add(i, c)
            }
            image.add(index, h)
            // println()
        }

        val vertInserts = mutableListOf<Int>()
        image.forEachIndexed { index, chars ->
            if (chars.all { it == '.' }) {
                vertInserts.add(index)
            }
        }
        val t = transpose(image)

        val horizontalInserts = mutableListOf<Int>()
        t.forEachIndexed { index, chars ->
            if (chars.all { it == '.' }) {
                horizontalInserts.add(index)
            }
        }

        val verticallyExpanded = verticalExpansion(image, vertInserts)

        val fullyExpanded = horizontalExpansion(verticallyExpanded, horizontalInserts)
        // println("EXPANDED!")
        fullyExpanded.forEach {
            // println(it)
        }

        val galaxies = getGalaxyCoordinates(fullyExpanded)
        return calculateGalaxyDistances(galaxies)
    }

    fun part2(input: List<String>): Int {
        val expansionFactor = 10

        val image = mutableListOf<MutableList<Char>>()
        input.forEachIndexed { index, s ->
            val h = mutableListOf<Char>()
            s.toCharArray().forEachIndexed { i, c ->
                print("$c ")
                h.add(i, c)
            }
            image.add(index, h)
            println()
        }

        val vertInserts = mutableListOf<Int>()
        image.forEachIndexed { index, chars ->
            if (chars.all { it == '.' }) {
                vertInserts.add(index)
            }
        }
        println(vertInserts)
        val t = transpose(image)

        val horizontalInserts = mutableListOf<Int>()
        t.forEachIndexed { index, chars ->
            if (chars.all { it == '.' }) {
                horizontalInserts.add(index)
            }
        }

        val verticallyExpanded = verticalExpansion(image, vertInserts)

        val fullyExpanded = horizontalExpansion(verticallyExpanded, horizontalInserts)
        println("EXPANDED!")
        fullyExpanded.forEach {
            println(it)
        }

        val galaxies = getGalaxyCoordinates(fullyExpanded)
        return calculateGalaxyDistances(galaxies)
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day11")
            val part1Result = part1(input)
            part1Result.println()
            check(part1Result == 9742154)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day11_test")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 908)
        }
    println("part2 took: $elapsed2")
}
