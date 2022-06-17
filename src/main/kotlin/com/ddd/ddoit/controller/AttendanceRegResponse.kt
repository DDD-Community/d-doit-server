package com.ddd.ddoit.controller

import com.ddd.ddoit.domain.Attendance
import java.time.LocalDateTime

data class AttendanceRegResponse(
    val attendanceTime: LocalDateTime,
    val status: String
) {

    companion object{
        fun toDto(attendance: Attendance): AttendanceRegResponse {
            return AttendanceRegResponse(attendance.time, attendance.status)
        }
    }

}
