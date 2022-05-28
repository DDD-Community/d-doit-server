package com.ddd.ddoit.exception

import org.springframework.http.HttpStatus

enum class BaseErrorCodeException (val status: HttpStatus, val message: String, val code: Int) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", 700),

    INVALID_USER(HttpStatus.BAD_REQUEST, "유저 혹은 비밀번호가 일치하지 않습니다. 다시 입력해주세요.", 900),
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "중복된 이름입니다. 다시 입력해주세요." ,901),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.", 800),
    FAILED_TO_SAVE_USER(HttpStatus.NOT_FOUND, "사용자를 등록에 실패했습니다.", 801),

}