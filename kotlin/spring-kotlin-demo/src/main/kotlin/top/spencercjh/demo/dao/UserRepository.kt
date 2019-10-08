package top.spencercjh.demo.dao

import org.springframework.context.annotation.Scope
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import top.spencercjh.demo.entity.User

@Service
@Scope(WebApplicationContext.SCOPE_SESSION)
interface UserRepository : JpaRepository<User, Long> {
}