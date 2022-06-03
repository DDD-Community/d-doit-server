package com.ddd.ddoit.domain

import java.time.LocalDateTime
import javax.persistence.*

//출석
@Entity
class Attendance(
    val status: String,
) {
    @Id
    @Column(name = "attendance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null

    @OneToMany(mappedBy = "attendance", fetch = FetchType.LAZY)
    val attendanceEvent: MutableList<AttendanceEvent> = arrayListOf()


}