package com.ddd.ddoit.dto.attendance

import com.ddd.ddoit.domain.AttendanceEvent
import java.time.LocalDateTime

data class AttendanceResponse(
    val status: Boolean,
    val endDateTime: LocalDateTime?,
    val certification: String?
)
{

    companion object {

        fun toEntity(event: AttendanceEvent?): AttendanceResponse {
            return event?.let { AttendanceResponse(true, it.endDateTime, it.certification) } ?: AttendanceResponse(false, null, null)
        }
    }

}
