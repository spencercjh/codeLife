package top.spencercjh.exercise.function

val lambdaWithMultipleParameters = { x: Int, y: Int, z: Int -> x + y + z }
val same = object : Function3<Int, Int, Int, Int> {
    override fun invoke(p1: Int, p2: Int, p3: Int): Int {
        return p1 + p2 + p3
    }
}

fun main() {
    println(lambdaWithMultipleParameters(1, 2, 3))
    println(same(1, 2, 3))
    val x = 123
    println(functionAsParameters(x) { num: Int -> num > 0 })
    println(lambdaAsParameters(x) { num: Int -> num > 0 })
}

fun <A> functionAsParameters(value: A, predicate: Function1<A, Boolean>) = predicate(value)
fun <A> lambdaAsParameters(value: A, predicate: (A) -> Boolean) = predicate(value)
