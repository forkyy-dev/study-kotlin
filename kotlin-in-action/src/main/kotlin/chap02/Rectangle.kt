package chap02

class Rectangle (
    val height: Int,
    val width: Int
) {
    val isSquare: Boolean
        get() {
            return height == width
        }
}