package com.ddd.ddoit.controller

import com.ddd.ddoit.config.SecurityConfig
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.AuthRequest
import com.ddd.ddoit.dto.SocialType
import com.ddd.ddoit.exception.BaseErrorCodeException
import com.ddd.ddoit.exception.BaseException
import com.ddd.ddoit.jwt.JwtAuthenticationEntryPoint
import com.ddd.ddoit.jwt.JwtTokenProvider
import com.ddd.ddoit.service.UserService
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel
import org.springframework.restdocs.hypermedia.LinkDescriptor
import org.springframework.restdocs.hypermedia.LinksSnippet
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [AuthController::class])
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

    //MockMVC Field
    private val AuthRequestSnippet = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("name").type(JsonFieldType.STRING).description("회원이름"),
            fieldWithPath("socialId").type(JsonFieldType.STRING).description("소셜 ID명 (현 카카오 ID)")
        )

    private val loginResponseSnippet = responseFields(
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드 번호"),
            fieldWithPath("msg").type(JsonFieldType.STRING).description("응답 메세지"),
            fieldWithPath("body").type(JsonFieldType.STRING).description("User JWT 토큰 값"),
    )

    private val signUpResponseSnippet = responseFields(
        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드 번호"),
        fieldWithPath("msg").type(JsonFieldType.STRING).description("응답 메세지"),
        fieldWithPath("body").type(JsonFieldType.NUMBER).description("API 응답 값"),
    )

    private val errorResponseSnippet = responseFields(
        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드 번호"),
        fieldWithPath("msg").type(JsonFieldType.STRING).description("응답 메세지"),
        fieldWithPath("name").type(JsonFieldType.STRING).description("에러 Code 명칭"),
        fieldWithPath("trace").type(JsonFieldType.NULL).description("에러 위치 추적 (디버그용)"),
    )

    private val request = AuthRequest("test@test.com", "테스트", "1234")
    private val body = jacksonObjectMapper().writeValueAsString(request)
    private val user = User(request.name, request.email, SocialType.KAKAO, request.socialId)

    @Test
    @DisplayName("회원가입 테스트")
    fun signUp() {
        given(userService.signupUser(request)).willReturn(1L)

        mockMvc.perform(post("/signup")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body").value("1"))
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.msg").value("회원 가입 완료"))
            .andDo(MockMvcRestDocumentationWrapper.document("회원가입", ResourceSnippetParametersBuilder()
                .tag("유저 관련 API") //대분류
                .description("회원 가입")
            , snippets = arrayOf(AuthRequestSnippet, signUpResponseSnippet)
            ))

    }

    @Test
    @DisplayName("로그인 테스트")
    fun login() {
        given(userService.login(request)).willReturn(user)
        given(jwtTokenProvider.createToken(user.socialId, SocialType.KAKAO, listOf())).willReturn("abcd")

        mockMvc.perform(post("/login")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body").value("abcd"))
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.msg").value("로그인 완료"))
            .andDo(MockMvcRestDocumentationWrapper.document("로그인", ResourceSnippetParametersBuilder()
                .tag("유저 관련 API")
                .description("로그인")
            , snippets = arrayOf( AuthRequestSnippet, loginResponseSnippet))
            )
    }

    @Test
    @DisplayName("로그인 실패상황")
    fun loginFailTest(){
        val failRequest = AuthRequest("test@test.com", "테스트", "12324")
        val body = jacksonObjectMapper().writeValueAsString(failRequest)

        given(userService.login(failRequest)).willThrow(BaseException(BaseErrorCodeException.INVALID_USER))

        mockMvc.perform(post("/login")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.name").value("INVALID_USER"))
            .andExpect(jsonPath("$.code").value("900"))
            .andExpect(jsonPath("$.msg").value("유저 혹은 비밀번호가 일치하지 않습니다. 다시 입력해주세요."))
            .andDo(MockMvcRestDocumentationWrapper.document("로그인 실패", ResourceSnippetParametersBuilder()
                .tag("유저 관련 API")
                .description("로그인")
            , snippets = arrayOf(AuthRequestSnippet, errorResponseSnippet)
            )
            )
    }
}