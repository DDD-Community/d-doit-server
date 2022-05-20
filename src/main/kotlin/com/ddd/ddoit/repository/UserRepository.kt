package com.ddd.ddoit.repository

import com.ddd.ddoit.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email : String) : User?
}