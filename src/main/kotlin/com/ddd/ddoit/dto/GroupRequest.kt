package com.ddd.ddoit.dto

import com.ddd.ddoit.domain.Group

data class GroupRequest(
    val name: String,
    val description: String,
    val notice: String,
) {
    fun toEntity(): Group {
        return Group(name, description, notice, 50L)
    }
}
