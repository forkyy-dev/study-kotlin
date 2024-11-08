package chap02

interface Expr

class Num(val value: Int) : Expr

class Sum(val left: Expr, val right: Expr) : Expr

fun main() {
    val num1 = Num(1)
    val num2 = Num(2)
    val num3 = Num(4)

    val result = Sum(Sum(num1, num2), num3)

    println(eval(result))
}

//java style
fun eval(expr: Expr): Int {
    if (expr is Num) {
        val n = expr
        return n.value
    }

    if (expr is Sum) {
        return eval(expr.left) + eval(expr.right)
    }

    throw IllegalArgumentException("Unknown Exception!!")
}

//kotlin style 1
fun evalKt(e: Expr): Int = when (e) {
    is Sum -> eval(e.left) + eval(e.right)
    is Num -> e.value
    else -> throw IllegalArgumentException("Unknown Exception!!")
}

//kotlin style 2
fun evalKt2(e: Expr): Int = if (e is Num) e.value
else if (e is Sum) eval(e.left) + eval(e.right)
else throw IllegalArgumentException("Unknown Exception!!")
