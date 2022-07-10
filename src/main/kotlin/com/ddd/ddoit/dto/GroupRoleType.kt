package com.ddd.ddoit.dto

enum class GroupRoleType(
    val id: Long,
    val names: String,
)
{
    ADMIN(1, "방장"),
    USER(2, "유저"),;


    companion object {
        fun valueIdOf(id: Long): GroupRoleType? {
            return GroupRoleType.values().find { roles -> roles.id == id  }
        }
    }
}