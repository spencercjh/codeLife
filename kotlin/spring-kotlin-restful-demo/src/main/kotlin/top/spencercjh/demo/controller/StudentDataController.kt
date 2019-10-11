package top.spencercjh.demo.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE_SIZE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_SORT_COLUMN
import top.spencercjh.demo.entity.Student
import top.spencercjh.demo.service.StudentService
import java.util.*

@RestController
@RequestMapping("/api/v1")
class StudentController(@Autowired val studentService: StudentService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/students")
    fun findAllStudents(@RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE.toString()) page: Int,
                        @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE.toString()) size: Int,
                        @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_COLUMN) sort: String,
                        @RequestParam(value = "name", required = false) name: String? = null)
            : ResponseEntity<List<Student>> {
        logger.debug("request /students findAllStudent")
        val students = studentService.getAllStudents(page, size, sort, name)
        return if (students.isNotEmpty()) ResponseEntity.of(Optional.of(students)) else ResponseEntity(emptyList(), HttpStatus.NOT_FOUND)
    }

    @GetMapping("/classes/{classId}/students")
    fun findStudentsByClass(@PathVariable classId: String,
                            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE.toString()) page: Int,
                            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE.toString()) size: Int,
                            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_COLUMN) sort: String)
            : ResponseEntity<List<Student>> {
        logger.debug("request /classes/{classId}/students findStudentsByClass")
        val students = studentService.getStudentsByClassId(classId, page, size, sort)
        return if (students.isNotEmpty()) ResponseEntity.of(Optional.of(students)) else ResponseEntity(emptyList(), HttpStatus.NOT_FOUND)
    }

    @GetMapping("/classes/{classId}/students/{studentId}")
    fun findStudentByClassAndStudentId(@PathVariable classId: String,
                                       @PathVariable studentId: String): ResponseEntity<Student> {
        logger.debug("request /classes/{classId}/students/{studentId} findStudentByClassAndStudentId")
        val student: Student? = studentService.getStudentByClassAndStudentId(classId, studentId)
        return if (student != null) ResponseEntity.of(Optional.of(student)) else ResponseEntity.notFound().build()
    }
}