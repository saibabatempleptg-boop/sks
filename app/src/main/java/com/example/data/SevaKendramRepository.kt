package com.example.data

import com.example.model.SevaItem

object SevaKendramRepository {

    const val KENDRAM_TITLE = "Sri Krishna Sai Seva Kendram"
    const val KENDRAM_TITLE_TELUGU = "శ్రీ కృష్ణ సాయి సేవా కేంద్రం"
    const val KENDRAM_SUBTITLE = "Reservations for Gotra Name Pujas, Daily Midday Nivedan Baba Prasadam, Santra Prasadam, Morning Prasadam, and Special Worship."

    val sevasList = listOf(
        // Gotra Name Puja
        SevaItem(
            id = "gotra_puja",
            name = "Gotra Name Puja (గోత్ర నామ పూజ)",
            nameTe = "గోత్ర నామ సంకల్ప పూజ",
            category = "Gotra Name Puja",
            timing = "Daily at 5:15 AM & 5:00 PM",
            description = "Special personal or family sankalpam performed in your Gotram, Nakshatram, and family members' names before Baba's holy sannidhi. Sacred vibhuti and blessings sent to devotees.",
            descriptionTe = "భక్తుల గోత్రం, నక్షత్రం మరియు కుటుంబ సభ్యుల పేర్లతో బాబా సన్నిధిలో ప్రత్యేక సంకల్పం చేసి పూజ మరియు అర్చన నిర్వహించబడుతుంది. పూజా విభూతి మరియు ప్రసాదం లభిస్తుంది.",
            offerings = listOf("📜 Gotra Sankalpam", "🌺 Archana", "✨ Sacred Vibhuti", "🪔 Mangala Aarti"),
            icon = "📜"
        ),

        // Prasadam & Nivedan Sevas
        SevaItem(
            id = "daily_midday_prasadam",
            name = "Daily Midday Nivedan Baba Prasadam",
            nameTe = "నిత్య మధ్యాహ్న నైవేద్యం - బాబా మహా ప్రసాదం",
            category = "Prasadam & Nivedan",
            timing = "Daily at 12:00 PM (Noon)",
            description = "Sponsor the holy noon Mahaprasadam offered to Bhagwan Sri Sai Baba, distributed to visiting pilgrims and devotees as annaprasadam.",
            descriptionTe = "ప్రతిరోజూ మధ్యాహ్నం 12:00 గంటలకు బాబాకు సమర్పించే రాజభోగ మహా ప్రసాదం. వచ్చిన భక్తులకు బాబా ప్రసాదంగా అన్నవితరణ జరుగుతుంది.",
            offerings = listOf("🍚 Annadanam Bhog", "🍛 Mahaprasadam", "🥛 Ksheerabhishekam", "🙏 Devotee Distribution"),
            icon = "🍚",
            isPrasadam = true
        ),
        SevaItem(
            id = "midday_nivedan",
            name = "Midday Nivedan (మధ్యాహ్న నైవేద్యం)",
            nameTe = "మధ్యాహ్న నైవేద్య సమర్పణ",
            category = "Prasadam & Nivedan",
            timing = "Daily at 12:00 PM",
            description = "Traditional midday naivedyam offering to Baba before the temple rests for the afternoon break (12:30 PM to 5:00 PM).",
            descriptionTe = "మధ్యాహ్న విశ్రాంతి సమయానికి ముందు బాబాకు భక్తితో సమర్పించే సంపూర్ణ నైవేద్యం.",
            offerings = listOf("🍲 Shuddha Naivedyam", "🍇 Phala Samarpana", "🪔 Madhyahna Aarti"),
            icon = "🍲",
            isPrasadam = true
        ),
        SevaItem(
            id = "morning_prasadam",
            name = "Morning Prasadam (ఉదయపు ప్రసాదం)",
            nameTe = "ఉదయపు బాలభోగ్ ప్రసాదం",
            category = "Prasadam & Nivedan",
            timing = "Daily at 6:30 AM (Post-Abhishek)",
            description = "Sponsor the early morning balbhog / breakfast prasadam offered to Baba following the 5:15 AM abhishek, distributed to morning pilgrims.",
            descriptionTe = "ఉదయం 5:15 అభిషేకం అనంతరం బాబాకు సమర్పించే ఉదయపు బాలభోగ్ ప్రసాద వితరణ.",
            offerings = listOf("🥣 Morning Balbhog", "🥛 Fresh Milk", "🍌 Fresh Fruits"),
            icon = "🥣",
            isPrasadam = true
        ),
        SevaItem(
            id = "santra_prasadam",
            name = "Santra Prasadam (సంత్ర ప్రసాదం / సాయంత్ర ప్రసాదం)",
            nameTe = "సంత్ర ప్రసాదం (సాయంత్ర ఫల & తీపి ప్రసాదం)",
            category = "Prasadam & Nivedan",
            timing = "Daily at 6:30 PM (Post-Dhoop Aarti)",
            description = "Consecrated evening fruit and sweet prasadam offering (Santra / Sannidhana Prasadam) blessed at Baba's feet and distributed following evening bhajans and aarti.",
            descriptionTe = "సాయంత్రం ధూప హారతి మరియు గురువారం భజనల అనంతరం బాబా సన్నిధిలో పవిత్రం చేసిన సంత్ర ఫల మరియు మధుర ప్రసాద సమర్పణ.",
            offerings = listOf("🍊 Consecrated Fruits", "🍬 Sweet Prasadam", "🌺 Evening Aarti"),
            icon = "🍊",
            isPrasadam = true
        ),

        // Periodic Pujas
        SevaItem(
            id = "daily_puja",
            name = "Daily Puja & Abhishek (నిత్య పూజ)",
            nameTe = "నిత్య ఉదయపు అభిషేకం & అర్చన",
            category = "Daily Puja",
            timing = "Daily at 5:15 AM – 12:30 PM",
            description = "Morning abhishek of Sri Sai Baba with Panchamrit, holy Bhasma, Vibhuti, followed by fresh Vastram, fragrant flower malas, and archana.",
            descriptionTe = "రోజూ ఉదయం పంచామృతం, భస్మం, విభూతితో బాబాకు అభిషేకం మరియు నూతన వస్త్రాలంకరణ.",
            offerings = listOf("🥛 Panchamrit", "✨ Holy Bhasma", "🤍 Fragrant Vibhuti", "🧣 New Vastram"),
            icon = "🕉️"
        ),
        SevaItem(
            id = "wednesday_puja",
            name = "Wednesday Sri Ganpati Puja (బుధవారం పూజ)",
            nameTe = "బుధవారం శ్రీ గణపతి మహారాజ్ విశేష పూజ",
            category = "Wednesday Puja",
            timing = "Every Wednesday morning",
            description = "Special abhishek and archana offered to Lord Ganapathi Maharaj for removing obstacles, ushering prosperity, and spiritual peace.",
            descriptionTe = "విఘ్న నివారణ మరియు సంపద కొరకు బుధవారం నాడు శ్రీ గణపతి మహారాజ్ కు విశేష అభిషేకం, మోదక నైవేద్యం.",
            offerings = listOf("💧 Sacred Abhishek", "🌺 Garika & Malas", "🍯 Modakam", "🕉️ Ganapathi Stotram"),
            icon = "🐘"
        ),
        SevaItem(
            id = "thursday_puja",
            name = "Thursday Guruvaram Puja (గురువారం పూజ)",
            nameTe = "గురువారం శ్రీ దత్తాత్రేయ & సాయి బాబా పూజ",
            category = "Thursday Puja",
            timing = "Every Thursday (Abhishek & 6:30 PM Bhajan)",
            description = "Grand abhishek to Lord Dattatreya and Shirdi Sai Baba on the golden throne with milk, curd, ghee, honey, sugar, vibhuti, and gandham, followed by 6:30 PM Bhajan.",
            descriptionTe = "గురువారం నాడు దత్తాత్రేయ స్వామి మరియు సాయి బాబాకి క్షీర, దధి, మధు, ఘృత విశేష అభిషేకం, పసుపు పట్టు వస్త్రం మరియు సాయంత్రం 6:30 భజన.",
            offerings = listOf("🥛 Milk & Honey", "🍯 Panchamrit", "🪵 Gandham", "🧣 Yellow Vastram", "🎵 6:30 PM Bhajan"),
            icon = "🪔"
        ),
        SevaItem(
            id = "saturday_puja",
            name = "Saturday Sri Krishna Puja (శనివారం పూజ)",
            nameTe = "శనివారం శ్రీ కృష్ణ భగవాన్ విశేష పూజ",
            category = "Saturday Puja",
            timing = "Every Saturday morning",
            description = "Divine abhishek and archana for Lord Sri Krishna Bhagwan with holy Tulsi malas, fresh butter offerings, and devotional stotrams.",
            descriptionTe = "శనివారం నాడు శ్రీ కృష్ణ స్వామికి పవిత్ర తులసి మాల సమర్పణ, వెన్న నైవేద్యం మరియు విశేష అర్చన.",
            offerings = listOf("🌿 Tulsi Malas", "🧈 Fresh Butter", "💧 Abhishek", "🦚 Krishna Archana"),
            icon = "🦚"
        ),
        SevaItem(
            id = "full_moon_puja",
            name = "Full Moon / Purnima Puja (పౌర్ణమి పూజ)",
            nameTe = "పౌర్ణమి శ్రీ సత్యనారాయణ వ్రతం & బాబా పూజ",
            category = "Full Moon Puja",
            timing = "Every Purnima (Full Moon Day)",
            description = "Grand Satyanarayana Swamy Vratham, Deeparadhana, and special Full Moon archana to Sai Baba on the auspicious Purnima tithi.",
            descriptionTe = "ప్రతి పౌర్ణమి పర్వదినాన శ్రీ సత్యనారాయణ స్వామి వ్రతం, దీపారాధన మరియు బాబా విశేష అలంకరణ.",
            offerings = listOf("🌕 Purnima Vratham", "🪔 108 Deeparadhana", "🌸 Special Alankaram", "🍚 Mahaprasadam"),
            icon = "🌕"
        ),
        SevaItem(
            id = "monthly_puja",
            name = "Monthly Puja (మాస పూజ)",
            nameTe = "మాస సంకల్ప పూజ (సంక్రాంతి / శివరాత్రి)",
            category = "Monthly Puja",
            timing = "Monthly Auspicious Tithis",
            description = "Monthly sankalpam for long-term health, family harmony, and planetary peace performed on Masik Shivaratri, Sankashtahara Chaturthi, or Ekadashi.",
            descriptionTe = "కుటుంబ శ్రేయస్సు కొరకు మాస శివరాత్రి, సంకష్టహర చవితి, ఏకాదశి వంటి విశేష తిథులలో నిర్వహించే మాస పూజ.",
            offerings = listOf("📅 Monthly Sankalpam", "🥥 Coconut Offering", "🌺 Bilva / Tulsi", "✨ Vibhuti Prasadam"),
            icon = "🗓️"
        ),
        SevaItem(
            id = "special_puja",
            name = "Special Puja & Homams (ప్రత్యేక పూజ)",
            nameTe = "లక్ష్మీ గణపతి హోమం & ప్రత్యేక యజ్ఞం",
            category = "Special Puja",
            timing = "By Devotee Appointment / Festival Days",
            description = "Lakshmi Ganapathi Homam, Ayushya Homam, and customized family milestone poojas performed by authorized temple priests with sanctified ahutis.",
            descriptionTe = "ఆలయ వేద పండితులచే శాస్త్రోక్తంగా నిర్వహించబడే లక్ష్మీ గణపతి హోమం, నవగ్రహ హోమం మరియు కుటుంబ ప్రత్యేక పూజలు.",
            offerings = listOf("🔥 Sanctified Homam", "🌾 Purnahuti", "🍯 Havis Samarpana", "📜 Ashirvachanam"),
            icon = "🔥"
        )
    )

