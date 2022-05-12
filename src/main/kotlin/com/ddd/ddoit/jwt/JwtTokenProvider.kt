package com.ddd.ddoit.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(val userDetailsService: UserDetailsService) {

    companion object {
        const val JWT_SECRET = "keyName123141asdafafvadssssqweqwuiasdy89pgadhyuiadgvjnk"
        const val JWT_EXPIRED_TIME = "100"
    }

    fun getSignKey(): Key {
        return Keys.hmacShaKeyFor(JWT_SECRET.toByteArray(Charsets.UTF_8))
    }

    fun createToken(email: String, roles: List<String>?): String{
        val claims = Jwts.claims().setSubject(email)
        claims["roles"] = roles
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis()+60*24*1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact()
    }

    fun getAuthentication(token: String): Authentication{
        val detail = userDetailsService.loadUserByUsername(getTokenSubject(token));
        return UsernamePasswordAuthenticationToken(detail, "", detail.authorities)
    }

    fun getTokenSubject(token: String): String{
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build()
            .parseClaimsJws(token).body.subject
    }


    fun resolveToken(request : HttpServletRequest): String?{
        return request.getHeader(HttpHeaders.AUTHORIZATION)
    }

    fun validateToken(token: String): Boolean {
        val claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token)
        return !claims.body.expiration.before(Date())
    }

}