package chap03.strings

fun <T> Collection<T>.joinToString(
    separator: String = ",",
    prefix: String = "",
    postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((i, e) in this.withIndex()) {
        if (i > 0) result.append(separator)
        result.append(e)
    }
    result.append(postfix)
    return result.toString()
}