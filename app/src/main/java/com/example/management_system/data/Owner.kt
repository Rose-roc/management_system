package com.example.management_system.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val familyId: Int,
    val houseId: Int,
    val name: String,
    val ownerId: Int,
    var phone: String
): Parcelable
