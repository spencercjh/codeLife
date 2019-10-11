package top.spencercjh.demo.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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

    fun getAllStudents(page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE, sort: String = "id"): List<Student> {
        val students = studentRepository.findAll(PageRequest.of(page, size, Sort(Sort.DEFAULT_DIRECTION, sort))).content
        logger.debug("StudentService getStudents:")
        students.forEach { student -> logger.debug(student.toString()) }
        return students
    }

    fun getStudentsByClass(classId: String, page: Int, size: Int, sort: String): List<Student> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getStudentByClassAndStudentId(classId: String, studentId: String): Student {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}