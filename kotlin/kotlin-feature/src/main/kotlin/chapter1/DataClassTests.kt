package chapter1

data class User(val name: String = "testName", val age: Int = 22) {
    // data class类体中的属性将不会有component()函数，不会在toString()、 equals()、 hashCode() 以及 copy()中出现
    var innerValue: String = "123"

    val readOnlyInnerValue: String = "readOnly"


}

val user: User = User()

fun main(args: Array<String>) {
    println(user)
    user.innerValue = "456"
    println(user.innerValue)
    println(user.readOnlyInnerValue)
    println(user.hashCode())
}


