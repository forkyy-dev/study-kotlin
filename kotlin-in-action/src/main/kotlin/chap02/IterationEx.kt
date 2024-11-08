package chap02

import java.util.TreeMap

fun main2() {
    for (i in 1..100) {
        println(fizzBuzz(i))
    }
}

fun fizzBuzz(num: Int) = when {
    num % 15 == 0 -> "FizzBuzz"
    num % 3 == 0 -> "Fizz"
    num % 5 == 0 -> "Buzz"
    else -> "$num"
}

fun main() {
    val binaryReps = TreeMap<Char, String>()

    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.code)
        binaryReps[c] = binary
    }

    for ((c, s) in binaryReps) {
        println("key: $c, value: $s")
    }
}