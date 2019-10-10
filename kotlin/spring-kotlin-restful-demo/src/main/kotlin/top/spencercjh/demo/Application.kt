package top.spencercjh.demo

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
    @Autowired
    lateinit var clazzRepository: ClazzRepository
    @Autowired
    lateinit var studentRepository: StudentRepository

    companion object Constant {
        var studentCount: Int = 30
    }

    @PostConstruct
    fun initData() {
        val clazzOne = Clazz(name = "class1")
        clazzOne.students = RandomUtil.getStudents(studentCount, clazzOne)
        clazzRepository.save(clazzOne)
        studentRepository.saveAll(clazzOne.students)
    }
}

fun main(args: Array<String>) {
    runApplication<SpringKotlinRestfulDemoApplication>(*args)
}
