package com.ddd.ddoit.service

import com.ddd.ddoit.domain.Group
import com.ddd.ddoit.domain.GroupInfo
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.exception.BaseErrorCodeException
import com.ddd.ddoit.exception.BaseException
import com.ddd.ddoit.repository.GroupInfoRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class GroupInfoService(val groupInfoRepository: GroupInfoRepository) {

    @Transactional
    fun joinGroupInfo(group: Group, user: User){
        val info = groupInfoRepository.save(GroupInfo(user, group))
        group.makeGroup(info); //메소드명 수정 필요.
        user.addGroupInfo(info);
    }

    fun isUserInGroup(user: User, group: Group): Boolean {
        return groupInfoRepository.findByGroupIdAndUserId(groupId = group.id!!, userId = user.id!!).isPresent
    }

    @Transactional
    fun deleteGroup(group: Group, user: User) {
        val info = groupInfoRepository.findByGroupIdAndUserId(groupId = group.id!!, userId = user.id!!).orElseThrow{ throw BaseException(BaseErrorCodeException.BAD_REQUEST)}
        group.deleteInfo(info)
        user.removeGroupInfo(info)
        groupInfoRepository.delete(info)
    }
}