    fun formatWhatsAppReservationMessage(
        devoteeName: String,
        phone: String,
        gotram: String,
        nakshatram: String,
        familyMembers: String,
        preferredDate: String,
        seva: SevaItem,
        notes: String
    ): String {
        return """
            *🙏 Sri Krishna Sai Seva Kendram Reservation*
            Mandhiram: Sri Krishna Sai Dhyana Mandhiram, Puritigadda
            
            *Selected Seva:* ${seva.name}
            *Category:* ${seva.category}
            *Seva Timing:* ${seva.timing}
            
            *Devotee Name:* $devoteeName
            *Mobile Number:* $phone
            *Gotram (గోత్రం):* ${if (gotram.isNotBlank()) gotram else "Not specified"}
            *Nakshatram (నక్షత్రం):* ${if (nakshatram.isNotBlank()) nakshatram else "Not specified"}
            *Family Members:* ${if (familyMembers.isNotBlank()) familyMembers else "Self & Family"}
            *Preferred Date:* ${if (preferredDate.isNotBlank()) preferredDate else "Upcoming auspicious day"}
            
            ${if (notes.isNotBlank()) "*Special Sankalpam Request:* $notes\n" else ""}
            Please confirm our seva reservation and sankalpam timings.
            
            Om Sai Ram 🙏
        """.trimIndent()
    }
}
