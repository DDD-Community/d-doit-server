package com.ddd.ddoit.service

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.AuthRequest
import com.ddd.ddoit.dto.SocialType
import com.ddd.ddoit.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(var userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username)?: throw IllegalAccessException("없서용~~")
    }

    fun signupUser(req: AuthRequest): Long? {
       return userRepository.save(User(name = req.name, email = req.email, social = SocialType.KAKAO.social)).id
    }

    fun login(req: AuthRequest): User {
        return userRepository.findByEmail(req.email)?: throw IllegalAccessException("없슴니당~~")
    }
}