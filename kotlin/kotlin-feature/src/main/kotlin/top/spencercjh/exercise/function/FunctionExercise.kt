package top.spencercjh.exercise.function

val lambdaWithMultipleParameters = { x: Int, y: Int, z: Int -> x + y + z }
val same = object : Function3<Int, Int, Int, Int> {
    override fun invoke(p1: Int, p2: Int, p3: Int): Int {
        return p1 + p2 + p3
    }
}

/*fun main() {
    println(lambdaWithMultipleParameters(1, 2, 3))
    println(same(1, 2, 3))
    val x = 123
    println(functionAsParameters(x) { num: Int -> num > 0 })
    println(lambdaAsParameters(x) { num: Int -> num > 0 })
}*/

fun <A> functionAsParameters(value: A, predicate: Function1<A, Boolean>) = predicate(value)
fun <A> lambdaAsParameters(value: A, predicate: (A) -> Boolean) = predicate(value)


data class Person(val name: String, val age: Int)

val people = listOf(Person("Alice", 29), Person("Bob", 31))


fun lookForAliceWithFor(people: List<Person>): Unit {
    for (person in people) {
        if (person.name == "Alice") {
            println("lookForAliceWithFor: Found Alice!")
            return
        }
    }
    println("lookForAliceWithFor: Alice is not Found")
}

fun lookForAliceWithForEachAndReturn(people: List<Person>): Unit {
    people.forEach {
        if (it.name == "Alice") {
            println("lookForAliceWithForEachAndReturn: Found Alice!")
            return
        }
    }
    println("lookForAliceWithForEachAndReturn: Alice is not Found")
}

fun lookForAliceWithForEachAndReturnTag(people: List<Person>): Unit {
    people.forEach {
        if (it.name == "Alice") {
            println("lookForAliceWithForEachAndReturnTag: Found Alice!")
            return@forEach
        }
    }
    println("lookForAliceWithForEachAndReturnTag: Alice is not Found")
}

fun lookForAliceWithForEachAndAnonymousFunction(people: List<Person>): Unit {
    people.forEach(fun(person: Person) {
        if (person.name == "Alice") {
            println("lookForAliceWithForEachAndAnonymousFunction: Found Alice!")
            return
        }
    })
    println("lookForAliceWithForEachAndAnonymousFunction: Alice is not Found")
}


fun main() {
    lookForAliceWithFor(people)
    lookForAliceWithForEachAndReturn(people)
    lookForAliceWithForEachAndReturnTag(people)
    lookForAliceWithForEachAndAnonymousFunction(people)
}