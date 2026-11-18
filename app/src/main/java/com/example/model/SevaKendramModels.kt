package com.example.model

data class SevaItem(
    val id: String,
    val name: String,
    val nameTe: String,
    val category: String, // "Daily Puja", "Wednesday Puja", "Thursday Puja", "Saturday Puja", "Full Moon Puja", "Monthly Puja", "Special Puja", "Prasadam & Nivedan"
    val timing: String,
    val description: String,
    val descriptionTe: String,
    val offerings: List<String>,
    val icon: String,
    val isPrasadam: Boolean = false
)

data class SevaReservationFormState(
    val devoteeName: String = "",
    val phone: String = "",
    val gotram: String = "",
    val nakshatram: String = "",
    val familyMembers: String = "",
    val preferredDate: String = "",
    val selectedSevaId: String = "",
    val sankalpamNotes: String = ""
)
