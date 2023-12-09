import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) =
    File("src", "$name.txt")
        .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

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
