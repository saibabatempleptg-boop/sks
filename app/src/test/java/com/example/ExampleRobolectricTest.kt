package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.PanchangamCalculator
import com.example.data.SevaKendramRepository
import com.example.data.TempleRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Sri Krishna Sai Mandhiram", appName)
    }

    @Test
    fun `verify temple repository data`() {
        assertEquals("Sri Krishna Sai Dhyana Mandhiram", TempleRepository.TEMPLE_NAME)
        assertEquals(3, TempleRepository.darshanTimings.size)
        assertEquals(16, TempleRepository.galleryPhotos.size)
        assertTrue(TempleRepository.abhishekDays.isNotEmpty())
        assertTrue(TempleRepository.saiPromises.size == 11)

        val status = TempleRepository.getCurrentStatus()
        assertNotNull(status.second)
        assertNotNull(status.third)

        // Verify devotee amenities and capacity
        assertEquals(5, TempleRepository.devoteeAmenities.size)
        assertTrue(TempleRepository.CAPACITY_DESCRIPTION.contains("400 to 500"))
        assertTrue(TempleRepository.devoteeAmenities.any { it.id == "sitting" })
        assertTrue(TempleRepository.devoteeAmenities.any { it.id == "sleeping" })
        assertTrue(TempleRepository.devoteeAmenities.any { it.id == "bathroom" })
        assertTrue(TempleRepository.devoteeAmenities.any { it.id == "meditation_breeze" })

        // Verify Temple Updates list
        assertEquals(6, TempleRepository.updates.size)
        assertTrue(TempleRepository.updates.any { it.id == "morning_darshan" })
        assertTrue(TempleRepository.updates.any { it.id == "pilgrim_capacity" })
        assertTrue(TempleRepository.updates.any { it.isImportant })
    }

    @Test
    fun `verify panchangam calculation`() {
        val cal = Calendar.getInstance()
        cal.set(2026, Calendar.OCTOBER, 9) // 2026-10-09 (Friday)

        val panchangam = PanchangamCalculator.calculatePanchangam(cal)

        assertNotNull(panchangam)
        assertTrue(panchangam.tithi.isNotBlank())
        assertTrue(panchangam.tithiTe.isNotBlank())
        assertTrue(panchangam.nakshatra.isNotBlank())
        assertTrue(panchangam.nakshatraTe.isNotBlank())
        assertTrue(panchangam.yoga.isNotBlank())
        assertTrue(panchangam.karana.isNotBlank())
        assertTrue(panchangam.rahuKalam.isNotBlank())
        assertTrue(panchangam.abhijitMuhurtham.isNotBlank())
        assertTrue(panchangam.sunrise.isNotBlank())
        assertTrue(panchangam.sunset.isNotBlank())
        assertTrue(panchangam.recommendedSeva.isNotBlank())

        assertEquals("Friday", panchangam.dayOfWeekEn)
        assertEquals("10:30 AM – 12:00 PM", panchangam.rahuKalam)
    }

    @Test
    fun `verify sri krishna sai seva kendram offerings`() {
        assertEquals("Sri Krishna Sai Seva Kendram", SevaKendramRepository.KENDRAM_TITLE)
        val sevas = SevaKendramRepository.sevasList
        assertEquals(12, sevas.size)

        // Verify requested specific Seva types
        assertTrue(sevas.any { it.id == "gotra_puja" })
        assertTrue(sevas.any { it.id == "daily_midday_prasadam" })
        assertTrue(sevas.any { it.id == "midday_nivedan" })
        assertTrue(sevas.any { it.id == "morning_prasadam" })
        assertTrue(sevas.any { it.id == "santra_prasadam" })
        assertTrue(sevas.any { it.id == "daily_puja" })
        assertTrue(sevas.any { it.id == "wednesday_puja" })
        assertTrue(sevas.any { it.id == "thursday_puja" })
        assertTrue(sevas.any { it.id == "saturday_puja" })
        assertTrue(sevas.any { it.id == "full_moon_puja" })
        assertTrue(sevas.any { it.id == "monthly_puja" })
        assertTrue(sevas.any { it.id == "special_puja" })

        // Verify WhatsApp reservation format
        val gotraSeva = sevas.first { it.id == "gotra_puja" }
        val msg = SevaKendramRepository.formatWhatsAppReservationMessage(
            devoteeName = "K. Venkateswara Rao",
            phone = "+91 9876543210",
            gotram = "Kaundinya",
            nakshatram = "Uttarabhadra",
            familyMembers = "Venkateswara Rao, Lakshmi & Children",
            preferredDate = "Upcoming Thursday",
            seva = gotraSeva,
            notes = "Family welfare and peace"
        )

        assertTrue(msg.contains("Sri Krishna Sai Seva Kendram Reservation"))
        assertTrue(msg.contains("Gotra Name Puja"))
        assertTrue(msg.contains("Kaundinya"))
        assertTrue(msg.contains("Uttarabhadra"))
    }
}
