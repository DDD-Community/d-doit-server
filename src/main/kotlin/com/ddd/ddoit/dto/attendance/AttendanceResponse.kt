package com.ddd.ddoit.dto.attendance

import com.ddd.ddoit.domain.AttendanceEvent
import java.time.LocalDateTime

data class AttendanceResponse(
    val endDateTime: LocalDateTime,
    val certification: String
)
{
    companion object {

        fun toEntity(event: AttendanceEvent): AttendanceResponse {
            return AttendanceResponse(event.endDateTime, event.certification)
        }
    }

}
