package top.spencercjh.demo.config

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author SpencerCJH
 * @date 2019/12/1 22:21
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class GlobalExceptionHandlerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun handleConstraintViolationException() {
        mockMvc.perform(get("/classes/-1/students"))
                .andExpect(status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun handleMethodArgumentTypeMismatchException() {
        mockMvc.perform(get("/classes/abc/students"))
                .andExpect(status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andDo(MockMvcResultHandlers.print())
    }

   /* @Test
    fun handle() {
        mockMvc.perform(get("/classes//students"))
                .andExpect(status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andDo(MockMvcResultHandlers.print())
    }*/
}