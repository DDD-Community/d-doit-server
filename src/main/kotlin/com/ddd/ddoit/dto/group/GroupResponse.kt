package com.ddd.ddoit.dto.group

import com.ddd.ddoit.domain.Group

data class GroupResponse(
    val id: Long,
    val name: String,
    val description: String,
){
    companion object{
        fun toEntity(group: Group?): GroupResponse? {
            return group?.let { GroupResponse(it.id!! ,it.name, group.description) }
        }
    }

}
