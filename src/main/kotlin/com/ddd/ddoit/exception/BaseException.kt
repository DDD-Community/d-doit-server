package com.ddd.ddoit.exception

import org.springframework.http.HttpStatus

class BaseException(baseException: BaseErrorCodeException) : RuntimeException() {
    val code: Int = baseException.code
    val exceptName: String = baseException.name
    val msg: String = baseException.message
    val status: HttpStatus = baseException.status
}