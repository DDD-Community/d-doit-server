package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.domain.UserService
import com.ddd.ddoit.dto.AuthRequest
import com.ddd.ddoit.jwt.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val userService: UserService, val jwtTokenProvider: JwtTokenProvider, val authenticationManager: AuthenticationManager) {

    @PostMapping("/signup")
    fun signUp(@RequestBody req: AuthRequest): Long? {
        return userService.signupUser(req)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequest): String{
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(req.email, ""))
        SecurityContextHolder.getContext().authentication = auth
        val user = auth as User
        return jwtTokenProvider.createToken(user.id?:11, listOf())
    }

}