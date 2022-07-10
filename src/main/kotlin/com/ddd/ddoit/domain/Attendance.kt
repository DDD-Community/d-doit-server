package com.ddd.ddoit.domain

import java.time.LocalDateTime
import javax.persistence.*

//출석
@Entity
class Attendance(
    val status: String,
    val time: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @Column(name = "attendance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @JoinColumn(table = "attendance_event", referencedColumnName = "id")
    var attendanceEventId: Long? = null

    fun addUserAndEvent(user: User, eventId: Long) {
        attendanceEventId = eventId
        this.user = user
        user.attendance.add(this)
    }

}