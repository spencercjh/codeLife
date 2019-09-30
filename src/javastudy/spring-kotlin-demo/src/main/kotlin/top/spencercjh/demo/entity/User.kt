package top.spencercjh.demo.entity

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * @author Spencer
 */
data class User(@get: NotBlank(message = "{first_name.required}")
                var firstName: String = "",

                @get: NotBlank(message = "{last_name.required}")
                var lastName: String = "",

                @get: NotBlank(message = "{email.required}")
                @get: Email(message = "{email.invalid}")
                var email: String = "",

                @get: NotBlank(message = "{phone.required}")
                var phone: String = "",

                @get: NotBlank(message = "{address.required}")
                var address: String = "",

                @get: NotBlank(message = "{city.required}")
                var city: String = "",

                @get: NotBlank(message = "{state.required}")
                @get: Size(min = 2, max = 2, message = "{state.size}")
                var state: String = "",

                @get: NotBlank(message = "{zip.required}")
                var zip: String = "")