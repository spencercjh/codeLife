package top.spencercjh.demo.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication
import top.spencercjh.demo.dao.ClazzRepository
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Clazz
import top.spencercjh.demo.entity.Sex
import top.spencercjh.demo.entity.Student
import top.spencercjh.demo.util.RandomUtil
import javax.annotation.PostConstruct

@Component
class InitData {
    private val logger = LoggerFactory.getLogger(javaClass)
    @Autowired
    private lateinit var clazzRepository: ClazzRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository

    @PostConstruct
    fun initData() {
        // init two class and 90 students in total
        logger.debug("init data")
        val clazzOne = Clazz(name = SpringKotlinRestfulDemoApplication.CLASS_NAME_ONE)
        clazzOne.students = RandomUtil.getRandomStudents(SpringKotlinRestfulDemoApplication.MOCK_STUDENT_AMOUNT, clazzOne)
        clazzRepository.save(clazzOne)
        studentRepository.saveAll(clazzOne.students)
        val clazzTwo = Clazz(name = SpringKotlinRestfulDemoApplication.CLASS_NAME_TWO)
        clazzTwo.students = RandomUtil.getRandomStudents(SpringKotlinRestfulDemoApplication.MOCK_STUDENT_AMOUNT * 2, clazzTwo)
                // plus a designated student for searching api test
                .plus(Student(name = "蔡佳昊",
                        clazz = clazzTwo,
                        sex = Sex.Male,
                        phone = "1234567890",
                        email = RandomUtil.getRandomEmail()))
        clazzRepository.save(clazzTwo)
        studentRepository.saveAll(clazzTwo.students)
    }
}