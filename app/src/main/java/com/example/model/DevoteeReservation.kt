package com.example.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class DevoteeReservation(
    @DocumentId val id: String = "",
    val userId: String = "",
    val sevaId: String = "",
    val sevaTitle: String = "",
    val devoteeName: String = "",
    val devoteePhone: String = "",
    val gotram: String = "",
    val nakshatram: String = "",
    val preferredDate: String = "",
    val specialPrayer: String = "",
    val status: String = "Confirmed",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class DevoteePrayer(
    @DocumentId val id: String = "",
    val userId: String = "",
    val devoteeName: String = "",
    val prayerText: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)
