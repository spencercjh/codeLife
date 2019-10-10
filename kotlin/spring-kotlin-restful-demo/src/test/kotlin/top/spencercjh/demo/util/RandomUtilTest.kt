package top.spencercjh.demo.util

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import top.spencercjh.demo.entity.Clazz
import top.spencercjh.demo.entity.Student

/**
 * test RandomUtil use JUnit5
 * @author Spencer
 * @see RandomUtil
 */
internal class RandomUtilTest {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun getRandomNum() {
        assertThrows<IllegalArgumentException> { RandomUtil.getRandomNumber(1, 0) }
        for (i in 0..99) {
            val random = RandomUtil.getRandomNumber(-10, 10)
            logger.debug("generated random number:\t$random")
            assertTrue(random >= -10 && random <= 10)
        }
    }

    @Test
    fun getEmail() {
        for (i in 0..99) {
            val email = RandomUtil.getEmail()
            logger.debug("generated random email:\t$email")
            assertTrue(email.substring(0, email.indexOf('@')).length == RandomUtil.emailLength)
        }
    }

    @Test
    fun getPhone() {
        for (i in 0..99) {
            val phone = RandomUtil.getPhone()
            logger.debug("generated random phone number:\t$phone")
            assertTrue(phone.length == RandomUtil.phoneNumberLength)
        }
    }

    @Test
    fun getName() {
        for (i in 0..99) {
            val maleName = RandomUtil.getName(Student.Sex.Male)
            logger.debug("male generated random Name:\t$maleName")
            assertTrue(maleName.length <= RandomUtil.nameLength)
            val girlName = RandomUtil.getName(Student.Sex.Female)
            logger.debug("female generated random Name:\t$girlName")
            assertTrue(girlName.length <= RandomUtil.nameLength)
            val randomSexName = RandomUtil.getName()
            logger.debug("random generated random Name:\t$randomSexName")
            assertTrue(randomSexName.length <= RandomUtil.nameLength)
        }
    }

    @Test
    fun getStudents() {
        assertThrows<IllegalArgumentException> { RandomUtil.getStudents(0, Clazz(name = "test")) }
        assertDoesNotThrow { RandomUtil.getStudents(1, Clazz(name = null.toString())) }
        assertThrows<IllegalArgumentException> { RandomUtil.getStudents(5, Clazz(name = "")) }
        val students = RandomUtil.getStudents(20, Clazz(name = "test"))
        students.forEach { student -> logger.debug(student.toString()) }
    }
}