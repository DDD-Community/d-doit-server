package com.ddd.ddoit.controller

import com.ddd.ddoit.service.UserService
import com.ddd.ddoit.dto.AuthRequest
import com.ddd.ddoit.dto.HttpResponse
import com.ddd.ddoit.dto.SocialType
import com.ddd.ddoit.jwt.JwtTokenProvider
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val userService: UserService, val jwtTokenProvider: JwtTokenProvider) {

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(@RequestBody req: AuthRequest, @RequestParam social: String): ResponseEntity<HttpResponse<Long?>> {
        return ResponseEntity(HttpResponse(
            200,"회원 가입 완료", userService.signupUser(req, social)),
            HttpStatus.OK)
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequest, @RequestParam social: String): ResponseEntity<HttpResponse<String>>{
        return ResponseEntity(HttpResponse(
            200, "로그인 완료", jwtTokenProvider.createToken(userService.login(req, social).socialId, SocialType.byCode(social), listOf())
        ), HttpStatus.OK)
    }

    /**
     * 테스트용
     */
    @GetMapping("/test")
    fun test(): String{
        return "test"
    }

}