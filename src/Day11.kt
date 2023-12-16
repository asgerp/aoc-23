import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {

    fun calculateGalaxyDistances(galaxies: List<Pair<Int, Int>>): Pair<Int, List<Int>> {
        val searchList = galaxies.toMutableList()
        var result = 0
        val distances = mutableListOf<Int>()
        while (searchList.isNotEmpty()) {
            val current = searchList[0]
            searchList.forEachIndexed { index, _ ->

                if (index + 1 == searchList.size) return@forEachIndexed
                val nextGalaxy = searchList[index + 1]
                val distance = abs(nextGalaxy.first - current.first) + abs(nextGalaxy.second - current.second)
                distances.add(distance)
                result += distance
            }
            // println()
            searchList.removeAt(0)
        }

        return result to distances
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

        val galaxies = getGalaxyCoordinates(fullyExpanded)
        return calculateGalaxyDistances(galaxies).first
    }

    fun calDistDif(
        orig: List<Int>,
        expanded: List<Int>,
        factor: Int,
    ): Long {
        var result = 0L
        orig.forEachIndexed { index, dist ->
            val d = expanded[index] - dist
            result += dist + (factor * d) - d
        }
        return result
    }

    fun part2(input: List<String>): Long {
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

        val imageGalaxies = getGalaxyCoordinates(image)
        val orig = calculateGalaxyDistances(imageGalaxies)

        val galaxies = getGalaxyCoordinates(fullyExpanded)
        val expanded = calculateGalaxyDistances(galaxies)

        return calDistDif(orig.second, expanded.second, 1000000)
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
            val input2 = readInput("Day11")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 908)
        }
    println("part2 took: $elapsed2")
}
