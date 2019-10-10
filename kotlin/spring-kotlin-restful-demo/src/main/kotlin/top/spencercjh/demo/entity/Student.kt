package top.spencercjh.demo.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

/**
 * 学生类
 * @param id 数据库自增长的Id
 * @param name 姓名
 * @param clazz 班级对象
 * @param phone 手机号
 * @param email 邮箱
 * @param createTimeStamp 数据库的创建时间戳
 * @param updateTimestamp 数据库的更新时间戳
 *
 */
@Entity
@Table(name = "student")
data class Student(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        @Column(name = "name")
        @NotNull
        var name: String,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "clazz_id", referencedColumnName = "id")
        @NotNull
        var clazz: Clazz,

        @Column(name = "phone")
        var phone: String? = null,

        @Column(name = "email")
        @Email
        var email: String? = null,

        @Column(columnDefinition = "smallint")
        @NotNull
        @Enumerated
        var sex: Sex,

        @Column(name = "create_time")
        @NotNull
        @CreationTimestamp
        var createTimeStamp: Timestamp = Timestamp(System.currentTimeMillis()),

        @NotNull
        @Column(name = "update_time")
        @UpdateTimestamp
        var updateTimestamp: Timestamp = Timestamp(System.currentTimeMillis())) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        if (id != other.id) return false
        if (name != other.name) return false
        if (clazz != other.clazz) return false
        if (phone != other.phone) return false
        if (email != other.email) return false
        if (sex != other.sex) return false
        if (createTimeStamp != other.createTimeStamp) return false
        if (updateTimestamp != other.updateTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + clazz.hashCode()
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + sex.hashCode()
        result = 31 * result + createTimeStamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "Student(id=$id, name='$name', clazz=${clazz.name}, phone=$phone, email=$email, sex=$sex, createTimeStamp=$createTimeStamp, updateTimestamp=$updateTimestamp)"
    }

    enum class Sex {
        Male, Female
    }
}