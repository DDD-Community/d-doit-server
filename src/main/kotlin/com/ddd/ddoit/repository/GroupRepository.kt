package com.ddd.ddoit.repository

import com.ddd.ddoit.domain.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long>{
}