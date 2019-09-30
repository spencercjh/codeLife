package top.spencercjh.demo

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@Slf4j
class SpringKotlinDemoApplication {

}

fun main(args: Array<String>) {
    runApplication<SpringKotlinDemoApplication>(*args)
}
