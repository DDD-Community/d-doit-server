package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.Group
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.group.GroupRequest
import com.ddd.ddoit.dto.SocialType
import com.ddd.ddoit.jwt.JwtAuthenticationEntryPoint
import com.ddd.ddoit.jwt.JwtTokenProvider
import com.ddd.ddoit.service.AttendanceService
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
    lateinit var attendanceService: AttendanceService

    @MockBean
    lateinit var jwtTokenProvider: JwtTokenProvider

    @MockBean
    lateinit var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint

    val request = GroupRequest("?????? ??????", "?????? ??????", "?????????????????????")
    val user = User("??????", "test", SocialType.KAKAO, "1234")
    var group = Group(request.name, request.description, request.notice, 50)
    val body = jacksonObjectMapper().writeValueAsString(request)

    private val GroupRequestSnippet = PayloadDocumentation.requestFields(
        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("?????????"),
        PayloadDocumentation.fieldWithPath("description").type(JsonFieldType.STRING).description("????????????"),
        PayloadDocumentation.fieldWithPath("notice").type(JsonFieldType.STRING).description("????????????"),
    )

    private val GroupResponseSnippet = PayloadDocumentation.responseFields(
        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
        PayloadDocumentation.fieldWithPath("msg").type(JsonFieldType.STRING).description("?????? ?????????"),
        PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
    )

    private val GroupFindResponseSnippet = PayloadDocumentation.responseFields(
        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
        PayloadDocumentation.fieldWithPath("msg").type(JsonFieldType.STRING).description("?????? ?????????"),
        PayloadDocumentation.fieldWithPath("body").type(JsonFieldType.OBJECT).description("?????? ?????? ??????"),
        PayloadDocumentation.fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
        PayloadDocumentation.fieldWithPath("body.name").type(JsonFieldType.STRING).description("?????? ?????????"),
        PayloadDocumentation.fieldWithPath("body.description").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
    )


    @Test
    @DisplayName("?????? ??????")
    @WithMockCustomUser("??????")
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
            .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("?????? ?????? ??????"))
            .andDo(
                MockMvcRestDocumentationWrapper.document("?????? ??????", ResourceSnippetParametersBuilder()
                .tag("?????? ?????? API") //?????????
                .description("?????? ??????")
                , snippets = arrayOf(GroupRequestSnippet, GroupResponseSnippet)
                )
            )
    }


    @Test
    @DisplayName("?????? ??????")
    @WithMockCustomUser("??????")
    fun findGroup() {
        group.id = 1L
        given(groupService.findGroup(anyLong())).willReturn(group)

        mockMvc.perform(get("/group/{id}", 1)
            .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWthb18xMjM0Iiwicm9sZXMiOltdLCJpYXQiOjE2NTQyNDI1NDQsImV4cCI6MTY1NDMyODk0NH0.SniH9LmLDVPMNm3a-q9wx14_HCWBf1M08wY2Ie6dTL0")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk)
        .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value("?????? ??????"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.body.description").value("?????? ??????"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("?????? ?????? ??????"))
        .andDo(
            MockMvcRestDocumentationWrapper.document("?????? ??????", ResourceSnippetParametersBuilder()
                .tag("?????? ?????? API") //?????????
                .description("?????? ??????")
                , snippets = arrayOf(GroupFindResponseSnippet)
            )
        )
    }
}