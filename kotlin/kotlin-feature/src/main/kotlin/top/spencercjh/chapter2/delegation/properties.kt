package top.spencercjh.chapter2.delegation

val testLazy: String by lazy {
    println("first");"hello"
}

fun main(args: Array<String>) {
    println(testLazy)
    println(testLazy)
}