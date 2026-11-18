package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.AbhishekSchedules
import com.example.data.local.entity.DarshanTimings
import com.example.data.local.entity.Homams
import kotlinx.coroutines.flow.Flow

@Dao
interface DarshanTimingsDao {
    @Query("SELECT * FROM darshan_timings ORDER BY id ASC")
    fun getAllDarshanTimings(): Flow<List<DarshanTimings>>

    @Query("SELECT * FROM darshan_timings WHERE category = :category ORDER BY id ASC")
    fun getDarshanTimingsByCategory(category: String): Flow<List<DarshanTimings>>

    @Query("SELECT COUNT(*) FROM darshan_timings")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(timings: List<DarshanTimings>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timing: DarshanTimings)

    @Query("DELETE FROM darshan_timings WHERE id = :id")
    suspend fun deleteById(id: Int)
}

@Dao
interface AbhishekSchedulesDao {
    @Query("SELECT * FROM abhishek_schedules ORDER BY id ASC")
    fun getAllAbhishekSchedules(): Flow<List<AbhishekSchedules>>

    @Query("SELECT * FROM abhishek_schedules WHERE scheduleKey = :scheduleKey ORDER BY id ASC")
    fun getSchedulesByKey(scheduleKey: String): Flow<List<AbhishekSchedules>>

    @Query("SELECT COUNT(*) FROM abhishek_schedules")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schedules: List<AbhishekSchedules>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: AbhishekSchedules)

    @Query("DELETE FROM abhishek_schedules WHERE id = :id")
    suspend fun deleteById(id: Int)
}

@Dao
interface HomamsDao {
    @Query("SELECT * FROM homams ORDER BY id ASC")
    fun getAllHomams(): Flow<List<Homams>>

    @Query("SELECT * FROM homams WHERE category = :category ORDER BY id ASC")
    fun getHomamsByCategory(category: String): Flow<List<Homams>>

    @Query("SELECT COUNT(*) FROM homams")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(homams: List<Homams>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(homam: Homams)

    @Query("DELETE FROM homams WHERE id = :id")
    suspend fun deleteById(id: Int)
}
