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
}