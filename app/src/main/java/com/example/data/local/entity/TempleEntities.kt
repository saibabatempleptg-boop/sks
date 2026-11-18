package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "darshan_timings")
data class DarshanTimings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val labelTe: String,
    val timeRange: String,
    val description: String,
    val category: String
)

@Entity(tableName = "abhishek_schedules")
data class AbhishekSchedules(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val scheduleKey: String,
    val dayNameEn: String,
    val dayNameHi: String,
    val dayNameTe: String,
    val deity: String,
    val timings: String,
    val offerings: String,
    val description: String,
    val weekday: Int? = null,
    val isDaily: Boolean = false
)

@Entity(tableName = "homams")
data class Homams(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val nameTe: String,
    val schedule: String,
    val significance: String,
    val sankalpamDetails: String,
    val offerings: String,
    val category: String
)
