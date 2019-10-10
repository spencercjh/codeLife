package top.spencercjh.demo.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Student

@Service
class StudentService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var studentRepository: StudentRepository
    @Value("\${default.pageSize}")
    val defaultPageSize: Int = 15

    fun getStudents(page: Int, size: Int, sort: String): List<Student> {
        val students = try {
            studentRepository.findAll(PageRequest.of(page, size, Sort(Sort.DEFAULT_DIRECTION, sort))).content
        } catch (e: Exception) {
            studentRepository.findAll(PageRequest.of(0, defaultPageSize)).content
        }
        students.forEach { student -> logger.debug(student.toString()) }
        return students
    }
}