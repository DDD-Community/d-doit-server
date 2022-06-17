package com.ddd.ddoit.domain

import com.ddd.ddoit.dto.SocialType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
class User(
    name: String,
    email: String,
    @Enumerated(EnumType.STRING)
    var social: SocialType,
    val socialId: String
) : UserDetails
{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = name

    var email: String = email

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var groupInfo: MutableList<GroupInfo> = arrayListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var attendance: MutableList<Attendance> = arrayListOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun addGroupInfo(info: GroupInfo) {
        groupInfo.add(info)
        info.user = this
    }

    fun removeGroupInfo(info: GroupInfo) {
        groupInfo.remove(info)
        info.user = null
    }

    fun addAttendance(attendance: Attendance){
        attendance.user = this
        this.attendance.add(attendance)
    }
}