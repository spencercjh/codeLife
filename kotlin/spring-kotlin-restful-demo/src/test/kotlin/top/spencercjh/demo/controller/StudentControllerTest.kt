package top.spencercjh.demo.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class StudentControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    private val prefix="/api/v1"
    @Test
    fun findAllStudents() {
        mockMvc.perform(get("$prefix/students"))
                .andExpect(status().isOk)
    }

    @Test
    fun findStudentsByClass() {
    }

    @Test
    fun findStudentByClassAndStudentId() {
    }
}