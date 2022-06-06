package com.ddd.ddoit.domain

import javax.persistence.*

@Entity
class GroupInfo(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "group_id")
    var group: Group? = null
) {

    @Id
    @Column(name = "group_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupInfo")
    val groupRole: MutableList<GroupRoles> = arrayListOf()

    fun joinGroup(group: Group?, user: User?){
        this.group = group
        this.user = user
    }

}