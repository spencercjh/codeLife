package top.spencercjh.springsecuritydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.spencercjh.springsecuritydemo.service.GreetService;

import java.security.Principal;

/**
 * @author Spencer
 */
@RestController
public class GreetController {
    @Autowired
    private GreetService greetService;

    @GetMapping("/")
    public Mono<String> greet(Mono<Principal> principal) {
        return principal
                .map(Principal::getName)
                .map(name -> String.format("Hello, %s", name));
    }

    @GetMapping("/admin")
    public Mono<String> greetAdmin(Mono<Principal> principal) {
        return principal
                .map(Principal::getName)
                .map(name -> String.format("Admin access: %s", name));
    }

    @GetMapping("/superAdmin")
    public Mono<String> greetSuperAdmin(Mono<Principal> principal) {
        return principal
                .map(Principal::getName)
                .map(name -> String.format("SuperAdmin access: %s", name));
    }

    @GetMapping("/greetService")
    public Mono<String> greetService() {
        return greetService.greet();
    }
}
