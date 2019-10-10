package top.spencercjh.demo

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import top.spencercjh.demo.dao.ClazzRepository
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Clazz
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
        const val studentCount: Int = 30
        const val className = "class1"
        const val defaultPageSize: Int = 15
    }

    @PostConstruct
    fun initData() {
        logger.debug("init data")
        val clazzOne = Clazz(name = className)
        clazzOne.students = RandomUtil.getStudents(studentCount, clazzOne)
        clazzRepository.save(clazzOne)
        studentRepository.saveAll(clazzOne.students)
    }
}

fun main(args: Array<String>) {
    runApplication<SpringKotlinRestfulDemoApplication>(*args)
}
