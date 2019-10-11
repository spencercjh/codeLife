package top.spencercjh.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

/**
 * use [PostConstruct] to init data in db
 * @author spencer
 */
@SpringBootApplication
class SpringKotlinRestfulDemoApplication {

    companion object Constant {
        // FIXME these const String val cannot inject through @Value
        const val MOCK_STUDENT_AMOUNT: Int = 30
        const val DEFAULT_PAGE_SIZE: Int = 15
        const val DEFAULT_PAGE: Int = 0
        const val CLASS_NAME_ONE = "class1"
        const val CLASS_NAME_TWO = "class2"
        const val DEFAULT_SORT_COLUMN = "id"
    }
}

fun main(args: Array<String>) {
    runApplication<SpringKotlinRestfulDemoApplication>(*args)
}
