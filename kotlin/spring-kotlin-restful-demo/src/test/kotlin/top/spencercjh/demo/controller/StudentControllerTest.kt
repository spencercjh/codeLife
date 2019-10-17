package top.spencercjh.demo.controller

import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import top.spencercjh.demo.SpringKotlinRestfulDemoApplication.Constant
import top.spencercjh.demo.entity.Student

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class StudentControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    private val prefix = "/api/v1"
    @Test
    fun findAllStudents() {
        mockMvc.perform(get("$prefix/students"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Request success"))
                .andExpect(jsonPath("$.body").exists())
                .andExpect(jsonPath("$.body.content").exists())
                .andExpect(jsonPath("$.body.content").isArray)
                .andExpect(jsonPath("$.body.content", hasSize<Student>(Constant.DEFAULT_PAGE_SIZE)))
                .andExpect(jsonPath("$.body.pageable").isMap)
                .andExpect(jsonPath("$.body.sort").isMap)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun findStudentsByClass() {
        mockMvc.perform(get("$prefix/classes/2/students").param("size", (Constant.MOCK_STUDENT_AMOUNT * 2).toString()))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Request success"))
                .andExpect(jsonPath("$.body").exists())
                .andExpect(jsonPath("$.body.content").exists())
                .andExpect(jsonPath("$.body.content").isArray)
                .andExpect(jsonPath("$.body.content", hasSize<Student>(Constant.MOCK_STUDENT_AMOUNT * 2)))
                .andExpect(jsonPath("$.body.content[0].clazz.id").value(2))
                .andExpect(jsonPath("$.body.content[0].clazz.name").value(Constant.CLASS_NAME_TWO))
                .andExpect(jsonPath("$.body.pageable").isMap)
                .andExpect(jsonPath("$.body.sort").isMap)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun findStudentByClassAndStudentId() {
        mockMvc.perform(get("$prefix/classes/2/students/91"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Request success"))
                .andExpect(jsonPath("$.body").exists())
                .andExpect(jsonPath("$.body").isMap)
                .andExpect(jsonPath("$.body.name").value("蔡佳昊"))
                .andExpect(jsonPath("$.body.sex").value(Student.Sex.Male.name))
                .andExpect(jsonPath("$.body.id").value(91))
                .andExpect(jsonPath("$.body.clazz").isMap)
                .andExpect(jsonPath("$.body.clazz.id").value(2))
                .andExpect(jsonPath("$.body.clazz.name").value(Constant.CLASS_NAME_TWO))
                .andDo(MockMvcResultHandlers.print())
    }
}