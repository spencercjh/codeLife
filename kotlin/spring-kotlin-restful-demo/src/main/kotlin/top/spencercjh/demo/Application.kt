package top.spencercjh.demo

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import top.spencercjh.demo.dao.ClazzRepository
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Clazz
import top.spencercjh.demo.entity.Student
import top.spencercjh.demo.util.RandomUtil
import javax.annotation.PostConstruct

@SpringBootApplication
class SpringKotlinRestfulDemoApplication {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var clazzRepository: ClazzRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository

    companion object Constant {
        const val MOCK_STUDENT_AMOUNT: Int = 30
        const val CLASS_NAME_ONE = "class1"
        const val CLASS_NAME_TWO = "class2"
        const val DEFAULT_SORT_COLUMN="id"
        const val DEFAULT_PAGE_SIZE: Int = 15
        const val DEFAULT_PAGE: Int = 0
    }

    @PostConstruct
    fun initData() {
        logger.debug("init data")
        val clazzOne = Clazz(name = CLASS_NAME_ONE)
        clazzOne.students = RandomUtil.getStudents(MOCK_STUDENT_AMOUNT, clazzOne)
        clazzRepository.save(clazzOne)
        studentRepository.saveAll(clazzOne.students)
        val clazzTwo = Clazz(name = CLASS_NAME_TWO)
        // plus a designated student for searching api test
        clazzTwo.students = RandomUtil.getStudents(MOCK_STUDENT_AMOUNT * 2, clazzTwo)
                .plus(Student(name = "蔡佳昊", clazz = clazzTwo, sex = Student.Sex.Male))
        clazzRepository.save(clazzTwo)
        studentRepository.saveAll(clazzTwo.students)
    }
}

fun main(args: Array<String>) {
    runApplication<SpringKotlinRestfulDemoApplication>(*args)
}
