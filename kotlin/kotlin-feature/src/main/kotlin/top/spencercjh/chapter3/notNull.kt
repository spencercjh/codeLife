package top.spencercjh.chapter3

import kotlin.properties.Delegates

/**
 * @author SpencerCJH
 * @date 2019/10/26 14:22
 */

var testNotNull: String by Delegates.notNull()

fun main() {
//   assert exception:Property testNotNull should be initialized before get.
//   println(testNotNull)
    testNotNull = "123"
    println(testNotNull)
}
