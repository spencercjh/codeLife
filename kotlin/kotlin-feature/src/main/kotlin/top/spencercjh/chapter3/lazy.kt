package top.spencercjh.chapter3

/**
 * @author SpencerCJH
 * @date 2019/10/26
 */
val testLazy: String by lazy {
    println("first");"hello"
}

fun main(args: Array<String>) {
    println(testLazy)
    println(testLazy)
}