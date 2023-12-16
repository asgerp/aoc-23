import kotlin.system.measureTimeMillis

fun main() {
    fun handleSlash(beam: Beam): Beam {
        // handle /
        val nextDirection =
            when (beam.direction) {
                BeamDirection.RIGHT -> BeamDirection.UP
                BeamDirection.LEFT -> BeamDirection.DOWN
                BeamDirection.UP -> BeamDirection.RIGHT
                BeamDirection.DOWN -> BeamDirection.LEFT
            }
        beam.direction = nextDirection
        return beam
    }

    fun handleBackSlash(beam: Beam): Beam {
        // handle \
        val nextDirection =
            when (beam.direction) {
                BeamDirection.RIGHT -> BeamDirection.DOWN
                BeamDirection.LEFT -> BeamDirection.UP
                BeamDirection.UP -> BeamDirection.LEFT
                BeamDirection.DOWN -> BeamDirection.RIGHT
            }
        beam.direction = nextDirection
        return beam
    }

    fun handleDash(
        beam: Beam,
        iterator: MutableListIterator<Beam>,
    ): Beam {
        // handle -
        val nextDirection =
            when (beam.direction) {
                BeamDirection.RIGHT -> BeamDirection.RIGHT
                BeamDirection.LEFT -> BeamDirection.LEFT
                BeamDirection.UP -> {
                    val splitBeam = Beam(BeamDirection.LEFT, beam.position)
                    iterator.add(splitBeam)
                    BeamDirection.RIGHT
                }

                BeamDirection.DOWN -> {
                    val splitBeam = Beam(BeamDirection.RIGHT, beam.position)
                    iterator.add(splitBeam)
                    BeamDirection.LEFT
                }
            }
        beam.direction = nextDirection
        return beam
    }

    fun handlePipe(
        beam: Beam,
        iterator: MutableListIterator<Beam>,
    ): Beam {
        val nextDirection =
            when (beam.direction) {
                BeamDirection.RIGHT -> {
                    val splitBeam = Beam(BeamDirection.UP, beam.position)
                    iterator.add(splitBeam)
                    BeamDirection.DOWN
                }

                BeamDirection.LEFT -> {
                    val splitBeam = Beam(BeamDirection.DOWN, beam.position)
                    iterator.add(splitBeam)
                    BeamDirection.UP
                }

                BeamDirection.UP -> BeamDirection.UP
                BeamDirection.DOWN -> BeamDirection.DOWN
            }
        beam.direction = nextDirection
        return beam
    }

    fun moveBeam(
        beam: Beam,
        contraption: MutableList<MutableList<Char>>,
        iterator: MutableListIterator<Beam>,
        visited: MutableSet<String>,
        coordsVisited: MutableSet<Pair<Int, Int>>,
    ) {
        if (visited.contains(beam.toString())) {
            iterator.remove()
            return
        } else {
            visited.add(beam.toString())
            coordsVisited.add(beam.position)
        }
        // do the move
        val nextPosition =
            when (beam.direction) {
                BeamDirection.RIGHT -> beam.position.first to beam.position.second + 1
                BeamDirection.LEFT -> beam.position.first to beam.position.second - 1
                BeamDirection.UP -> beam.position.first - 1 to beam.position.second
                BeamDirection.DOWN -> beam.position.first + 1 to beam.position.second
            }
        if (
            nextPosition.first < 0 ||
            nextPosition.first >= contraption.size ||
            nextPosition.second < 0 ||
            nextPosition.second >= contraption[0].size
        ) {
            // remove the beam as it goes out of bounds
            iterator.remove()
        } else {
            // decide direction from next char
            beam.position = nextPosition
            val c = contraption[nextPosition.first][nextPosition.second]
            when (c) {
                '/' -> handleSlash(beam)
                '\\' -> handleBackSlash(beam)
                '-' -> handleDash(beam, iterator)
                '|' -> handlePipe(beam, iterator)
                else -> {
                    beam
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val contraption = mutableListOf<MutableList<Char>>()
        input.forEachIndexed { index, s ->
            val h = mutableListOf<Char>()
            s.toCharArray().forEachIndexed { i, c ->
                h.add(i, c)
            }
            contraption.add(index, h)
        }
        val beams = mutableListOf(Beam(BeamDirection.DOWN, 0 to 0))
        val visited = mutableSetOf<String>()
        val coordsVisited = mutableSetOf<Pair<Int, Int>>()
        while (true) {
            while (beams.isNotEmpty()) {
                val beamIterator = beams.listIterator()
                while (beamIterator.hasNext()) {
                    val beam = beamIterator.next()
                    moveBeam(beam, contraption, beamIterator, visited, coordsVisited)
                }
            }
            if (beams.isEmpty()) {
                break
            }
        }
        return coordsVisited.size
    }

    fun fullScan(
        startDirection: BeamDirection,
        firstPosition: Int,
        secondPosition: Int,
        contraption: MutableList<MutableList<Char>>,
    ): Int {
        val beams = mutableListOf(Beam(startDirection, firstPosition to secondPosition))
        val visited = mutableSetOf<String>()
        val coordsVisited = mutableSetOf<Pair<Int, Int>>()
        while (true) {
            while (beams.isNotEmpty()) {
                val beamIterator = beams.listIterator()
                while (beamIterator.hasNext()) {
                    val beam = beamIterator.next()
                    moveBeam(beam, contraption, beamIterator, visited, coordsVisited)
                }
            }
            if (beams.isEmpty()) {
                break
            }
        }
        return coordsVisited.size
    }

    fun part2(input: List<String>): Int {
        val contraption = mutableListOf<MutableList<Char>>()
        input.forEachIndexed { index, s ->
            val h = mutableListOf<Char>()
            s.toCharArray().forEachIndexed { i, c ->
                h.add(i, c)
            }
            contraption.add(index, h)
        }
        // DOWN
        var max = 0
        (0..contraption.size).toList().forEachIndexed { index, _ ->
            val localMax = fullScan(BeamDirection.DOWN, 0, index, contraption)
            if (localMax > max) {
                max = localMax
            }
        }
        (0..contraption.size).toList().forEachIndexed { index, _ ->
            val localMax = fullScan(BeamDirection.UP, contraption.size, index, contraption)
            if (localMax > max) {
                max = localMax
            }
        }
        (0..contraption.size).toList().forEachIndexed { index, _ ->
            val localMax = fullScan(BeamDirection.RIGHT, index, 0, contraption)
            if (localMax > max) {
                max = localMax
            }
        }
        (0..contraption.size).toList().forEachIndexed { index, _ ->
            val localMax = fullScan(BeamDirection.LEFT, index, contraption.size, contraption)
            if (localMax > max) {
                max = localMax
            }
        }
        return max
    }

// test if implementation meets criteria from the description, like:
// val testInput = readInput("Day01_test")
// check(part1(testInput) == 1)
    val elapsed1 =
        measureTimeMillis {
            val input = readInput("Day16")
            val part1Result = part1(input)
            println("result = $part1Result")
            check(part1Result == 7111)
        }
    println("part1 took: $elapsed1")
    val elapsed2 =
        measureTimeMillis {
            val input2 = readInput("Day16")
            val part2Result = part2(input2)
            println("result = $part2Result")
            check(part2Result == 7831)
        }
    println("part2 took: $elapsed2")
}

class Beam(
    var direction: BeamDirection,
    var position: Pair<Int, Int>,
) {
    override fun toString(): String {
        return "Beam(direction=$direction, position=$position)"
    }
}

enum class BeamDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}
