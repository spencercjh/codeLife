package top.spencercjh.exercise

/**
 * @author SpencerCJH
 * @date 2019/11/6 20:26
 */
val alphabet = { sb: StringBuilder ->
    for (letter in 'A'..'Z') {
        sb.append(letter)
    }
}

fun alphabetWith() = with(
    receiver = StringBuilder(),
    block = {
        alphabet(this)
        toString()
    }
)


fun alphabetApply() = StringBuilder().apply { alphabet(this) }.toString()


class WithAndApply

fun WithAndApply.extendsAlphabet() = with(receiver = StringBuilder()) {
    //  you can get reference to the extended class: alphabet(this@extendsAlphabet)
    alphabet(this)
    toString()
}

fun main() {
    println(alphabetWith())
    println(alphabetApply())
    println(WithAndApply().extendsAlphabet())
}
