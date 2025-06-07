package chap04

data class DataTestClass(val data: String) {
    val data2: String = "hello"
}

fun main() {
    val data1 = DataTestClass("test1")
    val data2 = DataTestClass("test1")

    println(data1 == data2)
    println(data1)
    println(data2)
}