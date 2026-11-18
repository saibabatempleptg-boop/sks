package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.AbhishekSchedulesDao
import com.example.data.local.dao.DarshanTimingsDao
import com.example.data.local.dao.HomamsDao
import com.example.data.local.entity.AbhishekSchedules
import com.example.data.local.entity.DarshanTimings
import com.example.data.local.entity.Homams

@Database(
    entities = [
        DarshanTimings::class,
        AbhishekSchedules::class,
        Homams::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TempleDatabase : RoomDatabase() {
    abstract fun darshanTimingsDao(): DarshanTimingsDao
    abstract fun abhishekSchedulesDao(): AbhishekSchedulesDao
    abstract fun homamsDao(): HomamsDao

    companion object {
        @Volatile
        private var INSTANCE: TempleDatabase? = null

        fun getInstance(context: Context): TempleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TempleDatabase::class.java,
                    "mandhiram_activity_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
