package top.spencercjh.demo.entity

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * @author Spencer
 */
@Entity
@Table(name = "test_user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = true)
        @get: NotBlank(message = "{first_name.required}")
        var firstName: String? = null,

        @Column(nullable = true)
        @get: NotBlank(message = "{last_name.required}")
        var lastName: String? = null,

        @Column(nullable = true)
        @get: NotBlank(message = "{email.required}")
        @get: Email(message = "{email.invalid}")
        var email: String? = null,

        @Column(nullable = true)
        @get: NotBlank(message = "{phone.required}")
        var phone: String? = null,

        @Column(nullable = true)
        @get: NotBlank(message = "{address.required}")
        var address: String? = null,

        @Column(nullable = true)
        @get: NotBlank(message = "{city.required}")
        var city: String? = null,

        @Column(nullable = true)
        @get: NotBlank(message = "{province.required}")
        var province: String? = null,

        @Column(nullable = true)
        @get: NotBlank(message = "{zip.required}")
        var zip: String? = null) {
}