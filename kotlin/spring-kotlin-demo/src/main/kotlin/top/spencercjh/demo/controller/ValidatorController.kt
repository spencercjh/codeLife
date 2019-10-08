package top.spencercjh.demo.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import top.spencercjh.demo.dao.UserRepository
import top.spencercjh.demo.entity.User
import javax.validation.Valid


/**
 * Thymeleaf page controller
 */
@Controller
class ValidatorController(@Autowired val userRepository: UserRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/index")
    fun index(model: Model): String {
        model.addAttribute("user", User())
        return "index"
    }

    @GetMapping("/congrats")
    fun congrats(user: User, errors: Errors, model: Model): String {
        return when {
            errors.hasErrors() -> "index"
            else -> {
                model.addAttribute("user", user)
                "congrats"
            }
        }
    }

    @PostMapping("/index")
    fun doPost(
            //The @Valid annotation tells Spring to Validate this object
            @Valid user: User, attributes: RedirectAttributes,
            //Spring injects this class into this method. It will hold any errors that are found on the object
            errors: Errors): String {
        return when {
            //Test for errors
            errors.hasErrors() -> "index"
            else -> {
                //Otherwise proceed to the next page
                userRepository.save(user)
                attributes.addFlashAttribute("user", user)
                "redirect:/congrats"
            }
        }
    }
}