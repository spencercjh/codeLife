package top.spencercjh.exercise

import kotlin.reflect.KClass

fun <T> ensureTrailingPeriod(sequence: T) where T : CharSequence, T : Appendable {
    if (!sequence.endsWith('.')) {
        sequence.append('.')
    }
}

@Suppress("unused")
class Processor1<T> {
    fun process(value: T) {
        value?.hashCode() ?: -1
    }
}

@Suppress("unused")
class Processor2<T : Any> {
    fun process(value: T) {
        value?.hashCode() ?: -1
    }
}

interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String): Boolean = input.isNotEmpty()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int): Boolean = input >= 0
}

object Validators {
    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()

    inline fun <reified T : Any> registerValidator(filedValidator: FieldValidator<T>) {
        validators[T::class] = filedValidator
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(kClass: KClass<T>): FieldValidator<T> =
        validators[kClass] as? FieldValidator<T>
            ?: throw IllegalArgumentException("No validator for ${kClass.simpleName}")
}

fun main() {
    Validators.registerValidator(DefaultStringValidator)
    Validators.registerValidator(DefaultIntValidator)
    println(Validators[String::class].validate("Kotlin"))
    println(Validators[String::class].validate(""))
    println(Validators[Int::class].validate(123))
    println(Validators[Int::class].validate(-1))
}