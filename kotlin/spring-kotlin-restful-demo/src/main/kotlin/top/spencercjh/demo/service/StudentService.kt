package top.spencercjh.demo.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_PAGE_SIZE
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant.DEFAULT_SORT_COLUMN
import top.spencercjh.demo.dao.ClazzRepository
import top.spencercjh.demo.dao.StudentRepository
import top.spencercjh.demo.entity.Student
import top.spencercjh.demo.entity.StudentVO

/**
 * @author spencer
 */
@Service
class StudentService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var clazzRepository: ClazzRepository

    fun getAllStudents(page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE, name: String? = null, vararg sort: String = arrayOf(DEFAULT_SORT_COLUMN))
            : Page<Student> {
        logger.debug("${javaClass.simpleName} getAllStudents:")
        return if (name != null) {
            studentRepository.findAll(Example.of(Student(name = name), ExampleMatcher.matchingAny()),
                    PageRequest.of(page, size, Sort.by(Sort.DEFAULT_DIRECTION, *sort)))
        } else {
            studentRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.DEFAULT_DIRECTION, *sort)))
        }
    }

    fun getStudentsByClassId(classId: Int, page: Int = DEFAULT_PAGE, size: Int = DEFAULT_PAGE_SIZE, vararg sort: String = arrayOf(DEFAULT_SORT_COLUMN))
            : Page<Student> {
        logger.debug("${javaClass.simpleName} getStudentsByClass:")
        return studentRepository.findStudentsByClazzId(
                classId,
                PageRequest.of(page, size, Sort.by(Sort.DEFAULT_DIRECTION, *sort)))
    }

    fun getStudentByClassAndStudentId(classId: Int, studentId: Int): Student? {
        logger.debug("${javaClass.simpleName} getStudentByClassAndStudentId:")
        return studentRepository.findStudentByClazzIdAndId(clazz_id = classId, id = studentId)
    }

    fun createStudent(classId: Int, studentVO: StudentVO): Student {
        logger.debug("${javaClass.simpleName} createStudent:")
        val clazz = clazzRepository.findByIdOrNull(classId) ?: throw RuntimeException("Class does not exist")
        val student = Student(studentVO)
        student.clazz = clazz
        val saveResult = studentRepository.saveAndFlush(student)
        return if (saveResult.id == null) {
            throw RuntimeException("Save Student Failed")
        } else {
            saveResult
        }
    }
}