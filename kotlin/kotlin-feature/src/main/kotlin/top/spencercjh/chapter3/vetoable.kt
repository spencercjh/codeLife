package top.spencercjh.chapter3

import kotlin.properties.Delegates

/**
 * @author SpencerCJH
 * @date 2019/10/26 13:15
 */
var testVetoable: String by Delegates.vetoable(initialValue = "initialValue",
    onChange = { property, oldValue, newValue ->
        println("property:$property")
        newValue.length > oldValue.length
    })

fun main() {
    println(testVetoable)
    testVetoable = "first"
    println(testVetoable)
    testVetoable = "LongLongLongLong"
    println(testVetoable)
}