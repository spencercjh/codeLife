package top.spencercjh.demo.entity

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * @author SpencerCJH
 * @date 2019/12/1 19:50
 */
data class StudentVO(
        @get:NotEmpty
        val name: String,
        // todo: Custom validation of enum
        val sex: Sex,
        @get:NotEmpty
        val phone: String,
        @get:NotEmpty
        @get:Email
        val email: String
)