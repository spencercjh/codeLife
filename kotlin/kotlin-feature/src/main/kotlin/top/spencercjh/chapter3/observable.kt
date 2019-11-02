package top.spencercjh.chapter3

import kotlin.properties.Delegates

/**
 * @author SpencerCJH
 * @date 2019/10/26 13:05
 */
var testObservable: String by Delegates.observable(
    initialValue = "initialValue",
    onChange = { property, oldValue, newValue ->
        println("property:$property \t oldValue:$oldValue \t newValue:$newValue")
    })

fun main() {
    println(testObservable)
    testObservable = "first update"
    println(testObservable)
}