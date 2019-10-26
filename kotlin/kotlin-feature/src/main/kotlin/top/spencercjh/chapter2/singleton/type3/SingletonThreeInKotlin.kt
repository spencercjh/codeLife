package chapter2.singleton.type3

/**
 * @author SpencerCJH
 * @date 2019/10/17 19:51
 */
class SingletonThreeInKotlin private constructor() {
    companion object {
        private var INSTANCE: SingletonThreeInKotlin? = null
            get() {
                if (field == null) {
                    field = SingletonThreeInKotlin()
                }
                return field
            }

        @Synchronized
        fun getInstance(): SingletonThreeInKotlin {
            return INSTANCE!!
        }
    }
}