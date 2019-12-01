package top.spencercjh.demo.config

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.*
import java.util.stream.Collectors
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

/**
 * reference: https://sadique.io/blog/2015/12/05/validating-requestparams-and-pathvariables-in-spring-mvc/
 * @author spencer
 */
@ControllerAdvice
@Component
class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(exception: MethodArgumentNotValidException): Map<*, *> {
        TODO("This handler cannot be triggered correctly")
        return error(exception.bindingResult.fieldErrors
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList()))
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(exception: ConstraintViolationException): Map<*, *> {
        return error(exception.constraintViolations
                .stream()
                .map { obj: ConstraintViolation<*> -> obj.message }
                .collect(Collectors.toList()))
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(exception: MethodArgumentTypeMismatchException): Map<*, *> {
        return error(exception.message)
    }


    private fun error(message: Any?): Map<*, *> {
        return Collections.singletonMap("error", message)
    }

    // TODO handle HttpMessageNotReadableException
}