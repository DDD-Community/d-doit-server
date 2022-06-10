package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.Group
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.group.GroupRequest
import com.ddd.ddoit.dto.SocialType
import com.ddd.ddoit.jwt.JwtAuthenticationEntryPoint
import com.ddd.ddoit.jwt.JwtTokenProvider
import com.ddd.ddoit.service.GroupService
import com.epages.restdocs.apispec.HeaderDescriptorWithType
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mocking.WithMockCustomUser
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [GroupController::class])
@AutoConfigureRestDocs
class GroupControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var groupService: GroupService

    @MockBean
    lateinit var jwtTokenProvider: JwtTokenProvider

    @MockBean
    lateinit var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint

    val request = GroupRequest("그냥 그룹", "그냥 설명", "공지사항입니다")
    val user = User("이름", "test", SocialType.KAKAO, "1234")
    var group = Group(request.name, request.description, request.notice, 50)
    val body = jacksonObjectMapper().writeValueAsString(request)

    private val GroupRequestSnippet = PayloadDocumentation.requestFields(
        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("이메일"),
        PayloadDocumentation.fieldWithPath("description").type(JsonFieldType.STRING).description("회원이름"),
        PayloadDocumentation.fieldWithPath("notice").type(JsonFieldType.STRING).description("공지사항"),
    )

    private val GroupResponseSnippet = PayloadDocumentation.responseFields(
        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드 번호"),
        PayloadDocumentation.fieldWithPath("msg").type(JsonFieldType.STRING).description("응답 메세지"),
        PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.NUMBER).description("만든 그룹 ID"),
    )

    private val GroupFindResponseSnippet = PayloadDocumentation.responseFields(
        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드 번호"),
        PayloadDocumentation.fieldWithPath("msg").type(JsonFieldType.STRING).description("응답 메세지"),
        PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.OBJECT).description("찾은 그룹 객체"),
        PayloadDocumentation.fieldWithPath("body.name").type(JsonFieldType.STRING).description("찾은 그룹명"),
        PayloadDocumentation.fieldWithPath("body.description").type(JsonFieldType.STRING).description("찾은 그룹 설명"),
    )


    @Test
    @DisplayName("그룹 생성")
    @WithMockCustomUser("이름")
    fun saveGroup() {
        group.id = 1L
        given(groupService.saveGroup(request, SecurityContextHolder.getContext().authentication.principal as User)).willReturn(group)
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true)

        mockMvc.perform(post("/group")
            .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWthb18xMjM0Iiwicm9sZXMiOltdLCJpYXQiOjE2NTQyNDI1NDQsImV4cCI6MTY1NDMyODk0NH0.SniH9LmLDVPMNm3a-q9wx14_HCWBf1M08wY2Ie6dTL0")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("201"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("그룹 생성 완료"))
            .andDo(
                MockMvcRestDocumentationWrapper.document("그룹 생성", ResourceSnippetParametersBuilder()
                .tag("그룹 관련 API") //대분류
                .description("그룹 생성")
                , snippets = arrayOf(GroupRequestSnippet, GroupResponseSnippet)
                )
            )
    }


    @Test
    @DisplayName("그룹 찾기")
    @WithMockCustomUser("이름")
    fun findGroup() {

        given(groupService.findGroup(anyLong())).willReturn(group)

        mockMvc.perform(get("/group/{id}", 1)
            .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWthb18xMjM0Iiwicm9sZXMiOltdLCJpYXQiOjE2NTQyNDI1NDQsImV4cCI6MTY1NDMyODk0NH0.SniH9LmLDVPMNm3a-q9wx14_HCWBf1M08wY2Ie6dTL0")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk)
        .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value("그냥 그룹"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.body.description").value("그냥 설명"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("그룹 찾기 완료"))
        .andDo(
            MockMvcRestDocumentationWrapper.document("그룹 찾기", ResourceSnippetParametersBuilder()
                .tag("그룹 관련 API") //대분류
                .description("그룹 찾기")
                .requestHeaders(HeaderDescriptorWithType("AUTHORIZATION").description("JWT"))
                , snippets = arrayOf(GroupFindResponseSnippet)
            )
        )
    }
}