package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.group.GroupListResponse
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val groupService: GroupService) {

    @GetMapping("/user/groups")
    fun findMyGroups(@AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<List<GroupListResponse?>>>{
        return ResponseEntity(HttpResponse(
            200, "유저의 모든 리스트 공유",  groupService.listUserGroup(user)
        ), HttpStatus.OK)
    }
}