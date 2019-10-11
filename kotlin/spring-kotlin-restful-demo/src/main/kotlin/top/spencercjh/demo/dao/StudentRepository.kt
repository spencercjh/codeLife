package top.spencercjh.demo.dao

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import top.spencercjh.demo.entity.Student

@Service
interface StudentRepository : JpaRepository<Student, Int> {
    fun findStudentByClazzIdAndId(clazz_id: Int, id: Int): Student?

    fun findStudentsByClazzName(clazz_name: String): List<Student>
    fun findStudentsByClazzName(clazz_name: String, pageable: Pageable): Page<Student>

    fun findStudentsByClazzNameLike(clazz_name: String): List<Student>
    fun findStudentsByClazzNameLike(clazz_name: String, pageable: Pageable): Page<Student>

    fun findStudentsByClazzId(id: Int): List<Student>
    fun findStudentsByClazzId(id: Int, pageable: Pageable): Page<Student>
}