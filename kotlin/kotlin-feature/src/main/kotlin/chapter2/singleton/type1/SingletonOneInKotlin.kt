package chapter2.singleton.type1

/**
 * @author SpencerCJH
 * @date 2019/10/17 19:40
 */
object SingletonOneInKotlin {
    fun function(): String {
        return "abc"
    }
}

class LikeObject {
    companion object {
        fun functionInObject(): String {
            return "abc"
        }
    }
    fun function():String{
        return "def"
    }
}

fun main() {
    println(SingletonOneInKotlin.function())
    println(LikeObject.functionInObject())
    // but i can not call SingletonOneInKotlin.function
}