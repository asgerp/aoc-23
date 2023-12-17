import kotlin.system.measureTimeMillis

fun main() {
    fun hash(s: CharArray): Int {
        var currentValue = 0
        s.forEach {
            val code = it.code
            currentValue += code
            currentValue *= 17
            currentValue = currentValue.rem(256)
        }
        return currentValue
    }

    fun hashLabel(s: CharArray): Int {
        var currentValue = 0
        s.forEach {
            if (it.isDigit() || it == '=' || it == '-') {
                return@forEach
            } else {
                val code = it.code
                currentValue += code
                currentValue *= 17
                currentValue = currentValue.rem(256)
            }
        }
        return currentValue
    }

    fun part1(input: List<String>): Int {
        val sequences = input[0].split(",").map { it.toCharArray() }

        return sequences.fold(0) { acc, s ->
            val currentValue = hash(s)
            acc + currentValue
        }
    }

    fun calTotalFocusinPower(hashMutableMap: MutableMap<Int, MutableMap<String, Int>>): Int {
        var total = 0
        hashMutableMap.forEach { (boxNo, slots) ->
            var index = 1
            val boxPlusOne = (boxNo + 1)
            slots.forEach { (_, focalLength) ->
                val slotTotal = boxPlusOne * index * focalLength
                total += slotTotal
                index++
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var result = 0
        val sequences = input[0].split(",").map { it.toCharArray() }
        val hashMutableMap = mutableMapOf<Int, MutableMap<String, Int>>()
        sequences.forEach { s ->
            val currentValue = hashLabel(s)
            val seqString = String(s)
            if (seqString.contains("=")) {
                val splitted = seqString.split("=")
                if (hashMutableMap.containsKey(currentValue)) {
                    hashMutableMap[currentValue]?.set(splitted[0], splitted[1].toInt())
                } else {
                    hashMutableMap[currentValue] = mutableMapOf(splitted[0] to splitted[1].toInt())
                }
                // add to box
            } else {
                val splitted = seqString.split("-")
                if (hashMutableMap.containsKey(currentValue)) {
                    hashMutableMap[currentValue]?.remove(splitted[0])
                }
                // remove from box
            }
            result = calTotalFocusinPower(hashMutableMap)
            // println(hashMutableMap)
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day15")
            val part1Result = part1(input)
            println("result = $part1Result")
            check(part1Result == 517315)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day15")
            val part2Result = part2(input2)
            println("result = $part2Result")
            check(part2Result == 247763)
        }
    println("part2 took: $elapsed2")
}
