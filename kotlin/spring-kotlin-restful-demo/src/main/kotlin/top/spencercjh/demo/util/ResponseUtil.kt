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

    fun <A> success(code: Int = HttpStatus.OK.value(), message: String = "Request success", body: A? = null): ResponseEntity<Result<A>> {
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

    fun <A> failed(httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR, message: String = "Request failed"): ResponseEntity<Result<A>> {
        return ResponseEntity(
                Result.Builder<A>().code(httpStatus.value()).status(false).message(message).build(),
                httpStatus
        )
    }

    private fun getHttpStatus(code: Int): HttpStatus {
        return try {
            HttpStatus.valueOf(code)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            HttpStatus.INTERNAL_SERVER_ERROR
        }
    }

    class Result<T>(val code: Int?,
                    val message: String?,
                    val status: Boolean?,
                    val body: T? = null,
                    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Result<*>

            if (code != other.code) return false
            if (message != other.message) return false
            if (status != other.status) return false
            if (body != other.body) return false
            if (timestamp != other.timestamp) return false

            return true
        }

        override fun hashCode(): Int {
            var result = code ?: 0
            result = 31 * result + (message?.hashCode() ?: 0)
            result = 31 * result + (status?.hashCode() ?: 0)
            result = 31 * result + (body?.hashCode() ?: 0)
            result = 31 * result + timestamp.hashCode()
            return result
        }

        override fun toString(): String {
            return "Result(code=$code, message=$message, status=$status, body=$body, timestamp=$timestamp)"
        }

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