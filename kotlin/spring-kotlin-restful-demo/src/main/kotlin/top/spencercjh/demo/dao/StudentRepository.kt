package top.spencercjh.demo.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import top.spencercjh.demo.entity.Student

@Service
interface StudentRepository : JpaRepository<Student, Int> {
    fun findStudentsByNameLike(name: String): List<Student>
    fun findStudentsByClazzName(clazz_name: String): List<Student>
    fun findStudentsByClazzNameLike(clazz_name: String): List<Student>
}