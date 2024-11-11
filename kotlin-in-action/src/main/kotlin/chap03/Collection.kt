package chap03

fun main() {
    val set = hashSetOf(1, 7, 53)
    val list = arrayListOf(1, 7, 53)
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

    val result = "12.345-6.A".split("[.\\-]".toRegex())

}

fun <T> joinToString(
    collection: Collection<T>,
    separator: String = ",",
    prefix: String = "",
    postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for((i, e) in collection.withIndex()) {
        if(i > 0) result.append(separator)
        result.append(e)
    }
    result.append(postfix)
    return result.toString()
}