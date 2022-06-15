package com.ddd.ddoit.domain

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

//그룹
@Entity
@DynamicUpdate
@Table(name = "Groups")
class Group(
    val name: String,
    val description: String,
    val notice: String,
    val size: Long,
)
{
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "group")
    val groupInfo: MutableList<GroupInfo> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "group")
    val attendanceEvent: MutableList<AttendanceEvent> = mutableListOf()

    fun makeGroup(info: GroupInfo) {
        groupInfo.add(info)
        info.group = this
    }

    fun deleteInfo(info: GroupInfo) {
        groupInfo.remove(info)
        info.group = null
    }

    fun makeAttendanceEvent(event: AttendanceEvent) {
        event.group = this
        attendanceEvent.add(event)
    }


}