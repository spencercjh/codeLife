package top.spencercjh.demo.service

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import top.spencercjh.demo.entity.User

@Service
@Scope(WebApplicationContext.SCOPE_SESSION)
class RegistrationService {
    var user = User()
}