package top.spencercjh.demo

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import top.spencercjh.demo.dao.UserRepository
import top.spencercjh.demo.entity.User
import javax.annotation.PostConstruct

@SpringBootApplication
class SpringKotlinDemoApplication {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var userRepository: UserRepository

    @PostConstruct
    fun initData() {
        val savedList: List<User> = userRepository.saveAll(listOf(User(firstName = "spencer",
                lastName = "cai",
                email = "864712437@qq.com",
                phone = "12345678901",
                address = "shanghai",
                city = "shanghai",
                province = "shanghai",
                zip = "0000000"),
                User(firstName = "rita",
                        lastName = "wang",
                        email = "shouspencercjh@foxmail.com",
                        phone = "12345678901",
                        address = "shanghai",
                        city = "shanghai",
                        province = "shanghai",
                        zip = "0000000")))
        savedList.forEach {
            logger.info("[init data] saved one:\t $it")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringKotlinDemoApplication>(*args)
}
