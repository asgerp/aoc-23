import kotlin.system.measureTimeMillis

fun main() {
    /*
     get all seeds into list
     build maps
     from, to, yields + input
     */

    fun part1(input: List<String>): Long {
        val seeds = input[0].split(":")[1].trim().split(" ").map { it.toLong() }
        //println("seeds: $seeds")
        val lowest = mutableListOf<Long>()
        var currentMap = ""
        seeds.forEach seed@{ seed ->
//            "next seed $seed".println()
            var location = seed
            input.forEach lines@{ s ->

                if (s.isNotEmpty()) {
                    if (s.contains("map:")) {
                        currentMap = s
                    } else if (currentMap.isNotEmpty()) {
                        val coords = s.trim().split(" ").map { it.toLong() }
                        if (coords[1] <= location && location <= coords[1] + coords[2])
                            {
                                location = coords[0] + location - coords[1]
                                //println("$currentMap $location")
                                currentMap = ""
                            }
                    }
                }
            }

            lowest.add(location)
            //"location for seed: $seed = $location".println()
            currentMap = ""
        }
        "done".println()
        return lowest.minBy { it }
    }

    fun part2(input: List<String>): Long {
        val seeds = input[0].split(":")[1].trim().split(" ").map { it.toLong() }
        println("seeds: $seeds")
        var lowest = 0L
        var currentMap = ""
        seeds.forEachIndexed seed@{ i, seed ->
            if (i % 2 != 0) return@seed
            "next seed $seed".println()
            (seeds[i]..seeds[i] + seeds[i + 1]).toList().forEach seed@{ newSeed ->
                println("running the thing")
                var location = newSeed
                input.forEach lines@{ s ->

                    if (s.isNotEmpty()) {
                        if (s.contains("map:")) {
                            currentMap = s
                            // currentMap.println()
                        } else if (currentMap.isNotEmpty()) {
                            val coords = s.trim().split(" ").map { it.toLong() }
                            // coords.println()
                            if (coords[1] <= location && location <= coords[1] + coords[2]) {
                                location = coords[0] + location - coords[1]
                                println("$currentMap $location")
                                currentMap = ""
                            }
                        }
                    }
                }

                if (lowest == 0L) {
                    lowest = location
                } else {
                    if (location < lowest) {
                        lowest = location
                    }
                }
                "location for seed: $seed = $location".println()
                currentMap = ""
            }
        }
        "done".println()
        return lowest
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day05")
            val part1Result = part1(input)
            part1Result.println()
            // check(part1Result == 20855)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day05_test")
            val part2Result = part2(input2)
            part2Result.println()
            // check(part2Result == 5489600)
        }
    println("part2 took: $elapsed2")
}
