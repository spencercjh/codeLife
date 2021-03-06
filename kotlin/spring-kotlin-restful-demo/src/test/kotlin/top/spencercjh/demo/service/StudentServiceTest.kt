package top.spencercjh.demo.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.MOCK_STUDENT_AMOUNT
import top.spencercjh.demo.entity.Sex
import top.spencercjh.demo.entity.StudentVO
import top.spencercjh.demo.util.RandomUtil

/**
 * test student service use JUnit5
 * @author Spencer
 * @see StudentService
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class StudentServiceTest {
    private val logger = LoggerFactory.getLogger(javaClass)
    @Autowired
    private lateinit var studentService: StudentService

    @Test
    fun getAllStudents() {
        // default sort by Id
        val noSortList = studentService.getAllStudents(size = MOCK_STUDENT_AMOUNT)
        assertTrue(noSortList.size == MOCK_STUDENT_AMOUNT)
        assertTrue(noSortList.first().id == 1)
        assertTrue(noSortList.last().id == MOCK_STUDENT_AMOUNT)
        // sort by sex
        val sortBySexList = studentService.getAllStudents(size = MOCK_STUDENT_AMOUNT, sort = *arrayOf("sex"))
        assertTrue(sortBySexList.size == MOCK_STUDENT_AMOUNT)
        assertTrue(sortBySexList.first().sex == Sex.Male ||
                sortBySexList.last().sex == Sex.Female)
        // search name
        val searchResult = studentService.getAllStudents(name = "蔡佳昊").content
        assertTrue(searchResult.isNotEmpty())
        assertTrue(searchResult.first().name == "蔡佳昊")
    }

    @Test
    fun getStudentsByClassId() {
        // default sort by Id
        val classOneStudents = studentService.getStudentsByClassId(classId = 1, size = MOCK_STUDENT_AMOUNT)
        assertTrue(classOneStudents.size == MOCK_STUDENT_AMOUNT)
        val classTwoStudents = studentService.getStudentsByClassId(classId = 2, size = MOCK_STUDENT_AMOUNT * 2)
        assertTrue(classTwoStudents.size == MOCK_STUDENT_AMOUNT * 2)
        // sort by sex
        val sortedClassTwoStudents = studentService.getStudentsByClassId(classId = 2, size = MOCK_STUDENT_AMOUNT * 2,
                sort = *arrayOf("sex"))
        assertTrue(sortedClassTwoStudents.size == MOCK_STUDENT_AMOUNT * 2)
        assertTrue(sortedClassTwoStudents.first().sex == Sex.Male)
        assertTrue(sortedClassTwoStudents.last().sex == Sex.Female)
        // empty list
        val emptyList = studentService.getStudentsByClassId(classId = 3)
        assertTrue(emptyList.isEmpty)
    }

    @Test
    fun getStudentByClassAndStudentId() {
        val student = studentService.getStudentByClassAndStudentId(1, 1)
        logger.debug(student.toString())
        assertEquals(1, student!!.id)
        assertEquals(1, student.clazz!!.id)
        val nullStudent = studentService.getStudentByClassAndStudentId(3, 1)
        logger.debug(nullStudent.toString())
        assertEquals(null, nullStudent)
    }

    @Test
    @Rollback
    fun createStudent() {
        val student = StudentVO(name = "newSaved", phone = RandomUtil.getRandomPhone(), email = RandomUtil.getRandomEmail(), sex = Sex.Male)
        val classId = 1
        val expectSize = studentService.getStudentsByClassId(classId = classId, size = MOCK_STUDENT_AMOUNT * 2).content.size + 1
        val result = studentService.createStudent(classId, student)
        assertEquals(expectSize, studentService.getStudentsByClassId(classId, size = MOCK_STUDENT_AMOUNT * 2).content.size)
        assertEquals(student.name, result.name)
        assertEquals(student.phone, result.phone)
        assertEquals(student.email, result.email)
        assertEquals(student.sex, result.sex)
        assertNotNull(result.id)
        // class not exist
        val notExistedClassId = 3
        assertThrows(RuntimeException::class.java) { studentService.createStudent(notExistedClassId, student) }
        // duplicated phone bounded
        assertThrows(RuntimeException::class.java) {
            studentService.createStudent(classId,
                    StudentVO(name = "newSaved", phone = "1234567890", email = RandomUtil.getRandomEmail(), sex = Sex.Male))
        }
    }
}