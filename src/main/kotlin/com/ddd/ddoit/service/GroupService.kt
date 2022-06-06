package com.ddd.ddoit.service

import com.ddd.ddoit.domain.Group
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.GroupRequest
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
        groupInfoService.joinGroupInfo(group, user)
        return group
    }

    fun findGroup(id: Long): Group {
        return groupRepository.findById(id).orElseThrow { throw BaseException(BaseErrorCodeException.INVALID_USER) }
    }

    fun joinGroup(id: Long, user: User){
        val group = groupRepository.findById(id).orElseThrow { throw BaseException(BaseErrorCodeException.INVALID_USER) }
        if(groupInfoService.isUserInGroup(user, group)) throw BaseException(BaseErrorCodeException.INVALID_USER) //TODO 그룹내에 이미 유저가 포함된 상황 에러코드 새로 발급
        else return groupInfoService.joinGroupInfo(group, user)
    }


    fun exitGroup(id: Long, user: User){
        val group = groupRepository.findById(id).orElseThrow { throw BaseException(BaseErrorCodeException.INVALID_USER) }
        if(!groupInfoService.isUserInGroup(user, group)) throw BaseException(BaseErrorCodeException.INVALID_USER) //TODO 이번에는 그룹에 유저가 없는데 탈퇴하는 상황이 생길수 없음
        else return groupInfoService.deleteGroup(group, user)

    }

    @Transactional
    fun updateGroup(groupId: Long, req: GroupRequest, user: User){
        //TODO 계정 역할 확인 필요 ADMIN CHECK
        //val info = groupInfoRepository.findByGroupIdAndUserId(groupId, user.id!!).orElseThrow{throw BaseException(BaseErrorCodeException.BAD_REQUEST)}
        val group = groupRepository.findById(groupId).orElseThrow { throw BaseException(BaseErrorCodeException.INVALID_USER) }
    }
}