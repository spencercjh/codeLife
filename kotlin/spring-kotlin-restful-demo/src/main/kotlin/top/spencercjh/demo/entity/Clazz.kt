package top.spencercjh.demo.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * 班级类
 * @param id
 * @param name 班级名
 * @param students 班级学生
 * @param createTimeStamp 数据库的创建时间戳
 * @param updateTimestamp 数据库的更新时间戳
 */
@Entity
@Table(name = "clazz")
data class Clazz(@Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Int? = null,

            @Column(name = "name")
            @NotNull
            var name: String,

            @OneToMany(fetch = FetchType.EAGER,
                    mappedBy = "clazz",
                    orphanRemoval = true)
            var students: List<Student> = ArrayList(),

            @Column(name = "create_time")
            @NotNull
            @CreationTimestamp
            var createTimeStamp: Timestamp = Timestamp(System.currentTimeMillis()),

            @NotNull
            @Column(name = "update_time")
            @UpdateTimestamp
            var updateTimestamp: Timestamp = Timestamp(System.currentTimeMillis())) {
    @Suppress("DuplicatedCode")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Clazz

        if (id != other.id) {
            return false
        }
        if (name != other.name) {
            return false
        }
        if (students != other.students) {
            return false
        }
        if (createTimeStamp != other.createTimeStamp) {
            return false
        }
        if (updateTimestamp != other.updateTimestamp) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + students.hashCode()
        result = 31 * result + createTimeStamp.hashCode()
        result = 31 * result + updateTimestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "Clazz(id=$id, name='$name', students=$students, createTimeStamp=$createTimeStamp, updateTimestamp=$updateTimestamp)"
    }

}