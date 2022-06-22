package com.ddd.ddoit.dto

import com.ddd.ddoit.domain.Group

data class GroupDetailResponse(
    val name: String,
    val description: String,
    val notice: String,
    val role: GroupRoleType,
) {
    companion object {

        fun toDto(group: Group, role: GroupRoleType): GroupDetailResponse {
            return GroupDetailResponse(group.name, group.description, group.notice, role)
        }
    }
}
