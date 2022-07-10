package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.dto.group.GroupListResponse
import com.ddd.ddoit.service.GroupService
import com.ddd.ddoit.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val groupService: GroupService, val userService: UserService) {

    /**
     * 유저가 가지고 있는 방들 노출
     */
    @GetMapping("/user/groups")
    fun findMyGroups(@AuthenticationPrincipal user: User): ResponseEntity<HttpResponse<List<GroupListResponse?>>>{
        return ResponseEntity(HttpResponse(
            200, "유저의 모든 리스트 공유",  groupService.listUserGroup(user)
        ), HttpStatus.OK)
    }

    @GetMapping("/user")
    fun isDuplicateUserName(@RequestParam name: String): ResponseEntity<HttpResponse<Boolean>>{
        return  ResponseEntity(HttpResponse(
            200, "유저 닉네임 중복여부", userService.isDuplicateUsername(name)
        ), HttpStatus.OK)
    }
}