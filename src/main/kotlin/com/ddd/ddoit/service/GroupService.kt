package com.ddd.ddoit.service

import com.ddd.ddoit.domain.Group
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.GroupDetailResponse
import com.ddd.ddoit.dto.group.GroupRequest
import com.ddd.ddoit.dto.group.GroupListResponse
import com.ddd.ddoit.dto.GroupRoleType
import com.ddd.ddoit.dto.group.GroupUpdateRequest
import com.ddd.ddoit.exception.BaseErrorCodeException
import com.ddd.ddoit.exception.BaseException
import com.ddd.ddoit.repository.GroupRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class GroupService(val groupRepository: GroupRepository, val groupInfoService: GroupInfoService) {

    @Transactional
    fun saveGroup(groupRequest: GroupRequest, user: User): Group {
        val group = groupRepository.save(groupRequest.toEntity())
        groupInfoService.joinGroupInfo(group, user, GroupRoleType.ADMIN)
        return group
    }

    fun findGroup(id: Long): Group {
        return groupRepository.findById(id).orElseThrow { throw BaseException(BaseErrorCodeException.GROUP_NOT_FOUND) }
    }

    fun joinGroup(id: Long, user: User){
        val group = groupRepository.findById(id).orElseThrow { throw BaseException(BaseErrorCodeException.GROUP_NOT_FOUND) }
        if(groupInfoService.isUserInGroup(user, group)) throw BaseException(BaseErrorCodeException.USER_IN_GROUP)
        else return groupInfoService.joinGroupInfo(group, user, GroupRoleType.USER)
    }


    fun exitGroup(id: Long, user: User){
        val group = groupRepository.findById(id).orElseThrow { throw BaseException(BaseErrorCodeException.GROUP_NOT_FOUND) }
        if(!groupInfoService.isUserInGroup(user, group)) throw BaseException(BaseErrorCodeException.GROUP_IN_NOT_USER)
        else return groupInfoService.deleteGroup(group, user)

    }

    @Transactional
    fun updateGroupNotice(groupId: Long, req: GroupUpdateRequest, user: User){
        val group = groupRepository.findById(groupId)
            .orElseThrow { throw BaseException(BaseErrorCodeException.GROUP_NOT_FOUND) }
        val info = group.groupInfo.first { it.user == user }
        if ( GroupRoleType.valueIdOf(info.groupRolesId!!)!= GroupRoleType.ADMIN)
            throw BaseException(BaseErrorCodeException.NOT_ADMIN)
        req.notice?.let { group.updateNotice(it) }
    }

    @Transactional
    fun updateGroupDescription(groupId: Long, req: GroupUpdateRequest, user: User){
        val group = groupRepository.findById(groupId)
            .orElseThrow { throw BaseException(BaseErrorCodeException.GROUP_NOT_FOUND) }
        val info = group.groupInfo.first { it.user == user }
        if ( GroupRoleType.valueIdOf(info.groupRolesId!!)!= GroupRoleType.ADMIN)
            throw BaseException(BaseErrorCodeException.NOT_ADMIN)
        req.description?.let { group.updateDescription(it) }
    }

    fun listUserGroup(user: User): List<GroupListResponse?> {
        val groups = groupInfoService.findGroups(user)
        return groups.map { info -> GroupListResponse.toEntityList(info.group, info) }.toList()
    }

    fun findGroupDetail(id: Long, user: User): GroupDetailResponse? {
        val group = groupRepository.findById(id)
            .orElseThrow { throw BaseException(BaseErrorCodeException.GROUP_NOT_FOUND) }
        val info = group.groupInfo.first { it.user == user }
        val role = GroupRoleType.valueIdOf(info.groupRolesId!!)
        return role?.let { GroupDetailResponse.toDto(group, it) }
    }
}