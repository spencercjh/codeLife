package top.spencercjh.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author Spencer
 */
@Slf4j
@EnableWebFlux
@SpringBootApplication
public class ReactiveChatDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveChatDemoApplication.class, args);
    }
}
