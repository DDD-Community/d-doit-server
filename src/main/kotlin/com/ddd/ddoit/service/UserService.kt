package com.ddd.ddoit.service

import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.AuthRequest
import com.ddd.ddoit.dto.SocialType
import com.ddd.ddoit.exception.BaseErrorCodeException
import com.ddd.ddoit.exception.BaseException
import com.ddd.ddoit.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(var userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(token: String): UserDetails {
        val array = token.split("_")
        return userRepository.findBySocialIdAndSocial(array[1], SocialType.KAKAO)?: throw BaseException(BaseErrorCodeException.USER_NOT_FOUND)
    }

    fun signupUser(req: AuthRequest): Long? {
       return userRepository.save(User(name = req.name, email = req.email, social = SocialType.KAKAO, socialId = req.socialId)).id
    }

    fun login(req: AuthRequest): User {
        return userRepository.findBySocialIdAndSocial(req.socialId, SocialType.KAKAO)?: throw BaseException(BaseErrorCodeException.INVALID_USER)
    }

    fun isDuplicateUsername(name: String): Boolean{
        return userRepository.findByName(name).isPresent
    }
}