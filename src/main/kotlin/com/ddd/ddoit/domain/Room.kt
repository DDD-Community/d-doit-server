package com.ddd.ddoit.domain

import javax.persistence.*

@Entity
class Room(
) {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "group_id")
    val group: Group? = null

}