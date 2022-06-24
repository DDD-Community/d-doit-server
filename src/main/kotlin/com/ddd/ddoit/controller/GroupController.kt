package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.GroupDetailResponse
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.dto.attendance.AttendanceRequest
import com.ddd.ddoit.dto.attendance.AttendanceResponse
import com.ddd.ddoit.dto.group.GroupRequest
import com.ddd.ddoit.dto.group.GroupResponse
import com.ddd.ddoit.dto.group.GroupUpdateRequest
import com.ddd.ddoit.service.AttendanceService
import com.ddd.ddoit.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController(val groupService: GroupService, val attendanceService: AttendanceService) {

    /**
     * 그룹 생성
     */
    @PostMapping("/group")
    fun saveGroup(@RequestBody groupRequest: GroupRequest, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Long>>{
        return ResponseEntity(HttpResponse(
            201, "그룹 생성 완료", groupService.saveGroup(groupRequest, user).id!!
        ) ,HttpStatus.CREATED)
    }

    /**
     * 그룹 찾기.
     */
    @GetMapping("/group/{id}")
    fun findGroup(@PathVariable id: Long): ResponseEntity<HttpResponse<GroupResponse?>> {
        val group = groupService.findGroup(id)
        return ResponseEntity(HttpResponse(
            200, "그룹 찾기 완료", GroupResponse.toEntity(group)
        ), HttpStatus.OK)
    }

    /**
     * 그룹 정보 노출
     */
    @GetMapping("/group/{id}/detail")
    fun findGroupDetail(@PathVariable id:Long, @AuthenticationPrincipal user: User):
            ResponseEntity<HttpResponse<GroupDetailResponse?>>{
        return ResponseEntity(HttpResponse(
            200, "그룹 정보 노출 완료", groupService.findGroupDetail(id, user)
        ), HttpStatus.OK)
    }

    /**
     * 방 참가
     */
    @GetMapping("/group/{id}/join")
    fun joinGroup(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Unit>> {
        return ResponseEntity(HttpResponse(
            200, "그룹 참가 완료", groupService.joinGroup(id, user)
        ), HttpStatus.OK)
    }


    /**
     * 방 탈퇴
     */
    @GetMapping("/group/{id}/exit")
    fun exitGroup(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Unit>> {
        return ResponseEntity(HttpResponse(
            200, "그룹 탈퇴 완료", groupService.exitGroup(id, user)
        ), HttpStatus.OK)
    }


    /**
     * 그룹 내 출석 이벤트 시작
     */
    @PostMapping("/group/{id}/attendance")
    fun startAttendanceEvent(@PathVariable id: Long, @AuthenticationPrincipal user:User, @RequestBody request: AttendanceRequest): ResponseEntity<HttpResponse<AttendanceResponse>>{
        val group = groupService.findGroup(id)
        return ResponseEntity(HttpResponse(
            200, "그룹 출석 시작", AttendanceResponse.toEntity(attendanceService.triggerEvent(request, user, group))
        ), HttpStatus.OK)
    }

    /**
     * 그룹 내 출석 이벤트 진행중인지 확인
     */
    @GetMapping("/group/{id}/attendance")
    fun findAttendanceEvent(@PathVariable id: Long, @AuthenticationPrincipal user:User): ResponseEntity<HttpResponse<AttendanceResponse>>{
        return ResponseEntity(HttpResponse(
            200, "그룹 출석 이벤트 출력", AttendanceResponse.toEntity(attendanceService.findCurrentEvent(id))
        ), HttpStatus.OK)
    }

    /**
     * 그룹내의 유저의 출석 현황 체크
     */
    @GetMapping("/group/{id}/user/attendances")
    fun listAttendanceEvent(@PathVariable id: Long, @AuthenticationPrincipal user:User): ResponseEntity<HttpResponse<MutableMap<String, Int>>>{
        return ResponseEntity(HttpResponse(
            200, "그룹 내 현 유저 출석 현황", attendanceService.findUserAttendanceInGroup(user, id)
        ), HttpStatus.OK)
    }

    /**
     * 그룹 공지 수정
     */
    @PutMapping("/group/{id}/notice")
    fun updateGroupNotice(@PathVariable id: Long, @AuthenticationPrincipal user: User,
                        @RequestBody groupRequest: GroupUpdateRequest): ResponseEntity<HttpResponse<Unit>> {
        return ResponseEntity(HttpResponse(
            200, "그룹 공지 수정 완료", groupService.updateGroupNotice(id, groupRequest, user)
        ) ,HttpStatus.OK)
    }

    /**
     * 그룹 설명 수정
     */
    @PutMapping("/group/{id}/description")
    fun updateGroupDescription(@PathVariable id: Long, @AuthenticationPrincipal user: User,
                        @RequestBody groupRequest: GroupUpdateRequest): ResponseEntity<HttpResponse<Unit>> {
        return ResponseEntity(HttpResponse(
            200, "그룹 설명 수정 완료", groupService.updateGroupDescription(id, groupRequest, user)
        ) ,HttpStatus.OK)
    }
}