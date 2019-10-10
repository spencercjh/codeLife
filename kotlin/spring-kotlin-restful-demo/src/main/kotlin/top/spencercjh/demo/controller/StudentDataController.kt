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

@RestController("/api/v1")
class StudentController(@Autowired val studentService: StudentService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/students")
    fun findAllStudents(page: Int, size: Int, sort: String): ResponseEntity<List<Student>> {
        logger.debug("request /students findAllStudent")
        return ResponseEntity.of(Optional.of(studentService.getAllStudents(page, size, sort)))
    }

    @GetMapping("/classes/{classId}/students")
    fun findStudentsByClass(@PathVariable classId: String, page: Int, size: Int, sort: String): ResponseEntity<List<Student>> {
        logger.debug("request /classes/{classId}/students findStudentsByClass")
        return ResponseEntity.of(Optional.of(studentService.getStudentsByClass(classId, page, size, sort)))
    }

    @GetMapping("/classes/{classId}/students/{studentId}")
    fun findStudentByClassAndStudentId(@PathVariable classId: String, @PathVariable studentId: String): ResponseEntity<Student> {
        logger.debug("request /classes/{classId}/students/{studentId} findStudentByClassAndStudentId")
        return ResponseEntity.of(Optional.of(studentService.getStudentByClassAndStudentId(classId, studentId)))
    }
}