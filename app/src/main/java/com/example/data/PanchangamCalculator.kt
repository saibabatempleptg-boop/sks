package com.example.data

import com.example.model.PanchangamInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object PanchangamCalculator {

    private val tithiNamesEn = listOf(
        "Prathama (Padyami)", "Dwitiya (Vidiya)", "Tritiya (Tadiya)", "Chaturthi (Chavithi)",
        "Panchami", "Shashti", "Saptami", "Ashtami",
        "Navami", "Dashami", "Ekadashi", "Dwadashi",
        "Trayodashi", "Chaturdashi", "Pournami (Full Moon)"
    )

    private val tithiNamesTe = listOf(
        "పాడ్యమి", "విదియ", "తదియ", "చవితి",
        "పంచమి", "షష్ఠి", "సప్తమి", "అష్టమి",
        "నవమి", "దశమి", "ఏకాదశి", "ద్వాదశి",
        "త్రయోదశి", "చతుర్దశి", "పూర్ణిమ (పౌర్ణమి)"
    )

    private val nakshatraNamesEn = listOf(
        "Ashwini", "Bharani", "Krittika", "Rohini", "Mrigashira", "Arudra",
        "Punarvasu", "Pushya", "Ashlesha", "Magha", "Purva Phalguni (Pubba)", "Uttara Phalguni",
        "Hasta", "Chitra", "Swati", "Vishakha", "Anuradha", "Jyeshtha",
        "Moola", "Purva Ashadha", "Uttara Ashadha", "Shravana", "Dhanishta", "Shatabhisha",
        "Purva Bhadrapada", "Uttara Bhadrapada", "Revati"
    )

    private val nakshatraNamesTe = listOf(
        "అశ్విని", "భరణి", "కృత్తిక", "రోహిణి", "మృగశిర", "ఆరుద్ర",
        "పునర్వసు", "పుష్యమి", "ఆశ్లేష", "మఖ", "పుబ్బ (పూర్వఫల్గుణి)", "ఉత్తరఫల్గుణి",
        "హస్త", "చిత్త", "స్వాతి", "విశాఖ", "అనూరాధ", "జ్యేష్ఠ",
        "మూల", "పూర్వాషాఢ", "ఉత్తరాషాఢ", "శ్రవణం", "ధనిష్ఠ", "శతభిషం",
        "పూర్వాభాద్ర", "ఉత్తరాభాద్ర", "రేవతి"
    )

    private val yogaNamesEn = listOf(
        "Vishkambha", "Priti", "Ayushman", "Saubhagya", "Shobhana", "Atiganda",
        "Sukarma", "Dhriti", "Shoola", "Ganda", "Vriddhi", "Dhruva",
        "Vyaghata", "Harshana", "Vajra", "Asiddhi", "Vyatipata", "Variyan",
        "Parigha", "Shiva", "Siddha", "Sadhya", "Shubha", "Shukla",
        "Brahma", "Indra", "Vaidhriti"
    )

    private val yogaNamesTe = listOf(
        "విష్కంభం", "ప్రీతి", "ఆయుష్మాన్", "సౌభాగ్యం", "శోభనం", "అతిగండం",
        "సుకర్మ", "ధృతి", "శూలం", "గండం", "వృద్ధి", "ధ్రువం",
        "వ్యాఘాతం", "హర్షణం", "వజ్రం", "సిద్ధి", "వ్యతీపాతం", "వరీయాన్",
        "పరిఘం", "శివం", "సిద్ధం", "సాధ్యం", "శుభం", "శుక్లం",
        "బ్రహ్మం", "ఐంద్రం", "వైధృతి"
    )

    private val karanaNamesEn = listOf(
        "Bava", "Balava", "Kaulava", "Taitila", "Garija", "Vanija", "Vishti (Bhadra)",
        "Shakuni", "Chatushpada", "Naga", "Kintughna"
    )

    private val karanaNamesTe = listOf(
        "బవ", "బాలవ", "కౌలవ", "తైతిల", "గరిజ", "వణిజ", "విష్టి (భద్ర)",
        "శకుని", "చతుష్పాత్", "నాగవం", "కింస్తుఘ్నం"
    )

    private val teluguMonthsEn = listOf(
        "Chaitra", "Vaishakha", "Jyeshtha", "Ashadha", "Shravana", "Bhadrapada",
        "Ashwayuja", "Karthika", "Margashirsha", "Pushya", "Magha", "Phalguna"
    )

    private val teluguMonthsTe = listOf(
        "చైత్ర మాసం", "వైశాఖ మాసం", "జ్యేష్ఠ మాసం", "ఆషాఢ మాసం", "శ్రావణ మాసం", "భాద్రపద మాసం",
        "ఆశ్వయుజ మాసం", "కార్తీక మాసం", "మార్గశిర మాసం", "పుష్య మాసం", "మాఘ మాసం", "ఫాల్గుణ మాసం"
    )

    private val daysOfWeekEn = listOf(
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    )

    private val daysOfWeekTe = listOf(
        "ఆదివారం (భానువాసరం)", "సోమవారం (ఇందువాసరం)", "మంగళవారం (భౌమవాసరం)",
        "బుధవారం (సౌమ్యవాసరం)", "గురువారం (బృహస్పతివాసరం)", "శుక్రవారం (భృగువాసరం)", "శనివారం (స్థిరవాసరం)"
    )

    fun calculatePanchangam(calendar: Calendar): PanchangamInfo {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // 1-12
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfWeekIdx = calendar.get(Calendar.DAY_OF_WEEK) - 1 // 0 (Sunday) to 6 (Saturday)

        // Julian Day Number Calculation
        val jd = getJulianDay(year, month, day)

        // Synodic Month (New moon to New moon) = 29.53058885 days
        // Known Amavasya reference epoch: Jan 11, 2024 (JD 2460321.0)
        val refAmavasyaJD = 2460321.0
        val synodicPeriod = 29.53058885
        var moonAge = (jd - refAmavasyaJD) % synodicPeriod
        if (moonAge < 0) moonAge += synodicPeriod

        val tithiDuration = synodicPeriod / 30.0
        val rawTithiIndex = ((moonAge / tithiDuration).toInt()) % 30

        val isShuklaPaksha = rawTithiIndex < 15
        val pakshaEn = if (isShuklaPaksha) "Shukla Paksha (Waxing Moon)" else "Krishna Paksha (Waning Moon)"
        val pakshaTe = if (isShuklaPaksha) "శుక్ల పక్షం" else "కృష్ణ పక్షం (బహుళ పక్షం)"

        val tithiNumber = if (isShuklaPaksha) rawTithiIndex else (rawTithiIndex - 15)
        val tithiEn = if (!isShuklaPaksha && tithiNumber == 14) "Amavasya (New Moon)" else tithiNamesEn[tithiNumber]
        val tithiTe = if (!isShuklaPaksha && tithiNumber == 14) "అమావాస్య" else tithiNamesTe[tithiNumber]

        // Nakshatra Calculation (Sidereal period = 27.321661 days, 27 nakshatras)
        // Ref epoch: Jan 1, 2024 at JD 2460310.5 Moon in Purva Phalguni (idx 10)
        val siderealPeriod = 27.321661
        val nakshatraDuration = siderealPeriod / 27.0
        var siderealAge = (jd - 2460310.5) % siderealPeriod
        if (siderealAge < 0) siderealAge += siderealPeriod
        val nakshatraIndex = ((siderealAge / nakshatraDuration).toInt() + 10) % 27
        val nakshatraEn = nakshatraNamesEn[nakshatraIndex]
        val nakshatraTe = nakshatraNamesTe[nakshatraIndex]

        // Yoga Calculation (Combination of solar and lunar longitude)
        val yogaIndex = (rawTithiIndex + nakshatraIndex) % 27
        val yogaEn = yogaNamesEn[yogaIndex]
        val yogaTe = yogaNamesTe[yogaIndex]

        // Karana Calculation (Half of a tithi, 2 karanas per tithi)
        val karanaIndex = ((rawTithiIndex * 2) % 11)
        val karanaEn = karanaNamesEn[karanaIndex]
        val karanaTe = karanaNamesTe[karanaIndex]

        // Telugu Month Calculation (Chaitra starts around mid March / April)
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val approxMasaIndex = (((dayOfYear - 75) / 30.4) % 12).toInt().let { if (it < 0) it + 12 else it }
        val masaEn = teluguMonthsEn[approxMasaIndex]
        val masaTe = teluguMonthsTe[approxMasaIndex]

        // Ayana & Ruthu
        val isUttarayana = dayOfYear in 14..196 // Jan 14 to July 15
        val ayanaEn = if (isUttarayana) "Uttarayanam (Northern Course)" else "Dakshinayanam (Southern Course)"
        val ayanaTe = if (isUttarayana) "ఉత్తరాయణం" else "దక్షిణాయణం"

        val ruthuIndex = (approxMasaIndex / 2) % 6
        val ruthusEn = listOf("Vasanta Ruthu", "Greeshma Ruthu", "Varsha Ruthu", "Sharad Ruthu", "Hemantha Ruthu", "Shishira Ruthu")
        val ruthusTe = listOf("వసంత ఋతువు", "గ్రీష్మ ఋతువు", "వర్ష ఋతువు", "శరద్ ఋతువు", "హేమంత ఋతువు", "శిశిర ఋతువు")
        val ruthuEn = ruthusEn[ruthuIndex]
        val ruthuTe = ruthusTe[ruthuIndex]

        // Vedic Samvatsaram (Krodhi Nama / Vishvavasu)
        val samvatsara = "శ్రీ క్రోధి నామ సంవత్సరం (Sri Krodhi Nama Samvatsaram)"

        // Weekday timings (Rahu Kalam, Yamagandam, Gulika Kalam, Durmuhurtham)
        val (rahuKalam, yamaGandam, gulikaKalam, durmuhurtham) = getTimingSlots(dayOfWeekIdx)

        // Auspicious slots
        val abhijit = if (dayOfWeekIdx == 3) "11:45 AM – 12:30 PM (Special)" else "11:48 AM – 12:38 PM"
        val brahma = "04:32 AM – 05:20 AM"
        val amrita = getAmritaKalam(nakshatraIndex)

        // Sunrise & Sunset (approx for Puritigadda, Andhra Pradesh ~16.12° N, 80.88° E)
        val sunrise = "06:05 AM"
        val sunset = "05:58 PM"

        // Special Day & Temple Connection
        val (specialNote, recommendedSeva) = getSpecialDayNoteAndSeva(
            dayOfWeekIdx = dayOfWeekIdx,
            rawTithiIndex = rawTithiIndex,
            isShuklaPaksha = isShuklaPaksha,
            tithiNumber = tithiNumber,
            nakshatraEn = nakshatraEn
        )

        val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
        val dateDisplay = dateFormat.format(calendar.time)

        return PanchangamInfo(
            dateDisplay = dateDisplay,
            dayOfWeekEn = daysOfWeekEn[dayOfWeekIdx],
            dayOfWeekTe = daysOfWeekTe[dayOfWeekIdx],
            samvatsara = samvatsara,
            ayana = "$ayanaTe • $ayanaEn",
            ruthu = "$ruthuTe • $ruthuEn",
            masa = "$masaTe ($masaEn)",
            paksha = "$pakshaTe • $pakshaEn",
            tithi = tithiEn,
            tithiTe = tithiTe,
            nakshatra = nakshatraEn,
            nakshatraTe = nakshatraTe,
            yoga = yogaEn,
            yogaTe = yogaTe,
            karana = karanaEn,
            karanaTe = karanaTe,
            sunrise = sunrise,
            sunset = sunset,
            abhijitMuhurtham = abhijit,
            brahmaMuhurtham = brahma,
            amritaKalam = amrita,
            rahuKalam = rahuKalam,
            yamaGandam = yamaGandam,
            gulikaKalam = gulikaKalam,
            durmuhurtham = durmuhurtham,
            specialDayNote = specialNote,
            recommendedSeva = recommendedSeva
        )
    }

    private fun getJulianDay(year: Int, month: Int, day: Int): Double {
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = y / 100
        val b = 2 - a + (a / 4)
        return (365.25 * (y + 4716)).toInt() + (30.6001 * (m + 1)).toInt() + day + b - 1524.5
    }

    private fun getTimingSlots(dayOfWeekIdx: Int): List<String> {
        return when (dayOfWeekIdx) {
            0 -> listOf("04:30 PM – 06:00 PM", "12:00 PM – 01:30 PM", "03:00 PM – 04:30 PM", "04:24 PM – 05:12 PM")
            1 -> listOf("07:30 AM – 09:00 AM", "10:30 AM – 12:00 PM", "01:30 PM – 03:00 PM", "12:35 PM – 01:23 PM")
            2 -> listOf("03:00 PM – 04:30 PM", "09:00 AM – 10:30 AM", "12:00 PM – 01:30 PM", "08:36 AM – 09:24 AM")
            3 -> listOf("12:00 PM – 01:30 PM", "07:30 AM – 09:00 AM", "10:30 AM – 12:00 PM", "11:47 AM – 12:35 PM")
            4 -> listOf("01:30 PM – 03:00 PM", "06:00 AM – 07:30 AM", "09:00 AM – 10:30 AM", "10:11 AM – 10:59 AM")
            5 -> listOf("10:30 AM – 12:00 PM", "03:00 PM – 04:30 PM", "07:30 AM – 09:00 AM", "08:36 AM – 09:24 AM")
            6 -> listOf("09:00 AM – 10:30 AM", "01:30 PM – 03:00 PM", "06:00 AM – 07:30 AM", "06:08 AM – 06:56 AM")
            else -> listOf("12:00 PM – 01:30 PM", "09:00 AM – 10:30 AM", "01:30 PM – 03:00 PM", "12:00 PM – 01:00 PM")
        }
    }

    private fun getAmritaKalam(nakshatraIndex: Int): String {
        val slots = listOf(
            "07:15 AM – 08:45 AM", "10:20 AM – 11:50 AM", "02:10 PM – 03:40 PM",
            "05:30 PM – 07:00 PM", "08:15 PM – 09:45 PM", "11:40 AM – 01:10 PM"
        )
        return slots[nakshatraIndex % slots.size]
    }

    private fun getSpecialDayNoteAndSeva(
        dayOfWeekIdx: Int,
        rawTithiIndex: Int,
        isShuklaPaksha: Boolean,
        tithiNumber: Int,
        nakshatraEn: String
    ): Pair<String?, String> {
        val notes = mutableListOf<String>()

        if (dayOfWeekIdx == 4) { // Thursday
            notes.add("🪔 గురువారం (Guruvaram) — Grand Sai Baba & Lord Dattatreya Abhishek day! Evening 6:30 PM Sai Bhajan.")
        } else if (dayOfWeekIdx == 3) { // Wednesday
            notes.add("🐘 బుధవారం (Wednesday) — Sri Ganpati Maharaj Abhishek & Vighna Nivarana Seva.")
        } else if (dayOfWeekIdx == 6) { // Saturday
            notes.add("🦚 శనివారం (Saturday) — Lord Sri Krishna Abhishek & special pooja.")
        }

        if (isShuklaPaksha && tithiNumber == 14) { // Full Moon
            notes.add("🌕 పౌర్ణమి (Pournami) — Sri Sai Baba Full Moon Pooja & Sri Satyanarayana Swamy Vratham.")
        } else if (!isShuklaPaksha && tithiNumber == 14) { // New Moon
            notes.add("🌑 అమావాస్య (Amavasya) — Pitru Devata remembrance & Shanti Homam.")
        } else if (tithiNumber == 10) { // Ekadashi
            notes.add("🌿 ఏకాదశి (Ekadashi) — Auspicious fasting day & Lord Krishna Tulsi archana.")
        } else if (!isShuklaPaksha && tithiNumber == 3) { // Sankashtahara Chaturthi
            notes.add("🐘 సంకష్టహర చతుర్థి (Sankashtahara Chaturthi) — Ganapathi Homam & Deeparadhana.")
        } else if (tithiNumber == 12) { // Trayodashi / Pradosham
            notes.add("🔱 ప్రదోష వ్రతం (Pradosham) — Auspicious evening twilight prayers.")
        }

        val seva = when {
            dayOfWeekIdx == 4 -> "Special Guruvaram Vastram, Panchamrit Abhishek to Baba on golden throne, and 6:30 PM Bhajan."
            dayOfWeekIdx == 3 -> "Ganapathi Modaka naivedyam, special abhishek & Lakshmi Ganapathi Homam."
            dayOfWeekIdx == 6 -> "Tulsi Mala samarpana to Lord Sri Krishna & Govinda archana."
            isShuklaPaksha && tithiNumber == 14 -> "Sri Satyanarayana Vratham & Sai Baba Full Moon archana."
            else -> "Daily morning Panchamrit, holy Bhasma and Vibhuti abhishek at 5:15 AM."
        }

        val noteText = if (notes.isNotEmpty()) notes.joinToString("\n") else null
        return Pair(noteText, seva)
    }
}
