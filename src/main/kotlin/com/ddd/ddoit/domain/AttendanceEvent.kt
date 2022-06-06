package com.ddd.ddoit.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AttendanceEvent(
    val startDate: LocalDateTime,
    val certification: String,
    val duration: Long,
) {

    @Id
    @Column(name = "attendance_event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    val group: Group? = null

    @ManyToOne(fetch = FetchType.LAZY)
    val attendance: Attendance? = null

}