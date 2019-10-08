package top.spencercjh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Spencer
 */
@Controller
public class PageController {
    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @GetMapping("/test")
    public Flux<Map<String, Object>> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("test1", "123");
        map.put("test2", 987);
        return Flux.just(map);
    }
}
