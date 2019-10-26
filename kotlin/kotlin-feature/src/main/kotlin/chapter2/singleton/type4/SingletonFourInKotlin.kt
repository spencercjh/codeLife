package chapter2.singleton.type4

/**
 * @author SpencerCJH
 * @date 2019/10/17 19:56
 */
class SingletonFourInKotlin private constructor() {
    companion object {
        val INSTANCE: SingletonFourInKotlin by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED,
                initializer = { SingletonFourInKotlin() })
    }
}