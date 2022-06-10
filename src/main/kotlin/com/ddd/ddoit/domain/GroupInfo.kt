package com.ddd.ddoit.domain

import com.ddd.ddoit.dto.GroupRoleType
import javax.persistence.*

@Entity
class GroupInfo(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "group_id")
    var group: Group? = null,

    @JoinColumn(table = "group_roles", referencedColumnName = "id")
    var groupRolesId: Long? = null
) {

    @Id
    @Column(name = "group_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun joinGroup(group: Group?, user: User?, roles: GroupRoleType){
        this.group = group
        this.user = user
        this.groupRolesId = roles.id
    }

}