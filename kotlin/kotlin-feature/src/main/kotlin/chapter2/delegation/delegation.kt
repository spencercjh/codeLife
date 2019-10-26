package chapter2.delegation

/**
 * @author SpencerCJH
 * @date 2019/10/18 20:32
 */
interface Base {
    val message: String
    fun print()
}

class BaseImpl(private val x: Int) : Base {
    override val message: String = "base implement message"

    override fun print() {
        print(x)
    }
}

class Derived(base: Base) : Base by base {
    override val message: String = "derived override message"

    override fun print() {
        println("derived print override base function")
    }
}

fun main(args: Array<String>) {
    val b = BaseImpl(10)
    val derived = Derived(b)
    derived.print()
    println(derived.message)
}