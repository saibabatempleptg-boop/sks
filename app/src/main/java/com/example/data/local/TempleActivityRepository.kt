package com.example.data.local

import android.content.Context
import com.example.data.local.dao.AbhishekSchedulesDao
import com.example.data.local.dao.DarshanTimingsDao
import com.example.data.local.dao.HomamsDao
import com.example.data.local.entity.AbhishekSchedules
import com.example.data.local.entity.DarshanTimings
import com.example.data.local.entity.Homams
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class TempleActivityRepository(
    private val darshanTimingsDao: DarshanTimingsDao,
    private val abhishekSchedulesDao: AbhishekSchedulesDao,
    private val homamsDao: HomamsDao
) {

    constructor(database: TempleDatabase) : this(
        darshanTimingsDao = database.darshanTimingsDao(),
        abhishekSchedulesDao = database.abhishekSchedulesDao(),
        homamsDao = database.homamsDao()
    )

    constructor(context: Context) : this(TempleDatabase.getInstance(context))

    val darshanTimings: Flow<List<DarshanTimings>> = darshanTimingsDao.getAllDarshanTimings()

    val abhishekSchedules: Flow<List<AbhishekSchedules>> = abhishekSchedulesDao.getAllAbhishekSchedules()

    val homams: Flow<List<Homams>> = homamsDao.getAllHomams()

    fun getDarshanTimingsByCategory(category: String): Flow<List<DarshanTimings>> =
        darshanTimingsDao.getDarshanTimingsByCategory(category)

    fun getAbhishekSchedulesByKey(scheduleKey: String): Flow<List<AbhishekSchedules>> =
        abhishekSchedulesDao.getSchedulesByKey(scheduleKey)

    fun getHomamsByCategory(category: String): Flow<List<Homams>> =
        homamsDao.getHomamsByCategory(category)

    suspend fun insertDarshanTiming(timing: DarshanTimings) {
        darshanTimingsDao.insert(timing)
    }

    suspend fun insertAbhishekSchedule(schedule: AbhishekSchedules) {
        abhishekSchedulesDao.insert(schedule)
    }

    suspend fun insertHomam(homam: Homams) {
        homamsDao.insert(homam)
    }

    suspend fun deleteDarshanTimingById(id: Int) {
        darshanTimingsDao.deleteById(id)
    }

    suspend fun deleteAbhishekScheduleById(id: Int) {
        abhishekSchedulesDao.deleteById(id)
    }

    suspend fun deleteHomamById(id: Int) {
        homamsDao.deleteById(id)
    }

    suspend fun seedInitialDataIfEmpty() {
        if (darshanTimingsDao.getCount() == 0) {
            darshanTimingsDao.insertAll(defaultDarshanTimings())
        }
        if (abhishekSchedulesDao.getCount() == 0) {
            abhishekSchedulesDao.insertAll(defaultAbhishekSchedules())
        }
        if (homamsDao.getCount() == 0) {
            homamsDao.insertAll(defaultHomams())
        }
    }

    companion object {
        fun defaultDarshanTimings(): List<DarshanTimings> = listOf(
            DarshanTimings(
                label = "Morning darshan",
                labelTe = "ఉదయపు దర్శనం",
                timeRange = "5:15 am – 12:30 pm",
                description = "Daily abhishek, archana, morning balbhog & peaceful darshan",
                category = "Daily"
            ),
            DarshanTimings(
                label = "Afternoon break",
                labelTe = "మధ్యాహ్న విశ్రాంతి",
                timeRange = "12:30 pm – 5:00 pm",
                description = "Temple closed after midday nivedan & prasadam distribution",
                category = "Break"
            ),
            DarshanTimings(
                label = "Evening darshan",
                labelTe = "సాయంత్రం దర్శనం",
                timeRange = "5:00 pm – 8:30 pm",
                description = "Evening dhoop aarti, deeparadhana & quiet dhyana",
                category = "Daily"
            ),
            DarshanTimings(
                label = "Thursday Sai Bhajan",
                labelTe = "గురువారం సాయి భజన",
                timeRange = "6:30 pm",
                description = "Special Guruvaram congregational bhajan and samuhika pooja",
                category = "Weekly"
            )
        )

        fun defaultAbhishekSchedules(): List<AbhishekSchedules> = listOf(
            AbhishekSchedules(
                scheduleKey = "daily",
                dayNameEn = "Daily abhishek",
                dayNameHi = "हर रोज़",
                dayNameTe = "ప్రతిరోజూ ఉదయం",
                deity = "Sri Shirdi Sai Baba",
                timings = "Daily • 5:15 AM",
                offerings = "Panchamrit, Bhasma, Vibhuti, New Vastram, Daily Pooja",
                description = "Daily morning abhishek of Sri Sai Baba with Panchamrit, holy Bhasma, and fragrant Vibhuti followed by fresh Vastram and Mangala Aarti.",
                weekday = null,
                isDaily = true
            ),
            AbhishekSchedules(
                scheduleKey = "wednesday",
                dayNameEn = "Every Wednesday",
                dayNameHi = "बुधवार",
                dayNameTe = "బుధవారం పూజ",
                deity = "Sri Ganpati Maharaj",
                timings = "Wednesdays • 6:00 AM",
                offerings = "Sacred Abhishek, Garika & Floral Malas, Modak Nivedan",
                description = "Special abhishek and archana offered to Lord Ganapathi for removal of obstacles, auspicious beginnings, and family peace.",
                weekday = Calendar.WEDNESDAY,
                isDaily = false
            ),
            AbhishekSchedules(
                scheduleKey = "thursday",
                dayNameEn = "Every Thursday",
                dayNameHi = "गुरुवार",
                dayNameTe = "గురువారం గురుపూజ",
                deity = "Lord Dattatreya Swami & Sri Sai Baba",
                timings = "Thursdays • 5:15 AM & 6:30 PM Bhajan",
                offerings = "Milk, Curd, Pure Ghee, Honey, Sugar, Vibhuti, Gandham, Yellow Silk Vastram",
                description = "Grand Guruvaram Abhishek on the golden throne with sacred dravyas followed by 6:30 PM Sai Bhajan and samuhika aarti.",
                weekday = Calendar.THURSDAY,
                isDaily = false
            ),
            AbhishekSchedules(
                scheduleKey = "saturday",
                dayNameEn = "Every Saturday",
                dayNameHi = "शनिवार",
                dayNameTe = "శనివారం పూజ",
                deity = "Sri Krishna Bhagwan",
                timings = "Saturdays • 6:00 AM",
                offerings = "Tulsi Malas, Fresh Butter, Panchamrit Abhishek, Krishna Archana",
                description = "Divine abhishek and archana for Lord Sri Krishna with fragrant Tulsi garlands and devotional stotrams.",
                weekday = Calendar.SATURDAY,
                isDaily = false
            ),
            AbhishekSchedules(
                scheduleKey = "purnima",
                dayNameEn = "Full moon special",
                dayNameHi = "पूर्णिमा",
                dayNameTe = "పౌర్ణమి విశేష పూజ",
                deity = "Sri Sai Baba & Satyanarayana Swami",
                timings = "Every Purnima Tithi • 6:30 AM & 6:00 PM",
                offerings = "Satyanarayana Vratham, 108 Deeparadhana, Mahaprasadam",
                description = "Special Full Moon archana, Satyanarayana Swamy Vratham, and twilight deeparadhana on every auspicious Purnima.",
                weekday = null,
                isDaily = false
            )
        )

        fun defaultHomams(): List<Homams> = listOf(
            Homams(
                name = "Sri Lakshmi Ganapathi Homam",
                nameTe = "శ్రీ లక్ష్మీ గణపతి హోమం",
                schedule = "Monthly Sankashtahara Chaturthi & Special Appointments",
                significance = "Removes obstacles, bestows prosperity, wisdom, and harmonious new beginnings for families and businesses.",
                sankalpamDetails = "Performed in devotee Gotram, Nakshatram, and family members' names at the temple Homam Kunda.",
                offerings = "Modak Ahuti, Pure Cow Ghee, Bilva & Garika, Purna Ahuti, Sacred Vibhuti",
                category = "Monthly & Special Homam"
            ),
            Homams(
                name = "Sri Sai Ayushya & Arogyaprada Homam",
                nameTe = "శ్రీ సాయి ఆయుష్య & ఆరోగ్యప్రద హోమం",
                schedule = "Birthdays, Nakshatra Days & Thursday Mornings",
                significance = "Prayers for longevity, recovery from illness, mental peace, and Baba's protective grace.",
                sankalpamDetails = "Special Charu and herbs offered into the sanctified agni reciting Ayushya Suktam and Sai Ashtottaram.",
                offerings = "Herbal Samidhas, Black Sesame, Pure Ghee, Milk Rice Charu",
                category = "Health & Longevity Homam"
            ),
            Homams(
                name = "Navagraha Shanti Homam",
                nameTe = "నవగ్రహ శాంతి హోమం",
                schedule = "Saturdays, Masik Shivaratri & By Appointment",
                significance = "Pacifies planetary doshas and brings balance, clarity, and spiritual protection to all household members.",
                sankalpamDetails = "Nine sacred samidhas and navadhanyas consecrated in the presence of Sri Sai Baba and Sri Krishna.",
                offerings = "Nine Sacred Samidhas, Navadhanya, Nine Colored Vastra, Purnahuti",
                category = "Planetary Peace Homam"
            ),
            Homams(
                name = "Chandi & Rudra Parayana Homam",
                nameTe = "రుద్ర & చండీ హోమం",
                schedule = "Mahashivaratri, Gurupurnima & Auspicious Tithis",
                significance = "Deep spiritual purification, collective temple welfare, and invoking divine strength.",
                sankalpamDetails = "Chanted by temple Vedic pandits with collective devotee participation inside the peaceful mandapam.",
                offerings = "Bilva Leaves, Lotus Flowers, Pure Ghee, Dry Fruits, Vastra Ahuti",
                category = "Festival Maha Homam"
            )
        )
    }
}
