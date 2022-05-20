package com.ddd.ddoit.repository

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.SocialType
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email : String) : User?

    fun findBySocialIdAndSocial(socialId: String, socialType: SocialType): User?
}