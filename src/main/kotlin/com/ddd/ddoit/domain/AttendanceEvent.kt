package com.ddd.ddoit.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class AttendanceEvent(
    val startDateTime: LocalDateTime,
    val certification: String,
    val endDateTime: LocalDateTime,
) {

    @Id
    @Column(name = "attendance_event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var group: Group? = null

    fun addGroup(group: Group) {
        this.group = group
        group.attendanceEvent.add(this)
    }
}