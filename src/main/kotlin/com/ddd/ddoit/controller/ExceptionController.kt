package com.ddd.ddoit.controller

import com.ddd.ddoit.dto.ErrorResponse
import com.ddd.ddoit.exception.BaseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(BaseException::class)
    fun baseExceptionHandler(err: BaseException): ResponseEntity<ErrorResponse<String?>> {
        return ResponseEntity(ErrorResponse(err.code, err.exceptName, err.msg) ,err.status)
    }
}