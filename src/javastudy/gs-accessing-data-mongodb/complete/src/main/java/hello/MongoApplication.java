package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spencer
 */
@SpringBootApplication
@EnableMongoRepositories
@RestController
public class MongoApplication {
    @Autowired
    private TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity test() {
        // it is a tricky
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(testService.findAll());
    }
}
