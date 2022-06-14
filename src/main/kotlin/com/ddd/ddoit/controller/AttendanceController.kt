package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.HttpResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AttendanceController() {

    @PutMapping("/attendance/{id}")
    fun checkUserAttendance(@PathVariable id:Long, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Unit>> {
        return ResponseEntity(HttpResponse(
            200, "출석 체크 완료", Unit
        ), HttpStatus.OK
        )
    }
}