package com.ddd.ddoit.controller

import com.ddd.ddoit.config.SecurityConfig
import com.ddd.ddoit.dto.AuthRequest
import com.ddd.ddoit.jwt.JwtAuthenticationEntryPoint
import com.ddd.ddoit.jwt.JwtTokenProvider
import com.ddd.ddoit.service.UserService
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureRestDocs
class AuthControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var securityConfig: SecurityConfig

    @MockBean
    lateinit var userService: UserService

    @MockBean
    lateinit var jwtTokenProvider: JwtTokenProvider

    @MockBean
    lateinit var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint

    @Test
    @DisplayName("회원가입 테스트")
    fun signUp() {
        val request = AuthRequest("test@test.com", "테스트", "1234")
        val body = jacksonObjectMapper().writeValueAsString(request)

        `when`(userService.signupUser(request)).thenReturn(1L)

        mockMvc.perform(post("/signup")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body").value("0"))
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.msg").value("회원 가입 완료"))
            .andDo(MockMvcRestDocumentationWrapper.document("회원가입", ResourceSnippetParametersBuilder()
                .tag("유저 관련 API") //대분류
                .description("회원가입")
            , snippets = arrayOf(requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원이름"),
                    fieldWithPath("socialId").type(JsonFieldType.STRING).description("소셜 ID명 (현 카카오 ID)")
                ), responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드 번호"),
                    fieldWithPath("msg").type(JsonFieldType.STRING).description("응답 메세지"),
                    fieldWithPath("body").type(JsonFieldType.NUMBER).description("API 응답 값"),
                ))
            )
            )

    }

    @Test
    @DisplayName("로그인 테스트")
    fun login() {
        mockMvc.perform(post("/login"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andDo(MockMvcRestDocumentation.document("login"))
    }
}