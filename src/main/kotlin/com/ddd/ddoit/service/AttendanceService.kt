package com.ddd.ddoit.service

import com.ddd.ddoit.domain.Attendance
import com.ddd.ddoit.domain.AttendanceEvent
import com.ddd.ddoit.domain.Group
import com.ddd.ddoit.domain.User
import com.ddd.ddoit.dto.GroupRoleType
import com.ddd.ddoit.dto.attendance.AttendanceRequest
import com.ddd.ddoit.exception.BaseErrorCodeException
import com.ddd.ddoit.exception.BaseException
import com.ddd.ddoit.repository.AttendanceEventRepository
import com.ddd.ddoit.repository.AttendanceRepository
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class AttendanceService(val attendanceEventRepository: AttendanceEventRepository,
                        val attendanceRepository: AttendanceRepository)
{

    @Transactional
    fun triggerEvent(req: AttendanceRequest, user: User, group: Group): AttendanceEvent {

        val userInfo = group.groupInfo.filter { info -> info.user == user }.toList()
        if(userInfo.isEmpty())
            throw BaseException(BaseErrorCodeException.GROUP_IN_NOT_USER)
        if(userInfo.last().groupRolesId?.let { GroupRoleType.valueIdOf(it) } == GroupRoleType.USER)
            throw BaseException(BaseErrorCodeException.NOT_ADMIN)

        val attendanceEvent = attendanceEventRepository.save(
            AttendanceEvent(
                LocalDateTime.now(),
                SecureRandom().nextInt(9999).toString(),
                req.endDateTime
            )
        )

        group.makeAttendanceEvent(attendanceEvent)
        attendanceEvent.addGroup(group)
        return attendanceEvent
    }

    fun findCurrentEvent(groupId: Long): AttendanceEvent{
        return attendanceEventRepository.findByGroupIdAndEndDateTimeAfter(groupId, LocalDateTime.now())
            .orElseThrow { throw BaseException(BaseErrorCodeException.NOT_CURRENT_ATTENDANCE) }
    }

    @Transactional
    fun registerAttendance(id: Long, user: User, request : AttendanceRegisterResquest): Attendance {
        val event = attendanceEventRepository.findById(id)
            .orElseThrow { throw BaseException(BaseErrorCodeException.NOT_ATTENDANCE_EVENT) }

        if(event.certification != request.certification)
            throw  BaseException(BaseErrorCodeException.NOT_CERTIFICATION)

        val attendace = attendanceRepository.save(Attendance("CHECK", LocalDateTime.now()))
        attendace.addUserAndEvent(user,id)
        user.addAttendance(attendace)
        return  attendace

    }

    data class AttendanceRegisterResquest(
        val certification: String,
    )
}