package top.spencercjh.chapter2.singleton.type2

/**
 * @author SpencerCJH
 * @date 2019/10/17 19:46
 */
class SingletonTwoInKotlin private constructor() {
    companion object {
        private var INSTANCE: SingletonTwoInKotlin? = null
            get() {
                if (field == null) {
                    field = SingletonTwoInKotlin()
                }
                return field
            }
    }

    fun getInstance(): SingletonTwoInKotlin {
        return INSTANCE!!
    }
}