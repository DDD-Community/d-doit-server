package com.ddd.ddoit.repository

import com.ddd.ddoit.domain.GroupInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupInfoRepository: JpaRepository<GroupInfo, Long> {
}