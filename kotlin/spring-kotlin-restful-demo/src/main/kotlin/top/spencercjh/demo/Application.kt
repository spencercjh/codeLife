package top.spencercjh.demo

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import top.spencercjh.demo.dao.ClazzRepository
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Clazz
import top.spencercjh.demo.entity.Student
import top.spencercjh.demo.util.RandomUtil
import javax.annotation.PostConstruct

/**
 * use [PostConstruct] to init data in db
 * @author spencer
 */
@SpringBootApplication
class SpringKotlinRestfulDemoApplication {

    private val logger = LoggerFactory.getLogger(javaClass)
    @Autowired
    private lateinit var clazzRepository: ClazzRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository

    @PostConstruct
    fun initData() {
        // init two class and 90 students in total
        logger.debug("init data")
        val clazzOne = Clazz(name = CLASS_NAME_ONE)
        clazzOne.students = RandomUtil.getRandomStudents(MOCK_STUDENT_AMOUNT, clazzOne)
        clazzRepository.save(clazzOne)
        studentRepository.saveAll(clazzOne.students)
        val clazzTwo = Clazz(name = CLASS_NAME_TWO)
        clazzTwo.students = RandomUtil.getRandomStudents(MOCK_STUDENT_AMOUNT * 2, clazzTwo)
                // plus a designated student for searching api test
                .plus(Student(name = "蔡佳昊",
                        clazz = clazzTwo,
                        sex = Student.Sex.Male,
                        phone = RandomUtil.getRandomPhone(),
                        email = RandomUtil.getRandomEmail()))
        clazzRepository.save(clazzTwo)
        studentRepository.saveAll(clazzTwo.students)
    }

    companion object Constant {
        @Value("\${default.MOCK_STUDENT_AMOUNT}")
        const val MOCK_STUDENT_AMOUNT: Int = 10
        @Value("\${default.DEFAULT_PAGE_SIZE}")
        const val DEFAULT_PAGE_SIZE: Int = 15
        @Value("\${default.DEFAULT_PAGE}")
        const val DEFAULT_PAGE: Int = 0

        // FIXME these const String val cannot inject through @Value
        const val CLASS_NAME_ONE = "class1"
        const val CLASS_NAME_TWO = "class2"
        const val DEFAULT_SORT_COLUMN = "id"
    }
}

fun main(args: Array<String>) {
    runApplication<SpringKotlinRestfulDemoApplication>(*args)
}
