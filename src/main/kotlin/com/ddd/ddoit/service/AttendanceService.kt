package com.ddd.ddoit.service

import com.ddd.ddoit.domain.AttendanceEvent
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.attendance.AttendanceRequest
import com.ddd.ddoit.exception.BaseErrorCodeException
import com.ddd.ddoit.exception.BaseException
import com.ddd.ddoit.repository.AttendanceEventRepository
import com.ddd.ddoit.repository.AttendanceRepository
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.LocalDateTime

@Service
class AttendanceService(val attendanceEventRepository: AttendanceEventRepository,
                        val attendanceRepository: AttendanceRepository)
{
    fun triggerEvent(req: AttendanceRequest, user: User, groupId: Long): AttendanceEvent {

        val random = SecureRandom()
        //그룹 찾고
        val attendanceEvent = attendanceEventRepository.save(
            AttendanceEvent(
                LocalDateTime.now(),
                random.nextInt(9999).toString(),
                req.endDateTime
            )
        )
        //그룹과 연관관계 맺기 (쌍방)
        return attendanceEvent
    }

    fun findCurrentEvent(groupId: Long): AttendanceEvent{
        return attendanceEventRepository.findByGroupIdAndEndDateTimeAfter(groupId, LocalDateTime.now())
            .orElseThrow { throw BaseException(BaseErrorCodeException.BAD_REQUEST) }
    }
}