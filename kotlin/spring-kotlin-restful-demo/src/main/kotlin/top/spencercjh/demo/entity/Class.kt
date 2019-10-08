package top.spencercjh.demo.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Date
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "class")
class Class(@Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @NotNull
            var id: Int,

            @Column(name = "name")
            @NotNull
            var name: String,

            @OneToMany(fetch = FetchType.LAZY, mappedBy = "clazz", orphanRemoval = true)
            var students: List<Student> = ArrayList(),

            @Column(name = "create_time")
            @NotNull
            @CreationTimestamp
            var createTimeStamp: Date,

            @NotNull
            @Column(name = "update_time")
            @UpdateTimestamp
            var updateTimestamp: Date) {

}