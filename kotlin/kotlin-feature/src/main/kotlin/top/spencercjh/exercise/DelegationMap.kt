package top.spencercjh.exercise

/**
 * @author SpencerCJH
 * @date 2019/11/6 0:14
 */
class Person {
    private val _attributes = hashMapOf<String, String>()

    fun setAttribute(key: String, value: String) {
        _attributes[key] = value
    }

    fun getAttribute(key: String) = _attributes[key]

    var name: String by _attributes
}

fun main() {
    val spencer = Person()
    spencer.setAttribute("name", "spencer")
//    spencer.setAttribute("name1", "spencer") assert exception
    println(spencer.name)
    spencer.name = "updated"
    println(spencer.getAttribute("name"))
}