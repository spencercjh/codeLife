package top.spencercjh.demo.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE_SIZE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_SORT_COLUMN
import top.spencercjh.demo.entity.Student
import top.spencercjh.demo.service.StudentService
import top.spencercjh.demo.util.ResponseUtil
import top.spencercjh.demo.util.ResponseUtil.Result
import javax.validation.Valid
import javax.validation.constraints.Positive

/**
 * enhance restful knowledge reference: https://www.scienjus.com/my-restful-api-best-practices/
 * @author spencer
 */
@RestController
@RequestMapping("/api/v1")
@Validated
class StudentController(@Autowired val studentService: StudentService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/students")
    fun findAllStudents(@RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE.toString())
                        page: Int,
                        @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE.toString())
                        size: Int,
                        @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_COLUMN)
                        sort: String,
                        @RequestParam(value = "name", required = false) @Valid
                        name: String?)
            : ResponseEntity<Result<Page<Student>>> {
        logger.debug("request /students findAllStudent")
        val students = studentService.getAllStudents(page, size, sort, name)
        return if (!students.isEmpty)
            ResponseUtil.success(body = students)
        else ResponseUtil.failed(HttpStatus.NOT_FOUND, "there are no students meet the criteria")
    }

    @GetMapping("/classes/{classId}/students")
    fun findStudentsByClass(@PathVariable @Positive classId: Int,
                            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE.toString())
                            page: Int,
                            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE.toString())
                            size: Int,
                            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_COLUMN)
                            sort: String)
            : ResponseEntity<Result<Page<Student>>> {
        logger.debug("request /classes/{classId}/students findStudentsByClass")
        val students = studentService.getStudentsByClassId(classId, page, size, sort)
        return if (!students.isEmpty)
            ResponseUtil.success(body = students)
        else ResponseUtil.failed(HttpStatus.NOT_FOUND, "there are no students meet the criteria")
    }

    @GetMapping("/classes/{classId}/students/{studentId}")
    fun findStudentByClassAndStudentId(@PathVariable @Positive
                                       classId: Int,
                                       @PathVariable @Positive
                                       studentId: Int)
            : ResponseEntity<Result<Student>> {
        logger.debug("request /classes/{classId}/students/{studentId} findStudentByClassAndStudentId")
        val student: Student? = studentService.getStudentByClassAndStudentId(classId, studentId)
        return if (student != null)
            ResponseUtil.success(body = student)
        else ResponseUtil.failed(HttpStatus.NOT_FOUND, "there are no students meet the criteria")
    }
}