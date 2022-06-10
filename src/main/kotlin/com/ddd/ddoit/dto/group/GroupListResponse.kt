package com.ddd.ddoit.dto.group

import com.ddd.ddoit.domain.Group
import com.ddd.ddoit.domain.GroupInfo
import com.ddd.ddoit.dto.GroupRoleType

data class GroupListResponse(
    val id: Long,
    val name: String,
    val description: String,
    val roles: GroupRoleType?
){
    companion object{
        fun toEntityList(group: Group?, info: GroupInfo): GroupListResponse? {
            return group?.let { GroupListResponse(it.id!! ,it.name, group.description, GroupRoleType.valueIdOf(info.groupRolesId!!)) }
        }
    }

}
