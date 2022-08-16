package com.ddd.ddoit.jwt

import com.ddd.ddoit.dto.SocialType
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider(val userDetailsService: UserDetailsService) {

    @Value("\${security.jwt.token.secret}")
    lateinit var JWT_SECRET: String

    @Value("\${security.jwt.token.expired-day}")
    lateinit var JWT_EXPIRED_TIME: String

    fun getSignKey(): Key {
        return Keys.hmacShaKeyFor(JWT_SECRET.toByteArray(Charsets.UTF_8))
    }

    fun createToken(socialId: String, socialType: SocialType, roles: List<String>?): String{
        val claims = Jwts.claims().setSubject(socialType.code+"_"+socialId)
        claims["roles"] = roles
        val issuedAt: Instant = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val expiration: Instant = issuedAt.plus(JWT_EXPIRED_TIME.toLong(), ChronoUnit.DAYS)
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
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