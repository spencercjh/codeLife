package top.spencercjh.demo.service

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.studentCount

/**
 * test student service use JUnit5
 * @author Spencer
 * @see StudentService
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class StudentServiceTest() {
    private val logger = LoggerFactory.getLogger(javaClass)
    @Autowired
    private lateinit var studentService: StudentService

    @Test
    fun getAllStudents() {
        // default sort by Id
        val noSortList = studentService.getAllStudents(size = 15)
        assertTrue(noSortList[0].id == 1)
        assertTrue(noSortList[noSortList.size - 1].id == 15)
        // sort by name
        val sortByNameList = studentService.getAllStudents(sort="name")
    }

    @Test
    fun getStudentsByClass() {
    }

    @Test
    fun getStudentByClassAndStudentId() {
    }
}