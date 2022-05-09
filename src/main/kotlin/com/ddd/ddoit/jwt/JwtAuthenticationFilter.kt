package com.ddd.ddoit.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(var jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = jwtTokenProvider.resolveToken(request)?:""
        if ( !token.startsWith("Bearer ")){
            filterChain.doFilter(request, response)
            return
        }
        if (!jwtTokenProvider.validateToken(token.split(" ")[1].trim())) {
            filterChain.doFilter(request, response)
            return
        }
        SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthentication(token)
        filterChain.doFilter(request, response)
    }

}