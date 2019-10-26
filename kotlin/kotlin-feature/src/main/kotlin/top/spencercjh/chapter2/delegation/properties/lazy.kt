package top.spencercjh.chapter2.delegation.properties

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