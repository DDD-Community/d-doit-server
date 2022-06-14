package com.ddd.ddoit.repository

import com.ddd.ddoit.domain.AttendanceEvent
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface AttendanceEventRepository: JpaRepository<AttendanceEvent, Long> {

    fun findByGroupIdAndEndDateTimeAfter(groupId: Long, endDateTime: LocalDateTime): Optional<AttendanceEvent>

}