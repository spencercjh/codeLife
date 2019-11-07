package top.spencercjh.exercise

import top.spencercjh.exercise.traverse.Node
import java.util.*
import kotlin.random.Random

/**
 * @author SpencerCJH
 * @date 2019/11/7 19:29
 */

object Service {
    val data = listOf(
        null,
        Node("Alice", 20),
        Node("Peter", 15),
        Node("Jack", 25),
        Node("Spencer", 22)
    )
    val random = Random(10)

    fun dbFunction(): List<Node?>? {
        return if (random.nextInt() % 2 == 0) {
            data
        } else {
            null
        }
    }

}

class TestAs(val name: String) {

    /*
     IDEA generated default code:
        override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TestAs

        if (name != other.name) return false

        return true
    }*/
    override fun equals(other: Any?): Boolean {
        val otherTestAs = other as? TestAs ?: return false
        return otherTestAs.name == name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

fun main() {
    val nullList: List<Node?>? = Service.dbFunction()
    println(nullList?.get(0) ?: "Null Object")
    val notNullList: List<Node?> = Service.dbFunction() ?: Collections.emptyList()
    println(notNullList)
    val a = TestAs("123")
    val b = TestAs("123")
    val c = TestAs("456")
    val d = Node("132", 1)
    val e = null
    assert(a == b)
    assert(b != c)
    assert(!c.equals(d))
    assert(a != e)
    // maybe throw NPE
    val assertNotNullList: List<Node?> = Service.dbFunction()!!
    println(assertNotNullList)
    // maybe throw RuntimeException
    val throwExceptionList: List<Node?> = Service.dbFunction() ?: throw RuntimeException("empty list")
    println(throwExceptionList)
}
