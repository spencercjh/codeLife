package top.spencercjh.demo.dao

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import top.spencercjh.demo.entity.Student

/**
 * @author spencer
 */
@Service
interface StudentRepository : JpaRepository<Student, Int> {
    fun findStudentByClazzIdAndId(clazz_id: Int, id: Int): Student?

    fun findStudentsByClazzId(id: Int, pageable: Pageable): Page<Student>
}