import kotlin.system.measureTimeMillis

fun main() {
    fun isSymbolInRange(
        symbolsAbove: List<Pair<String?, Int?>>,
        range: IntRange?,
    ) = symbolsAbove.firstOrNull { above ->
        range?.contains(above.second) == true ||
            range?.contains(above.second?.minus(1) ?: -1) == true ||
            range?.contains(above.second?.plus(1) ?: -1) == true
    } != null

    fun isGears(
        numbers: List<Pair<Int?, IntRange?>>,
        startPosition: Int?,
    ) = numbers.filter { number ->
        number.second?.contains(startPosition) == true ||
            number.second?.contains(startPosition?.minus(1) ?: -1) == true ||
            number.second?.contains(startPosition?.plus(1) ?: -1) == true
    }.map { it.first ?: 0 }

    fun part1(input: List<String>): Int {
        var result = 0
        val regNumber = "(?<num>\\d+)".toRegex()
        val regSymbols = "(?<sym>[^\\d\\.])".toRegex()
        var symbolsAbove = emptyList<Pair<String?, Int?>>()
        var symbolsCurrent = regSymbols.findAll(input[0]).map { it.groups["sym"]?.value to it.groups["sym"]?.range?.first }.toList()
        var symbolsBelow = regSymbols.findAll(input[1]).map { it.groups["sym"]?.value to it.groups["sym"]?.range?.first }.toList()
        input.forEachIndexed { lineIndex, line ->
            val numbers =
                regNumber.findAll(line).map { it.groups["num"]?.value?.toInt() to it.groups["num"]?.range }.toList()

            numbers.forEach {
                val number = it.first ?: 0
                val range = it.second
                val found1 = isSymbolInRange(symbolsAbove, range)
                val found2 = isSymbolInRange(symbolsCurrent, range)
                val found3 = isSymbolInRange(symbolsBelow, range)
                if (found1 || found2 || found3) {
                    result += number
                }
            }
            symbolsAbove = symbolsCurrent
            symbolsCurrent = symbolsBelow
            symbolsBelow =
                if (lineIndex + 2 < input.size) {
                    regSymbols.findAll(input[lineIndex + 2])
                        .map { it.groups["sym"]?.value to it.groups["sym"]?.range?.first }.toList()
                } else {
                    emptyList()
                }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        val numberRegex = "(?<num>\\d+)".toRegex()
        val starRegex = "(?<sym>[\\*])".toRegex()
        var numbersAbove = emptyList<Pair<Int?, IntRange?>>()
        var numbersCurrent =
            numberRegex.findAll(input[0]).map { it.groups["num"]?.value?.toInt() to it.groups["num"]?.range }.toList()
        var numbersBelow =
            numberRegex.findAll(input[1]).map { it.groups["num"]?.value?.toInt() to it.groups["num"]?.range }.toList()
        input.forEachIndexed { lineIndex, line ->
            val stars = starRegex.findAll(line).map { it.groups["sym"]?.value to it.groups["sym"]?.range?.first }.toList()

            stars.forEach { it ->
                val position = it.second
                val gearNumbersAbove = isGears(numbersAbove, position)
                val gearNumbersCurrent = isGears(numbersCurrent, position)
                val gearNumbersBelow = isGears(numbersBelow, position)

                val above = gearNumbersAbove.reduceOrNull(Int::times) ?: 1
                val current = gearNumbersCurrent.reduceOrNull(Int::times) ?: 1
                val below = gearNumbersBelow.reduceOrNull(Int::times) ?: 1
                val checkThis = (above * current * below)
                val foundOnes = (gearNumbersAbove.size + gearNumbersCurrent.size + gearNumbersBelow.size)
                if (checkThis != 1 && foundOnes > 1) {
                    result += checkThis
                }
            }
            numbersAbove = numbersCurrent
            numbersCurrent = numbersBelow
            numbersBelow =
                if (lineIndex + 2 < input.size) {
                    numberRegex.findAll(input[lineIndex + 2])
                        .map { it.groups["num"]?.value?.toInt() to it.groups["num"]?.range }.toList()
                } else {
                    emptyList()
                }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day03")
            val part1Result = part1(input)
            part1Result.println()
            check(part1Result == 533784)
        }
    println(elapsed1)
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day03_02")
            val part2Result = part2(input2)
            part2Result.println()
            check(part2Result == 78826761)
        }
    println(elapsed2)
}
