package chapter2.singleton.type4

/**
 * @author SpencerCJH
 * @date 2019/10/17 19:56
 */
class SingletonFourInKotlinWithInit private constructor(private val property: Any) {
    companion object {
        @Volatile
        private var INSTANCE: SingletonFourInKotlinWithInit? = null

        fun getInstance(property: Any): SingletonFourInKotlinWithInit {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SingletonFourInKotlinWithInit(property).also { INSTANCE = it }
            }
        }
    }
}