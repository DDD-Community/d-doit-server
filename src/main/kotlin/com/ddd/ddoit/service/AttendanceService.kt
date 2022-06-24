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
                        val attendanceRepository: AttendanceRepository,
                        val groupService: GroupService,
                        )
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

    data class AttendanceRegisterRequest(
        val certification: String,
    )

    @Transactional
    fun registerAttendance(id: Long, user: User, request : AttendanceRegisterRequest): Attendance {
        val event = attendanceEventRepository.findById(id)
            .orElseThrow { throw BaseException(BaseErrorCodeException.NOT_ATTENDANCE_EVENT) }

        if(event.certification != request.certification)
            throw  BaseException(BaseErrorCodeException.NOT_CERTIFICATION)

        val attendace = attendanceRepository.save(Attendance("CHECK", LocalDateTime.now()))
        attendace.addUserAndEvent(user,id)
        user.addAttendance(attendace)
        return  attendace

    }

    //쿼리 자체는 괜찮은데... 방식이 비효율적, 전체 출석 다 가져올 필요가 없음
    fun findUserAttendanceInGroup(user: User, id: Long): MutableMap<String, Int> {
        val group = groupService.findGroup(id)
        //그룹이 가지고있는 모든 출석 이벤트 다가져오기.
        val list = group.attendanceEvent.map { event -> event.id }.toList()
        //유저가 가지고 있는 출석 이벤트랑, 그에 맞는 status 계산하기
        val map = mutableMapOf(Pair("출석", 0), Pair("결석", list.size))
        user.attendance.filter { list.contains(it.attendanceEventId) }.forEach {
            if(it.status=="CHECK"){
                map["출석"] = map["출석"]?.plus(1) ?: 0
                map["결석"] = map["결석"]?.minus(1) ?: 0
            }
        }
        return map
    }
}