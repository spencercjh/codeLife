package top.spencercjh.demo.service

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.MOCK_STUDENT_AMOUNT
import top.spencercjh.demo.entity.Student

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
        val noSortList = studentService.getAllStudents(size = MOCK_STUDENT_AMOUNT)
        assertTrue(noSortList.size == MOCK_STUDENT_AMOUNT)
        assertTrue(noSortList[0].id == 1)
        assertTrue(noSortList[noSortList.size - 1].id == MOCK_STUDENT_AMOUNT)
        // sort by sex
        val sortBySexList = studentService.getAllStudents(size = MOCK_STUDENT_AMOUNT, sort = "sex")
        assertTrue(sortBySexList.size == MOCK_STUDENT_AMOUNT)
        assertTrue(sortBySexList[0].sex == Student.Sex.Male)
        assertTrue(sortBySexList[sortBySexList.size - 1].sex == Student.Sex.Female)
    }

    @Test
    fun getStudentsByClass() {
    }

    @Test
    fun getStudentByClassAndStudentId() {
    }
}