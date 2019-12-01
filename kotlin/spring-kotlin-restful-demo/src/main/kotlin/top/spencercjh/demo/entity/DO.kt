@file:Suppress("JpaDataSourceORMInspection")

package top.spencercjh.demo.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*

/**
 * 班级类
 * @param id
 * @param name 班级名
 * @param students 班级学生
 * @param createTimeStamp 数据库的创建时间戳
 * @param updateTimestamp 数据库的更新时间戳
 * @author spencer
 */
@Entity
@Table(name = "clazz")
data class Clazz(@Id
                 @GeneratedValue(strategy = GenerationType.IDENTITY)
                 var id: Int? = null,

                 @Column(name = "name")
                 var name: String? = null,

                 @OneToMany(fetch = FetchType.EAGER,
                         mappedBy = "clazz",
                         orphanRemoval = true)
                 @JsonIgnoreProperties(value = ["clazz"])
                 var students: List<Student> = ArrayList(),

                 @Column(name = "create_time")
                 @CreationTimestamp
                 var createTimeStamp: Timestamp = Timestamp(System.currentTimeMillis()),

                 @Column(name = "update_time")
                 @UpdateTimestamp
                 var updateTimestamp: Timestamp = Timestamp(System.currentTimeMillis())) {
    override fun toString(): String {
        return "Clazz(id=$id, name='$name', students=$students, createTimeStamp=$createTimeStamp, updateTimestamp=$updateTimestamp)"
    }
}

/**
 * 学生类
 * @param id 数据库自增长的Id
 * @param name 姓名
 * @param clazz 班级对象
 * @param phone 手机号
 * @param email 邮箱
 * @param createTimeStamp 数据库的创建时间戳
 * @param updateTimestamp 数据库的更新时间戳
 * @author spencer
 */
@Entity
@Table(name = "student", uniqueConstraints = [UniqueConstraint(columnNames = ["phone"])])
data class Student(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        @Column(name = "name")
        var name: String? = null,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "clazz_id", referencedColumnName = "id")
        @JsonIgnoreProperties(value = ["students"])
        var clazz: Clazz? = null,

        @Column(name = "phone")
        var phone: String? = null,

        @Column(name = "email")
        var email: String? = null,

        @Column(columnDefinition = "smallint")
        @Enumerated
        var sex: Sex? = Sex.Unknown,

        @Column(name = "create_time")
        @CreationTimestamp
        var createTimeStamp: Timestamp = Timestamp(System.currentTimeMillis()),

        @Column(name = "update_time")
        @UpdateTimestamp
        var updateTimestamp: Timestamp = Timestamp(System.currentTimeMillis())) {

    constructor(vo: StudentVO) : this(name = vo.name, sex = vo.sex, phone = vo.phone, email = vo.email)

    override fun toString(): String {
        return "Student(id=$id, name='$name', clazz=${clazz?.name
                ?: null.toString()}, phone=$phone, email=$email, sex=$sex, createTimeStamp=$createTimeStamp, updateTimestamp=$updateTimestamp)"
    }
}

enum class Sex {
    Male, Female, Unknown
}