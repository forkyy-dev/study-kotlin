package chap04

internal open class TalkativeButton {
    private fun yell() = println("ahhhhhhhhhh!")
    protected fun whisper() = println("shhhh..")
}

internal fun TalkativeButton.hello() {

}