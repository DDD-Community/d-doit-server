package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.GroupRequest
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController(val groupService: GroupService) {

    @PostMapping("/group")
    fun saveGroup(@RequestBody groupRequest: GroupRequest, @AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<Long>>{
        return ResponseEntity(HttpResponse(
            201, "그룹 생성 완료", groupService.saveGroup(groupRequest, user).id!!
        ) ,HttpStatus.CREATED)
    }

//    @GetMapping("/group/{id}")
//    fun findGroup(@PathVariable id: Long): ResponseEntity<HttpResponse<GroupDto>{
//        return ResponseEntity(HttpResponse(
//            200, "그룹 찾기 완료", groupService.findById()
//        ))
//    }
}