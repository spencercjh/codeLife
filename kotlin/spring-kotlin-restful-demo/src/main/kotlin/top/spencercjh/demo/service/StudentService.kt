package top.spencercjh.demo.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE_SIZE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_SORT_COLUMN
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Student

/**
 * @author spencer
 */
@Service
class StudentService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var studentRepository: StudentRepository

    fun getAllStudents(page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE, name: String? = null, vararg sort: String = arrayOf(DEFAULT_SORT_COLUMN))
            : Page<Student> {
        logger.debug("StudentService getAllStudents:")
        val students = if (name != null)
            studentRepository.findAll(Example.of(Student(name = name), ExampleMatcher.matchingAny()),
                    PageRequest.of(page, size, Sort.by(Sort.DEFAULT_DIRECTION, *sort)))
        else studentRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.DEFAULT_DIRECTION, *sort)))
        students.forEach { student -> logger.debug(student.toString()) }
        return students
    }

    fun getStudentsByClassId(classId: Int, page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE, vararg sort: String = arrayOf(DEFAULT_SORT_COLUMN))
            : Page<Student> {
        logger.debug("StudentService getStudentsByClass:")
        val students = studentRepository.findStudentsByClazzId(
                classId,
                PageRequest.of(page, size, Sort.by(Sort.DEFAULT_DIRECTION, *sort)))
        students.forEach { student -> logger.debug(student.toString()) }
        return students
    }

    fun getStudentByClassAndStudentId(classId: Int, studentId: Int): Student? {
        logger.debug("StudentService getStudentByClassAndStudentId:")
        val student = studentRepository.findStudentByClazzIdAndId(clazz_id = classId, id = studentId)
        logger.debug("search student by ID:${student.toString()}")
        return student
    }
}