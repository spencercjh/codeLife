package top.spencercjh.exercise

/**
 * @author SpencerCJH
 * @date 2019/11/25 21:43
 */

/* 在kt 1.0，你还能写出 var canReturnNull: (Int, Int) -> Int? = { null } 这样的代码
* 此处表示一个接收Int,Int返回Int?的lambda变量*/
var canReturnNull: (Int, Int) -> Int? = { _, _ -> null }

/**/
var funOrNull: ((Int, Int) -> Int)? = null

fun String.myFilter(predicate: (Char) -> Boolean): String = with(StringBuilder()) {
    for (ch in this@myFilter) {
        if (predicate(ch)) {
            append(ch)
        }
    }
    toString()
}

fun <T : Any> Collection<T>.myJoinToString(
    separator: String = ", ",
    prefix: String = "[",
    postfix: String = "]",
    transform: (T) -> String = { it.toString() }
): String = with(StringBuilder()) {
    append(prefix)
    this@myJoinToString.forEachIndexed { index, item ->
        if (index > 0) {
            append(separator)
        }
        append(transform(item))
    }

    append(postfix)
    toString()
}

fun main() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(list.myJoinToString { number -> (number - 1).toString() })
}