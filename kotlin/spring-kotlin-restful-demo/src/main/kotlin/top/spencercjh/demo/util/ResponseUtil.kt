package top.spencercjh.demo.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import top.spencercjh.demo.util.ResponseUtil.Result
import java.sql.Timestamp

/**
 * package [ResponseEntity] as a util. Its body is a package class [Result] with [Result.Builder] pattern
 * @author spencer
 */
object ResponseUtil {

    fun <A> success(code: Int = HttpStatus.OK.value(), message: String = "Request success", body: A): ResponseEntity<Result<A>> {
        return ResponseEntity(
                Result.Builder<A>().code(code).status(true).message(message).body(body).build(),
                HttpStatus.OK
        )
    }

    fun <A> failed(code: Int = HttpStatus.INTERNAL_SERVER_ERROR.value(), message: String = "Request failed"): ResponseEntity<Result<A>> {
        return ResponseEntity(
                Result.Builder<A>().code(code).status(false).message(message).build(),
                getHttpStatus(code)
        )
    }

    private fun getHttpStatus(code: Int): HttpStatus {
        return try {
            HttpStatus.valueOf(code)
        } catch (e: IllegalArgumentException) {
            HttpStatus.INTERNAL_SERVER_ERROR
        }
    }

    data class Result<T>(val code: Int?,
                         val message: String?,
                         val status: Boolean?,
                         val body: T? = null,
                         val timestamp: Timestamp = Timestamp(System.currentTimeMillis())) {

        data class Builder<T>(var code: Int? = null,
                              var message: String? = null,
                              var status: Boolean? = null,
                              var body: T? = null
        ) {
            fun code(code: Int) = apply { this.code = code }
            fun message(message: String) = apply { this.message = message }
            fun status(status: Boolean) = apply { this.status = status }
            fun body(body: T?) = apply { this.body = body }
            fun build() = Result(code, message, status, body)
        }
    }
}