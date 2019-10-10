package top.spencercjh.demo.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import top.spencercjh.demo.entity.Student
import top.spencercjh.demo.service.StudentService
import java.util.*

@RestController(value = "/api/v1")
class StudentController(@Autowired val studentService: StudentService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/students")
    fun findAllStudent(page: Int, size: Int, sort: String): ResponseEntity<List<Student>> {
        logger.debug("request /students findAllStudent")
        return ResponseEntity.of(Optional.of(studentService.getStudents(page, size, sort)))
    }

    @GetMapping("/class/{classId}/student/{studentId}")
    fun findStudentByClassAndId(@PathVariable classId: String, @PathVariable studentId: String) {
        logger.debug("request /class/{classId}/student/{studentId} findStudentByClassAndId")
    }
}