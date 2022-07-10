package com.ddd.ddoit.dto.attendance

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class AttendanceRequest(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val endDateTime: LocalDateTime
)