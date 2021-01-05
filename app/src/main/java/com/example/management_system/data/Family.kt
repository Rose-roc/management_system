package com.example.management_system.data

data class Family(
    val familyId: Int,
    val leader: String,
    val memberShip: Int,
    val members: List<Member>
)

