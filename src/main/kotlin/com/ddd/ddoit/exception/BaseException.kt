package com.ddd.ddoit.exception

class BaseException(baseException: BaseErrorCodeException) : RuntimeException() {
    val baseResponseCode: BaseErrorCodeException = baseException
}