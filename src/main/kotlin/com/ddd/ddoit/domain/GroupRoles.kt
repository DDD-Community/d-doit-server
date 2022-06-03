package com.ddd.ddoit.domain

import javax.persistence.*

@Entity
class GroupRoles(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,

    val code: String,

    val name: String,
){

    @ManyToOne(fetch = FetchType.LAZY, cascade = [])
    val groupInfo: GroupInfo? = null
}