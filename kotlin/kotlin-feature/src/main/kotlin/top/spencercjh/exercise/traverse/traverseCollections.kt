package top.spencercjh.exercise.traverse

// function needs to be manually imported
import java.util.function.Function
import java.util.stream.Collectors

data class Node(val name: String, val age: Int)

fun main() {
    val peoples = listOf(Node("Alice", 11),
            Node("Peter", 15),
            Node("Jack", 8))
    // Lambda expression equals kotlin.Function
    println(peoples.filter { person -> person.age > 10 })
    // use kotlin.function
    val function = object : Function1<Node, Boolean> {
        override fun invoke(person: Node): Boolean {
            return person.age > 10
        }
    }
    println(peoples.filter(function))
    // use java.util.Function instead
    fun <T> Iterable<T>.myFilter(predicate: Function<T, Boolean>): List<T> {
        val newArray = ArrayList<T>()
        for (t in this) {
            if (predicate.apply(t)) {
                newArray.add(t)
            }
        }
        return newArray;
    }
    println(peoples.myFilter(Function { node: Node -> node.age > 10 }))
    // Anonymous inner class
    println(peoples.myFilter(object : Function<Node, Boolean> {
        override fun apply(t: Node): Boolean {
            return t.age > 10;
        }
    }))
    println(peoples.filter { it.age > 10 })
    println(peoples.maxBy { it.age })
    println(peoples.stream().filter { it.age > 10 }.collect(Collectors.toList()).toString())
}