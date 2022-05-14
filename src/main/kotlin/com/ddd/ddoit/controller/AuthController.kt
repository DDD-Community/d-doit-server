package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.domain.UserService
import com.ddd.ddoit.dto.AuthRequest
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.jwt.JwtTokenProvider
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val userService: UserService, val jwtTokenProvider: JwtTokenProvider) {

    @PostMapping("/signup")
    fun signUp(@RequestBody req: AuthRequest): ResponseEntity<HttpResponse<Long?>> {
        return ResponseEntity(HttpResponse(
            200,"회원 가입 완료", userService.signupUser(req)),
            HttpStatus.OK)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequest): ResponseEntity<HttpResponse<String>>{
        return ResponseEntity(HttpResponse(
            200, "로그인 완료", jwtTokenProvider.createToken(userService.login(req).email, listOf())
        ), HttpStatus.OK)
    }

    @GetMapping("/test")
    fun test(): String{
        return "test"
    }

}