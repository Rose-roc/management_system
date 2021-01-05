package com.example.management_system.data

data class Pay(
    val date: String,
    val ownerId: Int,
    val payId: Int,
    val payStatus: Int,
    val paymentItem: String,
    val totalCost: Int,
    val usageAmount: Int,

    val ownerName: String
)