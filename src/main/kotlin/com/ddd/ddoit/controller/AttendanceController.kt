package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.service.AttendanceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AttendanceController(val attendanceService: AttendanceService) {

    @PostMapping("/attendance/{id}")
    fun registerUserAttendance(@PathVariable id:Long, @AuthenticationPrincipal user: User,
                            @RequestBody request: AttendanceService.AttendanceRegisterResquest)
    : ResponseEntity<HttpResponse<AttendanceRegResponse>> {
        return ResponseEntity(HttpResponse(
            200, "출석 체크 완료", AttendanceRegResponse.toDto(attendanceService.registerAttendance(id, user, request))
        ), HttpStatus.OK
        )
    }
}