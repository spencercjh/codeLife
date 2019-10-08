package top.spencercjh.demo.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Date
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "student")
data class Student(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @NotNull
        var id: Int,

        @Column(name = "name")
        @NotNull
        var name: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "class_id", referencedColumnName = "id")
        var clazz: Class,

        @Column(name = "phone")
        @NotNull
        var phone: String,

        @Column(name = "email")
        @Email
        var email: String,

        @Column(name = "create_time")
        @NotNull
        @CreationTimestamp
        var createTimeStamp: Date,

        @NotNull
        @Column(name = "update_time")
        @UpdateTimestamp
        var updateTimestamp: Date
) {
}