package top.spencercjh.springsecuritydemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTest {
    private static WebTestClient REST;
    @Autowired
    ApplicationContext context;

    @Before
    public void setup() {
        REST = WebTestClient
                .bindToApplicationContext(this.context)
                .configureClient()
                .build();
    }

    @Test
    public void whenNoCredentialsThenRedirectToLogin() {
        REST.get()
                .uri("/")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @WithMockUser
    public void whenUserHasCredentialsThenSeesGreeting() {
        REST.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello, user");
    }

    @Test
    @WithMockUser(value = "admin", username = "admin", password = "admin", roles = "ADMIN")
    public void whenAdminHasCredentialsThenSeesGreeting() {
        REST.get()
                .uri("/admin")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Admin access: admin");
    }
}
