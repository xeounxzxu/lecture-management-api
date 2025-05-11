package io.xeounxzxu.lecturemanagementapi.exception

import io.xeounxzxu.lecturemanagementapi.exception.ErrorResponse.Companion.DEFAULT_ERROR
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class AppRestControllerAdvice {

    @ExceptionHandler(BusinessException::class)
    fun error(
        ex: BusinessException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(ex.status)
            .body(ErrorResponse(ex.message ?: DEFAULT_ERROR))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun error(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {

        log.error(ex) { ex.message }

        val body = ex.bindingResult.fieldErrors
            .map { it.defaultMessage }
            .firstOrNull()
            ?.let {
                ErrorResponse(message = it)
            } ?: ErrorResponse.toBadRequest()

        return ResponseEntity.of(Optional.of(body))
    }

    @ExceptionHandler(Exception::class)
    fun error(
        ex: Exception
    ): ResponseEntity<ErrorResponse> {
        log.error(ex) { ex.message }
        val body = ErrorResponse.toException()
        return ResponseEntity.of(Optional.of(body))
    }
}

