package com.ddd.ddoit.exception

import org.springframework.http.HttpStatus

enum class BaseErrorCodeException (val status: HttpStatus, val message: String, val code: Int) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", 700),

    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다. 다시 입력해주세요.", 901),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다. 다시 입력해주세요." ,900),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.", 800),
    FAILED_TO_SAVE_USER(HttpStatus.NOT_FOUND, "사용자를 등록에 실패했습니다.", 801),

}