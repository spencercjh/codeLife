package top.spencercjh.springsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

/**
 * @author Spencer
 */
@SpringBootApplication
@EnableWebFlux
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

    @Bean(value = "testMapping")
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route().GET("/test",
                serverRequest -> ServerResponse.temporaryRedirect(URI.create("/actuator")).build()).build();
    }
}
