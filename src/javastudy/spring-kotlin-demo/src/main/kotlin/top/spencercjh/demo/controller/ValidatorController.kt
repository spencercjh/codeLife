package top.spencercjh.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.context.WebApplicationContext
import top.spencercjh.demo.entity.User
import top.spencercjh.demo.service.RegistrationService
import javax.validation.Valid

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
class ValidatorController(@Autowired val registrationService: RegistrationService) {

    @GetMapping("/index")
    fun doGet(model: Model): String {
        model.addAttribute("user", User())
        return "index"
    }

    @PostMapping("/")
    fun doPost(@Valid user: User,  //The @Valid annotation tells Spring to Validate this object
               errors: Errors)  //Spring injects this class into this method. It will hold any
    //errors that are found on the object
            : String {
        val result: String
        when {
            //Test for errors
            errors.hasErrors() -> result = "index"
            else -> {
                //Otherwise proceed to the next page
                registrationService.user = user
                result = "redirect:/congrats"
            }
        }
        return result
    }
}