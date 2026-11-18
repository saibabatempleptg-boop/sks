package com.example.model

import androidx.annotation.DrawableRes

data class DarshanTiming(
    val label: String,
    val time: String,
    val description: String = ""
)

data class MandiramUpdate(
    val id: String,
    val title: String,
    val titleTe: String,
    val text: String,
    val textTe: String,
    val category: String,
    val dateDisplay: String,
    val isImportant: Boolean = false,
    val icon: String = "📢",
    val actionText: String? = null
)

data class SevaFacility(
    val title: String,
    val text: String,
    val iconName: String,
    @DrawableRes val imageRes: Int? = null
)

data class AbhishekEntry(
    val deity: String,
    @DrawableRes val imageRes: Int,
    val altText: String,
    val text: String,
    val offerings: List<String>
)

data class AbhishekDay(
    val id: String,
    val dayNameHi: String,
    val dayNameEn: String,
    val dayNameTe: String,
    val icon: String,
    val weekday: Int?, // Calendar.WEDNESDAY = 4, etc. null if special like purnima or daily
    val isDaily: Boolean = false,
    val note: String? = null,
    val entries: List<AbhishekEntry>
)

data class GalleryPhoto(
    val id: String,
    @DrawableRes val resId: Int,
    val title: String,
    val description: String,
    val category: String // "Shrine", "Rituals", "Mandiram", "Deities"
)

data class FaqItem(
    val id: String,
    val question: String,
    val questionTe: String,
    val answer: String,
    val answerTe: String,
    val category: String
)

data class SaiPromise(
    val number: Int,
    val english: String,
    val telugu: String
)

data class DevoteeAmenity(
    val id: String,
    val icon: String,
    val title: String,
    val titleTe: String,
    val description: String,
    val tag: String
)
