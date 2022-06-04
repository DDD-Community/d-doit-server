package com.ddd.ddoit.service

import com.ddd.ddoit.domain.Group
import com.ddd.ddoit.domain.GroupInfo
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.GroupRequest
import com.ddd.ddoit.exception.BaseErrorCodeException
import com.ddd.ddoit.exception.BaseException
import com.ddd.ddoit.repository.GroupInfoRepository
import com.ddd.ddoit.repository.GroupRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class GroupService(val groupRepository: GroupRepository, val groupInfoRepository: GroupInfoRepository) {

    @Transactional
    fun saveGroup(groupRequest: GroupRequest, user: User): Group {
        val group = groupRepository.save(groupRequest.toEntity())
        val info = groupInfoRepository.save(GroupInfo(user, group))
        group.makeGroup(info); //메소드명 수정 필요.
        user.addGroupInfo(info);
        return group
    }

    fun findGroup(id: Long): Group {
        return groupRepository.findById(id).orElseThrow { throw BaseException(BaseErrorCodeException.INVALID_USER) }
    }

    fun enterGroup(groupId: Long, user: User){

    }

    fun exitGroup(groupId: Long, user: User){

    }

    @Transactional
    fun updateGroup(groupId: Long, req: GroupRequest, user: User){
        //TODO 계정 역할 확인 필요 ADMIN CHECK
        //val info = groupInfoRepository.findByGroupIdAndUserId(groupId, user.id!!).orElseThrow{throw BaseException(BaseErrorCodeException.BAD_REQUEST)}
        val group = groupRepository.findById(groupId).orElseThrow { throw BaseException(BaseErrorCodeException.INVALID_USER) }
    }
}