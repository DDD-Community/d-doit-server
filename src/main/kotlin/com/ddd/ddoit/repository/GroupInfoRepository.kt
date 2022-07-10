package com.ddd.ddoit.repository

import com.ddd.ddoit.domain.GroupInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GroupInfoRepository: JpaRepository<GroupInfo, Long> {

    fun findByGroupIdAndUserId(groupId: Long, userId: Long): Optional<GroupInfo>
    fun findAllByUserId(id: Long?): List<GroupInfo>
}