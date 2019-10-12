package top.spencercjh.demo.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import top.spencercjh.demo.entity.Clazz
import top.spencercjh.demo.entity.Student

/**
 * test ResponseUtil use Junit5
 * @author spencer
 * @see ResponseUtil
 */
internal class ResponseUtilTest {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val student: Student = Student(name = "蔡佳昊",
            clazz = Clazz(name = "test"),
            sex = Student.Sex.Male,
            phone = RandomUtil.getRandomPhone(),
            email = RandomUtil.getRandomEmail())

    @Test
    fun success() {
        // custom parameter test
        var expect = ResponseUtil.Result(code = HttpStatus.ACCEPTED.value(), status = true, message = "Test success", body = student)
        // different timeStamp
        Thread.sleep(1000)
        var responseEntity = ResponseUtil.success(code = HttpStatus.ACCEPTED.value(), message = "Test success", body = student)
        logger.debug(responseEntity.toString())
        assertEquals(expect.code, responseEntity.body!!.code)
        assertEquals(expect.message, responseEntity.body!!.message)
        assertEquals(expect.status, responseEntity.body!!.status)
        assertEquals(expect.body, responseEntity.body!!.body)
        assertNotEquals(expect, responseEntity.body)
        // default parameter test
        expect = ResponseUtil.Result(code = HttpStatus.OK.value(), message = "Request success", status = true)
        // different timeStamp
        Thread.sleep(1000)
        responseEntity = ResponseUtil.success()
        logger.debug(responseEntity.toString())
        assertEquals(expect.code, responseEntity.body!!.code)
        assertEquals(expect.message, responseEntity.body!!.message)
        assertEquals(expect.status, responseEntity.body!!.status)
        assertEquals(expect.body, responseEntity.body!!.body)
        assertTrue(responseEntity.body!!.body == null)
        assertNotEquals(expect, responseEntity.body)
    }

    @Test
    fun fail() {
        // custom parameter test
        var expect = ResponseUtil.Result<Any>(code = HttpStatus.SERVICE_UNAVAILABLE.value(), status = false, message = "Test failed")
        // different timeStamp
        Thread.sleep(1000)
        var responseEntity = ResponseUtil.failed<Any>(code = HttpStatus.SERVICE_UNAVAILABLE.value(), message = "Test failed")
        logger.debug(responseEntity.toString())
        assertEquals(expect.code, responseEntity.body!!.code)
        assertEquals(expect.message, responseEntity.body!!.message)
        assertEquals(expect.status, responseEntity.body!!.status)
        assertEquals(expect.body, responseEntity.body!!.body)
        assertNotEquals(expect, responseEntity.body)
        // default parameter test
        expect = ResponseUtil.Result(code = HttpStatus.INTERNAL_SERVER_ERROR.value(), message = "Request failed", status = false)
        // different timeStamp
        Thread.sleep(1000)
        responseEntity = ResponseUtil.failed(code = 500)
        logger.debug(responseEntity.toString())
        assertEquals(expect.code, responseEntity.body!!.code)
        assertEquals(expect.message, responseEntity.body!!.message)
        assertEquals(expect.status, responseEntity.body!!.status)
        assertEquals(expect.body, responseEntity.body!!.body)
        assertTrue(responseEntity.body!!.body == null)
        assertNotEquals(expect, responseEntity.body)
    }
}