package com.ddd.ddoit.exception

import org.springframework.http.HttpStatus

enum class BaseErrorCodeException (val status: HttpStatus, val message: String, val code: Int) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", 700),

    INVALID_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다.", 900),
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "중복된 이름입니다. 다시 입력해주세요." ,901),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.", 800),
    FAILED_TO_SAVE_USER(HttpStatus.NOT_FOUND, "사용자를 등록에 실패했습니다.", 801),
    USER_IN_GROUP(HttpStatus.BAD_REQUEST, "이미 그룹에 포함된 사용자입니다. ", 802),


    //Group 관련한 에러코드 750 ~
    GROUP_NOT_FOUND(HttpStatus.BAD_REQUEST, "그룹을 찾을 수 없습니다.", 750),
    GROUP_IN_NOT_USER(HttpStatus.BAD_REQUEST, "그룹에 유저가 포함되지 않았습니다.", 751),

    //출석 관련한 에러코드 600
    NOT_ADMIN(HttpStatus.BAD_REQUEST, "출석 시작은 그룹장만 가능합니다.", 600),
    NOT_CURRENT_ATTENDANCE(HttpStatus.BAD_REQUEST, "현재 열려있는 출석이 없습니다.", 601),

}