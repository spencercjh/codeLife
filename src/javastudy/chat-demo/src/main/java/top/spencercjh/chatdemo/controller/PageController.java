package top.spencercjh.chatdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Spencer
 */
@Controller
public class PageController {
    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
