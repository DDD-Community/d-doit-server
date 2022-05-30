package com.ddd.ddoit.domain

import javax.persistence.*

//그룹
@Entity
@Table(name = "Groups")
class Group(
    val name: String,
    val notice: String,
    val size: Long,
)
{
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "group")
    private val room: List<Room> = arrayListOf()


}