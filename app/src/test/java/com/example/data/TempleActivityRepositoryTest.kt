package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.TempleActivityRepository
import com.example.data.local.TempleDatabase
import com.example.data.local.entity.AbhishekSchedules
import com.example.data.local.entity.DarshanTimings
import com.example.data.local.entity.Homams
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class TempleActivityRepositoryTest {

    private lateinit var database: TempleDatabase
    private lateinit var repository: TempleActivityRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, TempleDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = TempleActivityRepository(database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun seedInitialDataIfEmpty_populatesAllThreeEntitiesIdempotently() = runBlocking {
        repository.seedInitialDataIfEmpty()
        repository.seedInitialDataIfEmpty() // Idempotency check

        val timings = repository.darshanTimings.first()
        val abhisheks = repository.abhishekSchedules.first()
        val homams = repository.homams.first()

        assertEquals(4, timings.size)
        assertTrue(timings.any { it.label == "Morning darshan" && it.timeRange == "5:15 am – 12:30 pm" })
        assertTrue(timings.any { it.label == "Evening darshan" && it.timeRange == "5:00 pm – 8:30 pm" })

        assertEquals(5, abhisheks.size)
        assertTrue(abhisheks.any { it.scheduleKey == "thursday" && it.deity.contains("Dattatreya") })
        assertTrue(abhisheks.any { it.scheduleKey == "daily" && it.isDaily })

        assertEquals(4, homams.size)
        assertTrue(homams.any { it.name.contains("Lakshmi Ganapathi Homam") })
    }

    @Test
    fun insertAndFilterQueries_workAccuratelyForAllEntities() = runBlocking {
        val customTiming = DarshanTimings(
            label = "Festival Special Darshan",
            labelTe = "పండుగ ప్రత్యేక దర్శనం",
            timeRange = "4:30 am – 10:00 pm",
            description = "Extended festival hours on Gurupurnima",
            category = "Festival"
        )
        repository.insertDarshanTiming(customTiming)

        val festivalTimings = repository.getDarshanTimingsByCategory("Festival").first()
        assertEquals(1, festivalTimings.size)
        assertEquals("Festival Special Darshan", festivalTimings[0].label)

        val customAbhishek = AbhishekSchedules(
            scheduleKey = "ekadashi",
            dayNameEn = "Ekadashi Special",
            dayNameHi = "एकादशी",
            dayNameTe = "ఏకాదశి విశేష అభిషేకం",
            deity = "Sri Krishna Bhagwan",
            timings = "6:00 AM",
            offerings = "Panchamrit, Tulsi",
            description = "Monthly Ekadashi special worship"
        )
        repository.insertAbhishekSchedule(customAbhishek)

        val ekadashiList = repository.getAbhishekSchedulesByKey("ekadashi").first()
        assertEquals(1, ekadashiList.size)
        assertEquals("Sri Krishna Bhagwan", ekadashiList[0].deity)

        val customHomam = Homams(
            name = "Sudarshana Homam",
            nameTe = "సుదర్శన హోమం",
            schedule = "Special Sankalpam",
            significance = "Protection and spiritual strength",
            sankalpamDetails = "Performed with Gotra Nama",
            offerings = "Ghee, Tulsi, Herbs",
            category = "Protection"
        )
        repository.insertHomam(customHomam)

        val protectionHomams = repository.getHomamsByCategory("Protection").first()
        assertEquals(1, protectionHomams.size)
        assertEquals("Sudarshana Homam", protectionHomams[0].name)

        // Verify delete
        repository.deleteHomamById(protectionHomams[0].id)
        assertTrue(repository.getHomamsByCategory("Protection").first().isEmpty())
    }
}
