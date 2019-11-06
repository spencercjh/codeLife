package top.spencercjh.exercise

/**
 * @author SpencerCJH
 * @date 2019/11/7 0:30
 */
val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

fun returnInLambda() {
    list.forEach {
        if (it == 5) {
            return
        }
    }
    // this line can't be reached
    println("lambda out")
}

fun returnByLabel() {
    list.forEach label@{
        if (it == 5) {
            return@label
        }
    }
    println("lambda out 1")
}

fun returnByAnonymousFunction() {
    list.forEach(fun(number) {
        if (number == 5) {
            return
        }
        println("not 5:${number}")
    })
    println("lambda out 2")
}

fun main() {
    returnInLambda()
    returnByLabel()
    returnByAnonymousFunction()
}