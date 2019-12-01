package top.spencercjh.demo.util

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.LoggerFactory
import top.spencercjh.demo.entity.Clazz
import top.spencercjh.demo.entity.Sex
import java.util.stream.Stream

/**
 * test RandomUtil use JUnit5
 * @author Spencer
 * @see RandomUtil
 */
internal class RandomUtilTest {

    private val logger = LoggerFactory.getLogger(javaClass)
    @Test
    fun getRandomNum() {
        for (i in 0..99) {
            val random = RandomUtil.getRandomNumber(-10, 10)
            logger.debug("generated random number:\t$random")
            assertTrue(random >= -10 && random <= 10)
        }
    }

    @ParameterizedTest
    @MethodSource("testMethodSource")
    fun getRandomNumThrows(begin: Int, end: Int) {
        assertThrows<IllegalArgumentException> { RandomUtil.getRandomNumber(begin, end) }
    }

    @Test
    fun getEmail() {
        for (i in 0..99) {
            val email = RandomUtil.getRandomEmail()
            logger.debug("generated random email:\t$email")
            assertTrue(email.substring(0, email.indexOf('@')).length == RandomUtil.emailLength)
        }
    }

    @Test
    fun getPhone() {
        for (i in 0..99) {
            val phone = RandomUtil.getRandomPhone()
            logger.debug("generated random phone number:\t$phone")
            assertTrue(phone.length == RandomUtil.phoneNumberLength)
        }
    }

    @Test
    fun getName() {
        for (i in 0..99) {
            val maleName = RandomUtil.getRandomName(Sex.Male)
            logger.debug("male generated random Name:\t$maleName")
            assertTrue(maleName.length <= RandomUtil.nameLength)
            val girlName = RandomUtil.getRandomName(Sex.Female)
            logger.debug("female generated random Name:\t$girlName")
            assertTrue(girlName.length <= RandomUtil.nameLength)
            val randomSexName = RandomUtil.getRandomName()
            logger.debug("random generated random Name:\t$randomSexName")
            assertTrue(randomSexName.length <= RandomUtil.nameLength)
        }
    }

    @Test
    fun getStudents() {
        assertThrows<IllegalArgumentException> { RandomUtil.getRandomStudents(0, Clazz(name = "test")) }
        assertDoesNotThrow { RandomUtil.getRandomStudents(1, Clazz(name = null.toString())) }
        assertThrows<IllegalArgumentException> { RandomUtil.getRandomStudents(5, Clazz(name = "")) }
        val students = RandomUtil.getRandomStudents(20, Clazz(name = "test"))
        students.forEach { student -> logger.debug(student.toString()) }
    }

    companion object {
        @JvmStatic
        @Suppress("unused")
        fun testMethodSource(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(1, 0)
            )
        }
    }
}