package com.ddd.ddoit.domain

import javax.persistence.*

@Entity
class GroupRoles(
    @Id
    val id : Long,

    val code: String,

    val name: String,
)