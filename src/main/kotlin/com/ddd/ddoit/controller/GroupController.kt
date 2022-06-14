package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.AttendanceEvent
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.group.GroupRequest
import com.ddd.ddoit.dto.group.GroupResponse
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.dto.attendance.AttendanceRequest
import com.ddd.ddoit.dto.attendance.AttendanceResponse
import com.ddd.ddoit.service.AttendanceService
import com.ddd.ddoit.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController(val groupService: GroupService, val attendanceService: AttendanceService) {

    @PostMapping("/group")
    fun saveGroup(@RequestBody groupRequest: GroupRequest, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Long>>{
        return ResponseEntity(HttpResponse(
            201, "그룹 생성 완료", groupService.saveGroup(groupRequest, user).id!!
        ) ,HttpStatus.CREATED)
    }

    @GetMapping("/group/{id}")
    fun findGroup(@PathVariable id: Long): ResponseEntity<HttpResponse<GroupResponse?>> {
        val group = groupService.findGroup(id)
        return ResponseEntity(HttpResponse(
            200, "그룹 찾기 완료", GroupResponse.toEntity(group)
        ), HttpStatus.OK)
    }

    @GetMapping("/group/{id}/join")
    fun joinGroup(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Unit>> {
        return ResponseEntity(HttpResponse(
            200, "그룹 참가 완료", groupService.joinGroup(id, user)
        ), HttpStatus.OK)
    }


    @GetMapping("/group/{id}/exit")
    fun exitGroup(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Unit>> {
        return ResponseEntity(HttpResponse(
            200, "그룹 탈퇴 완료", groupService.exitGroup(id, user)
        ), HttpStatus.OK)
    }


    @PostMapping("/group/{id}/attendance") //그룹 내 출석 이벤트 시작
    fun startAttendanceEvent(@PathVariable id: Long, @AuthenticationPrincipal user:User, @RequestBody request: AttendanceRequest): ResponseEntity<HttpResponse<AttendanceResponse>>{
        return ResponseEntity(HttpResponse(
            200, "그룹 출석 시작", AttendanceResponse.toEntity(attendanceService.triggerEvent(request, user, id))
        ), HttpStatus.OK)
    }

    @GetMapping("/group/{id}/attendance") //그룹내 출석 이벤트 처리하는지?
    fun findAttendanceEvent(@PathVariable id: Long, @AuthenticationPrincipal user:User): ResponseEntity<HttpResponse<AttendanceEvent>>{
        return ResponseEntity(HttpResponse(
            200, "그룹 출석 이벤트 출력", attendanceService.findCurrentEvent(id)
        ), HttpStatus.OK)
    }

    @GetMapping("/group/{id}/attendances") //그룹내의 출석현황 노출
    fun listAttendanceEvent(@PathVariable id: Long, @AuthenticationPrincipal user:User): ResponseEntity<HttpResponse<Unit>>{
        return ResponseEntity(HttpResponse(
            200, "그룹 출석 이벤트 출력", Unit
        ), HttpStatus.OK)
    }

    /*@PutMapping("/group/{id}")
    fun updateGroupInfo(@PathVariable id: Long, @RequestBody groupRequest: GroupRequest, @AuthenticationPrincipal user: User): ResponseEntity
    <HttpResponse<T>>{
        return ResponseEntity(HttpResponse(
            201, "그룹 생성 완료", groupService.updateGroup(groupRequest, user)
        ) ,HttpStatus.CREATED)
    }*/
}