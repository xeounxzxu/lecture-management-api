package io.xeounxzxu.lecturemanagementapi.exception

import io.xeounxzxu.lecturemanagementapi.exception.ErrorResponse.Companion.DEFAULT_ERROR
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class AppRestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun error(
        ex: IllegalArgumentException
    ): ResponseEntity<ErrorResponse> {
        log.error(ex) { ex.message }
        return ResponseEntity.of(
            Optional.of(
                ErrorResponse.toBadRequest(ex.message)
            )
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException::class)
    fun error(
        ex: IllegalStateException
    ): ResponseEntity<ErrorResponse> {
        log.error(ex) { ex.message }
        return ResponseEntity.of(
            Optional.of(
                ErrorResponse.toBadRequest(ex.message)
            )
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun error(
        ex: HttpMessageNotReadableException
    ): ResponseEntity<ErrorResponse> {
        log.error(ex) { ex.message }
        return ResponseEntity.of(
            Optional.of(
                ErrorResponse.toBadRequest()
            )
        )
    }

    @ExceptionHandler(BusinessException::class)
    fun error(
        ex: BusinessException
    ): ResponseEntity<ErrorResponse> {
        log.error(ex) { ex.message }
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

