package com.ddd.ddoit.repository

import com.ddd.ddoit.domain.Attendance
import org.springframework.data.jpa.repository.JpaRepository

interface AttendanceRepository: JpaRepository<Attendance, Long> {
}