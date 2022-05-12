package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.domain.UserService
import com.ddd.ddoit.dto.AuthRequest
import com.ddd.ddoit.jwt.JwtTokenProvider
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
    fun signUp(@RequestBody req: AuthRequest): Long? {
        return userService.signupUser(req)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequest): String{
        return jwtTokenProvider.createToken(userService.login(req).email, listOf())
    }

    @GetMapping("/test")
    fun test(): String{
        return "권한이 있어서 노출이 가능합니다."
    }

}