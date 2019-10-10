package top.spencercjh.demo.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.defaultPageSize
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Student

@Service
class StudentService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var studentRepository: StudentRepository

    fun getAllStudents(page: Int=0, size: Int=15, sort: String="id"): List<Student> {
        val students = /*try {
            studentRepository.findAll(PageRequest.of(page, size, Sort(Sort.DEFAULT_DIRECTION, sort))).content
        } catch (e: Exception) {
            studentRepository.findAll(PageRequest.of(0, defaultPageSize)).content
        }*/
                studentRepository.findAll(PageRequest.of(page, size, Sort(Sort.DEFAULT_DIRECTION, sort))).content
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