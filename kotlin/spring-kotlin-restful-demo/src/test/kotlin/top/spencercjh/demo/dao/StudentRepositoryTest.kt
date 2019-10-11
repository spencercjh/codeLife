package top.spencercjh.demo.dao

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.CLASS_NAME
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.MOCK_STUDENT_AMOUNT
import top.spencercjh.demo.entity.Clazz
import top.spencercjh.demo.entity.Student
import top.spencercjh.demo.util.RandomUtil
import java.util.stream.Collectors

/**
 * test JPA Repository use JUnit4
 * @author Spencer
 * @see StudentRepository
 */
@RunWith(SpringRunner::class)
@SpringBootTest
internal class StudentRepositoryTest {
    private val logger = LoggerFactory.getLogger(javaClass)
    @Autowired
    lateinit var studentRepository: StudentRepository
    @Autowired
    lateinit var clazzRepository: ClazzRepository

    /**
     * test Init Data in Application.kt whether become effective
     * @see SpringKotlinRestfulDemoApplication.initData
     */
    @Test
    fun testInitStudent() {
        val actualStudentList = studentRepository.findAll()
        logger.debug("actual student list is:\t")
        actualStudentList.forEach { student -> logger.debug(student.toString()) }
        Assert.assertEquals(MOCK_STUDENT_AMOUNT, actualStudentList.size)
    }

    /**
     * test JPA customize method
     * @see StudentRepository.findStudentsByClazzName
     */
    @Test
    @Transactional
    @Rollback(value = true)
    fun testFindStudentsByClassName() {
        // init data query
        val actualStudentList = studentRepository.findStudentsByClazzName(SpringKotlinRestfulDemoApplication.CLASS_NAME)
        val expectStudentList = studentRepository.findAll()
        Assert.assertEquals(expectStudentList.size, actualStudentList.size)
        /**
         * mock new class and student
         */
        fun mockNewData(expectStudentList: MutableList<Student>): Map<String, Any> {
            var newClazz = Clazz(name = "test")
            newClazz = clazzRepository.save(newClazz)
            Assert.assertTrue(newClazz.id == clazzRepository.findAll()[0].id!! + 1)
            var newStudent = RandomUtil.getStudents(1, newClazz)[0]
            newStudent = studentRepository.save(newStudent)
            Assert.assertEquals(newStudent.id, expectStudentList[expectStudentList.size - 1].id!! + 1)
            return mapOf("clazz" to newClazz, "student" to newStudent)
        }
        // mock new data query
        val newData = mockNewData(expectStudentList)
        Assert.assertEquals(newData["student"] as Student, studentRepository.findStudentsByClazzName("test")[0])
    }

    /**
     * test JPA customize method
     * @see StudentRepository.findStudentsByClazzNameLike
     */
    @Test
    fun testFindStudentByClassNameLike() {
        val actualStudentList = studentRepository.findStudentsByClazzNameLike("%" + CLASS_NAME.substring(0, 2) + "%")
        val expectStudentList = studentRepository.findAll()
        Assert.assertEquals(expectStudentList.size, actualStudentList.size)
    }

    /**
     * test cascade relationship between students and class
     */
    @Test
    @Transactional
    @Rollback(value = true)
    fun testDeleteStudent() {
        val predicate = { student: Student -> student.id!! % 2 == 0 }
        val originalStudents = studentRepository.findAll()
        // delete in database
        val wantToDeleteStudents = originalStudents
                .stream()
                .filter(predicate)
                .collect(Collectors.toList())
        studentRepository.deleteInBatch(wantToDeleteStudents)
        // delete in origin list
        Assert.assertTrue(originalStudents.removeIf(predicate))
        // compare size
        Assert.assertEquals(originalStudents.size, studentRepository.findAll().size)
    }
}