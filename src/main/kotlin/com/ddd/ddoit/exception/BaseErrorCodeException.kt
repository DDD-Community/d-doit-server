package com.ddd.ddoit.exception

import org.springframework.http.HttpStatus

enum class BaseErrorCodeException (val status: HttpStatus, val message: String, val code: Int) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", 700),

    INVALID_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 계정이에요!", 900),
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "이미 누군가가 사용하고 있어요! 다른 걸로 해볼까요?" ,901),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 계정이에요!", 800),
    FAILED_TO_SAVE_USER(HttpStatus.NOT_FOUND, "계정 등록에 실패했어요.", 801),
    USER_IN_GROUP(HttpStatus.BAD_REQUEST, "이미 이 방의 멤버에요!", 802),


    //Group 관련한 에러코드 750 ~
    GROUP_NOT_FOUND(HttpStatus.BAD_REQUEST, "방을 찾을 수가 없어요.", 750),
    GROUP_IN_NOT_USER(HttpStatus.BAD_REQUEST, "방의 멤버가 아니에요!", 751),

    //출석 관련한 에러코드 600
    NOT_ADMIN(HttpStatus.BAD_REQUEST, "출석 시작은 방장만 할 수 있어요!", 600),
    NOT_CURRENT_ATTENDANCE(HttpStatus.BAD_REQUEST, "현재 진행 중인 출석이 없어요!", 601),
    NOT_ATTENDANCE_EVENT(HttpStatus.BAD_REQUEST, "이미 종료된 혹은 참여할 수 없는 출석이에요!", 602),
    NOT_CERTIFICATION(HttpStatus.BAD_REQUEST, "틀린 코드에요! 다시 입력하세요!", 603),
    TOO_LATE_ATTENDANCE(HttpStatus.BAD_REQUEST, "현재 시각보다 늦은 시각의 출석을 등록할 수 없어요!", 604),
    END_ATTENDANCE(HttpStatus.BAD_REQUEST, "이미 종료된 출석입니다.", 604),

}