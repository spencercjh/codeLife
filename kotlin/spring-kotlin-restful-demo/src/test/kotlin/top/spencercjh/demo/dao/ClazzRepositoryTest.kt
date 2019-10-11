package top.spencercjh.demo.dao

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

/**
 * test JPA Repository
 * @author Spencer
 * @see ClazzRepository
 */
@RunWith(SpringRunner::class)
@SpringBootTest
internal class ClazzRepositoryTest {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var studentRepository: StudentRepository
    @Autowired
    lateinit var clazzRepository: ClazzRepository

    @Test
    fun testInitClazz() {
        val actualClazzList = clazzRepository.findAll()
        logger.debug("actual clazz is:\t${actualClazzList.first()}")
        Assert.assertEquals(2, actualClazzList.size)
    }

    /**
     * test cascade relationship between class and students
     */
    @Test
    @Transactional
    @Rollback(value = true)
    fun testDeleteClass() {
        clazzRepository.deleteAll()
        Assert.assertTrue(clazzRepository.findAll().isEmpty())
        Assert.assertTrue(studentRepository.findAll().isEmpty())
    }
}