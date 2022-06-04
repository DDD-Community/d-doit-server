package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.GroupRequest
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class GroupController(val groupService: GroupService) {

    @PostMapping("/group")
    fun saveGroup(@RequestBody groupRequest: GroupRequest, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Long>>{
        return ResponseEntity(HttpResponse(
            201, "그룹 생성 완료", groupService.saveGroup(groupRequest, user).id!!
        ) ,HttpStatus.CREATED)
    }

    @GetMapping("/group/{id}")
    fun findGroup(@PathVariable id: Long): ResponseEntity<HttpResponse<GroupResponse>> {
        val group = groupService.findGroup(id)
        return ResponseEntity(HttpResponse(
            200, "그룹 찾기 완료", GroupResponse(group.name, group.description)
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