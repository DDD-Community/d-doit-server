package com.ddd.ddoit.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(var jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = jwtTokenProvider.resolveToken(request)?:""
        if ( !token.startsWith("Bearer ")){
            filterChain.doFilter(request, response)
            return
        }
        val bearerToken = token.split(" ")[1].trim()
        if (!jwtTokenProvider.validateToken(bearerToken)) {
            filterChain.doFilter(request, response)
            return
        }
        SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthentication(bearerToken)
        filterChain.doFilter(request, response)
    }

}