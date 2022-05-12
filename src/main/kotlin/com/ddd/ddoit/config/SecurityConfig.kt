package com.ddd.ddoit.config

import com.ddd.ddoit.jwt.JwtAuthenticationFilter
import com.ddd.ddoit.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
class SecurityConfig(var jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter(){

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.headers().frameOptions().disable()
        http.authorizeHttpRequests()
                .antMatchers("/login", "/signup", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint { _, res, ex ->
                    run {
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
                    }
                } //인증 실패시 처리
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)

    }

    @Bean
    override fun authenticationManager(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

}