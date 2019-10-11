package top.spencercjh.demo.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE_SIZE
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Student

@Service
class StudentService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var studentRepository: StudentRepository

    fun getAllStudents(page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE, sort: String = "id", name: String? = null)
            : List<Student> {
        logger.debug("StudentService getAllStudents:")
        val students = if (name != null)
            studentRepository.findAll(Example.of(Student(name = name), ExampleMatcher.matchingAny()),
                    PageRequest.of(page, size, Sort(Sort.DEFAULT_DIRECTION, sort))).content
        else studentRepository.findAll(PageRequest.of(page, size, Sort(Sort.DEFAULT_DIRECTION, sort))).content
        students.forEach { student -> logger.debug(student.toString()) }
        return students
    }

    fun getStudentsByClassId(classId: String, page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE, sort: String = "id")
            : List<Student> {
        logger.debug("StudentService getStudentsByClass:")
        val students = studentRepository.findStudentsByClazzId(
                classId.toInt(),
                PageRequest.of(page, size, Sort(Sort.DEFAULT_DIRECTION, sort))).content
        students.forEach { student -> logger.debug(student.toString()) }
        return students
    }

    fun getStudentByClassAndStudentId(classId: String, studentId: String): Student? {
        logger.debug("StudentService getStudentByClassAndStudentId:")
        return studentRepository.findStudentByClazzIdAndId(clazz_id = classId.toInt(), id = studentId.toInt())
    }
}