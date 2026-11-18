package com.example.data

import com.example.R
import com.example.model.*
import java.util.Calendar

object TempleRepository {

    const val TEMPLE_NAME = "Sri Krishna Sai Dhyana Mandhiram"
    const val TEMPLE_NAME_TELUGU = "శ్రీ కృష్ణ సాయి ధ్యాన మందిరం"
    const val TEMPLE_LOCATION = "Puritigadda · Andhra Pradesh"
    const val TEMPLE_ADDRESS = "4VFQ+P77, Nadakuduru Road, Puritigadda, Andhra Pradesh 521126"
    const val TEMPLE_PHONE = "+91 7981240451"
    const val TEMPLE_PHONE_CLEAN = "917981240451"
    const val MAPS_URL = "https://maps.app.goo.gl/5VJJESmeyuMczDme6"
    const val GOOGLE_MAPS_SEARCH_URL = "https://maps.google.com/?q=Sri+Krishna+Sai+Dhyana+Mandhiram+Puritigadda"
    const val WHATSAPP_CHANNEL_URL = "https://whatsapp.com/channel/0029VbAp0cWBVJl6nz2haE0y"
    const val INSTAGRAM_URL = "https://www.instagram.com/krishnasaimandhiram"
    const val FACEBOOK_PROFILE_URL = "https://www.facebook.com/profile.php?id=61581869828360"
    const val FACEBOOK_PAGE_URL = "https://www.facebook.com/share/1FEByAr5aT/"
    const val YOUTUBE_URL = "https://youtube.com/@puritigadda"

    const val RATING_VALUE = "4.8"
    const val RATING_COUNT = "13 reviews on Google"
    const val FEATURED_QUOTE = "“A peaceful place — one of the popular Sai Baba temples in Krishna district.”"

    const val CAPACITY_TITLE = "400 to 500 Devotees Capacity with Ease"
    const val CAPACITY_TITLE_TELUGU = "ఒకేసారి 400 నుండి 500 మంది భక్తులకు విశాలమైన దర్శనం & వసతి"
    const val CAPACITY_DESCRIPTION = "400 to 500 people can visit the temple at a time with ease, with plenty of space for sitting, sleeping, clean bathrooms, and meditating in the cool breeze."

    val devoteeAmenities = listOf(
        DevoteeAmenity(
            id = "capacity",
            icon = "🏛️",
            title = "400–500 Devotee Capacity",
            titleTe = "ఒకేసారి 400–500 మందికి వసతి",
            description = "400 to 500 people can visit the temple at a time with ease. Generous courtyards and halls prevent overcrowding during peak darshan hours, Thursday bhajans, and festivals.",
            tag = "Large Capacity"
        ),
        DevoteeAmenity(
            id = "sitting",
            icon = "🪑",
            title = "Plenty of Space for Sitting",
            titleTe = "విశాలమైన కూర్చునే ప్రదేశాలు",
            description = "Expansive shaded mandapams and clean courtyard spaces allow families, elders, and large groups to sit comfortably in Bhagwan's peaceful presence.",
            tag = "Spacious Seating"
        ),
        DevoteeAmenity(
            id = "sleeping",
            icon = "🛌",
            title = "Resting & Sleeping Space",
            titleTe = "విశ్రాంతి & నిద్ర సౌకర్యాలు",
            description = "Sheltered, serene resting areas dedicated for visiting pilgrims and traveling families to relax, rest, and sleep peacefully during their stay.",
            tag = "Pilgrim Rest"
        ),
        DevoteeAmenity(
            id = "bathroom",
            icon = "🚿",
            title = "Clean Bathrooms & Restrooms",
            titleTe = "పరిశుభ్రమైన మరుగుదొడ్లు & స్నానపు గదులు",
            description = "Hygienic, well-maintained bathroom and washroom facilities with reliable running water for the convenience and comfort of all visitors.",
            tag = "Clean Restrooms"
        ),
        DevoteeAmenity(
            id = "meditation_breeze",
            icon = "🍃",
            title = "Meditating in the Cool Breeze",
            titleTe = "చల్లని గాలిలో ప్రశాంత ధ్యానం",
            description = "Open-air architectural design captures the gentle, refreshing Krishna delta breeze, providing a serene environment for deep meditation and japam.",
            tag = "Natural Breeze"
        )
    )

    val darshanTimings = listOf(
        DarshanTiming("Morning darshan", "5:15 am – 12:30 pm", "Daily abhishek, archana & morning darshan"),
        DarshanTiming("Evening darshan", "5:00 pm – 8:30 pm", "Evening dhoop aarti, darshan & peaceful meditation"),
        DarshanTiming("Thursday Sai Bhajan", "6:30 pm", "Special Guruvaram congregational bhajan and samuhika pooja")
    )

    val updates = listOf(
        MandiramUpdate(
            id = "morning_darshan",
            title = "Morning darshan now starts at 5:15 AM",
            titleTe = "ఉదయపు దర్శనం ఉదయం 5:15 నుండి ప్రారంభం",
            category = "Darshan Timings",
            dateDisplay = "Recent Update",
            isImportant = true,
            icon = "🌅",
            text = "The mandiram now opens earlier each day — morning darshan and abhishek are from 5:15 am to 12:30 pm every day. Temple closes for afternoon break from 12:30 pm to 5:00 pm.",
            textTe = "మందిరం ఇప్పుడు ప్రతిరోజూ మరింత ముందుగా తెరుచుకుంటుంది. ఉదయపు అభిషేకం మరియు దర్శనం ఉదయం 5:15 నుండి మధ్యాహ్నం 12:30 వరకు కొనసాగుతుంది. మధ్యాహ్నం 12:30 నుండి 5:00 వరకు ఆలయం మూసివేయబడుతుంది.",
            actionText = "View Darshan Timings"
        ),
        MandiramUpdate(
            id = "evening_darshan",
            title = "Evening darshan from 5:00 PM to 8:30 PM",
            titleTe = "సాయంత్రం దర్శన వేళలు 5:00 నుండి 8:30 వరకు",
            category = "Darshan Timings",
            dateDisplay = "Regular Schedule",
            isImportant = true,
            icon = "🪔",
            text = "Evening darshan begins at 5:00 pm and continues until 8:30 pm. Dhoop aarti, twilight deeparadhana, and quiet meditation are held daily during these hours.",
            textTe = "సాయంత్రం దర్శనం 5:00 గంటలకు ప్రారంభమై రాత్రి 8:30 గంటల వరకు కొనసాగుతుంది. ప్రతిరోజూ సాయంత్రం ధూప హారతి మరియు దీపారాధన నిర్వహించబడుతుంది.",
            actionText = "Plan Visit"
        ),
        MandiramUpdate(
            id = "thursday_bhajan",
            title = "Thursday Sai Bhajan at 6:30 PM",
            titleTe = "ప్రతి గురువారం సాయంత్రం 6:30 కి సాయి భజన",
            category = "Weekly Worship",
            dateDisplay = "Every Thursday",
            isImportant = false,
            icon = "🎵",
            text = "Every Thursday evening, devotees gather for Sai Bhajan at 6:30 pm followed by samuhika aarti and prasadam distribution. All families and devotees are warmly welcome to join.",
            textTe = "ప్రతి గురువారం సాయంత్రం 6:30 గంటలకు భక్తులందరి సమక్షంలో సామూహిక సాయి భజన, మంగళ హారతి మరియు తీర్థ ప్రసాద వితరణ జరుగుతుంది.",
            actionText = "Join Guruvaram Seva"
        ),
        MandiramUpdate(
            id = "pilgrim_capacity",
            title = "Spacious Accommodation for 400 to 500 Devotees",
            titleTe = "ఒకేసారి 400 నుండి 500 మంది భక్తులకు విశాలమైన వసతులు",
            category = "Facilities & Comfort",
            dateDisplay = "Facility Notice",
            isImportant = true,
            icon = "🏛️",
            text = "The temple complex comfortably hosts 400 to 500 people at a time with ease. Facilities include extensive sitting spaces, peaceful resting and sleeping halls for out-of-town pilgrims, clean modern washrooms, and open meditation halls cooled by refreshing breezes.",
            textTe = "శ్రీ కృష్ణ సాయి ధ్యాన మందిరంలో ఒకేసారి 400 నుండి 500 మంది భక్తులు ఎలాంటి అసౌకర్యం లేకుండా సందర్శించవచ్చు. కూర్చోవడానికి, విశ్రాంతి తీసుకొని నిద్రపోవడానికి, పరిశుభ్రమైన మరుగుదొడ్లు మరియు చల్లని గాలిలో ధ్యానం చేసుకునే సదుపాయాలు ఉన్నాయి.",
            actionText = "View Facilities"
        ),
        MandiramUpdate(
            id = "homam_pooja",
            title = "Lakshmi Ganapathi Homam & Special Pooja Bookings Open",
            titleTe = "లక్ష్మీ గణపతి హోమం & ప్రత్యేక పూజల బుకింగ్స్",
            category = "Pooja Sevas",
            dateDisplay = "Ongoing Seva",
            isImportant = false,
            icon = "🔥",
            text = "Devotees can sponsor monthly Lakshmi Ganapathi Homam, Satyanarayana Vratham, and Guruvaram vastram alankaram. Priests perform sankalpam in devotee gotra-namas.",
            textTe = "భక్తులు తమ గోత్ర నామాలతో లక్ష్మీ గణపతి హోమం, సత్యనారాయణ స్వామి వ్రతం మరియు గురువారం విశేష వస్త్ర సమర్పణలను బుక్ చేసుకోవచ్చు.",
            actionText = "Book via WhatsApp"
        ),
        MandiramUpdate(
            id = "subhavedika_hall",
            title = "Subhavedika Function Hall Available for Family Events",
            titleTe = "శుభకార్యాల కొరకు శ్రీ కృష్ణ సాయి శుభవేదిక",
            category = "Mandiram Hall",
            dateDisplay = "Announcements",
            isImportant = false,
            icon = "🎉",
            text = "Sri Krishna Sai Subhavedika hall is open for traditional ceremonies, upanayanams, and family celebrations. Strictly pure vegetarian functions only are permitted on temple premises.",
            textTe = "ఆలయ ప్రాంగణంలో సాంప్రదాయక ఉపనయనాలు, అన్నప్రాశన మరియు పుట్టినరోజుల కొరకు శుభవేదిక అందుబాటులో ఉంది. పూర్తిగా శాఖాహార కార్యక్రమాలకు మాత్రమే అనుమతి కలదు.",
            actionText = "Inquire Hall Booking"
        )
    )

    val facilities = listOf(
        SevaFacility(
            title = "Homams & Poojas",
            text = "Lakshmi Ganapathi Homam, Satyanarayana Vratham and archana performed by temple priests.",
            iconName = "Homam",
            imageRes = R.drawable.homam_fire
        ),
        SevaFacility(
            title = "Dhyana Mandapam",
            text = "A quiet hall before Baba's sannidhi for meditation, japam and Guruvaram bhajans.",
            iconName = "Meditation",
            imageRes = R.drawable.baba_darshan
        ),
        SevaFacility(
            title = "Subhavedika",
            text = "Sri Krishna Sai Subhavedika function hall — vegetarian functions only.",
            iconName = "Hall",
            imageRes = R.drawable.temple_wall
        )
    )

    val abhishekDays = listOf(
        AbhishekDay(
            id = "daily",
            dayNameHi = "हर रोज़",
            dayNameEn = "Daily abhishek",
            dayNameTe = "ప్రతిరోజూ ఉదయం",
            icon = "🕉️",
            weekday = null,
            isDaily = true,
            entries = listOf(
                AbhishekEntry(
                    deity = "Sai Baba",
                    imageRes = R.drawable.sai_baba_daily_abhishek,
                    altText = "Sai Baba decorated for daily pooja",
                    text = "रोज़ सुबह साई बाबा का अभिषेक और पूजा होती है — पंचामृत, भस्म और विभूति से, उसके बाद बाबा को नए वस्त्र पहनाए जाते हैं।\n\nDaily morning abhishek of Sri Sai Baba is performed with Panchamrit, holy Bhasma and fragrant Vibhuti, followed by new sacred Vastram and Mangala Aarti.",
                    offerings = listOf("🥛 Panchamrit", "✨ Bhasma", "🤍 Vibhuti", "🧣 New vastram", "🌺 Daily pooja")
                )
            )
        ),
        AbhishekDay(
            id = "wednesday",
            dayNameHi = "बुधवार",
            dayNameEn = "Every Wednesday",
            dayNameTe = "బుధవారం పూజ",
            icon = "🐘",
            weekday = Calendar.WEDNESDAY,
            entries = listOf(
                AbhishekEntry(
                    deity = "Sri Ganpati Maharaj",
                    imageRes = R.drawable.ganpati_maharaj,
                    altText = "Sri Ganpati Maharaj decorated for abhishek",
                    text = "बुधवार को श्री गणपति महाराज का अभिषेक और विशेष पूजा की जाती है।\n\nOn Wednesdays, special abhishek and archana is offered to Lord Ganapathi for removal of obstacles, good fortune, and peace.",
                    offerings = listOf("💧 Abhishek", "🌺 Special pooja", "🕉️ Wednesday seva")
                )
            )
        ),
        AbhishekDay(
            id = "thursday",
            dayNameHi = "गुरुवार",
            dayNameEn = "Every Thursday",
            dayNameTe = "గురువారం గురుపూజ",
            icon = "🪔",
            weekday = Calendar.THURSDAY,
            note = "🪔 गुरुवार शाम 6:30 बजे — साई भजन (Every Thursday 6:30 PM — Sai Bhajan)",
            entries = listOf(
                AbhishekEntry(
                    deity = "Dattatreya Swami",
                    imageRes = R.drawable.dattatreya_swami,
                    altText = "Dattatreya Swami decorated with flowers and new vastram",
                    text = "गुरुवार को दत्तात्रेय स्वामी का अभिषेक दूध, दही, घी, शहद, चीनी, विभूति और गंध से होता है, फिर नए वस्त्र और पूजा।\n\nAbhishek of Lord Dattatreya with milk, curd, pure ghee, honey, sugar, vibhuti, and sandalwood paste, followed by divine alankaram.",
                    offerings = listOf("🥛 Milk", "🥣 Curd", "🫙 Ghee", "🍯 Honey", "◻️ Sugar", "✨ Vibhuti", "🪵 Gandham", "🧣 New vastram")
                ),
                AbhishekEntry(
                    deity = "Sai Baba — Guruvaram",
                    imageRes = R.drawable.sai_baba_thursday,
                    altText = "Sai Baba on the golden throne for Thursday pooja",
                    text = "गुरुवार को साई बाबा का विशेष अभिषेक — दूध, दही, घी, शहद, चीनी, विभूति और गंध से, उसके बाद नए वस्त्र और पूजा।\n\nGrand Guruvaram Abhishek of Shirdi Sai Baba on the golden throne with milk, honey, panchamrit, holy vibhuti, and sacred gandham, followed by grand floral decorations.",
                    offerings = listOf("🥛 Milk", "🥣 Curd", "🫙 Ghee", "🍯 Honey", "◻️ Sugar", "✨ Vibhuti", "🪵 Gandham", "🧣 New vastram")
                )
            )
        ),
        AbhishekDay(
            id = "saturday",
            dayNameHi = "शनिवार",
            dayNameEn = "Every Saturday",
            dayNameTe = "శనివారం పూజ",
            icon = "🦚",
            weekday = Calendar.SATURDAY,
            entries = listOf(
                AbhishekEntry(
                    deity = "Sri Krishna Bhagwan",
                    imageRes = R.drawable.sri_krishna,
                    altText = "Sri Krishna Bhagwan decorated with flowers",
                    text = "शनिवार को श्री कृष्ण भगवान का अभिषेक और विशेष पूजा की जाती है।\n\nOn Saturdays, divine abhishek and archana are performed for Lord Sri Krishna with Tulsi malas, butter offerings, and special stotrams.",
                    offerings = listOf("💧 Abhishek", "🌺 Special pooja", "🦚 Saturday seva")
                )
            )
        ),
        AbhishekDay(
            id = "purnima",
            dayNameHi = "पूर्णिमा",
            dayNameEn = "Full moon special",
            dayNameTe = "పౌర్ణమి విశేష పూజ",
            icon = "🌕",
            weekday = null,
            entries = listOf(
                AbhishekEntry(
                    deity = "Sai Baba — Purnima pooja",
                    imageRes = R.drawable.baba_idol,
                    altText = "Sai Baba idol adorned with garlands on the golden throne",
                    text = "हर पूर्णिमा (पूरे चाँद) के दिन साई बाबा की विशेष पूजा की जाती है।\n\nSpecial Satyanarayana Vratham, full moon archana, and divine deeparadhana conducted on every Purnima day for the welfare of all devotees.",
                    offerings = listOf("🌕 Purnima pooja", "🌺 Special seva")
                )
            )
        )
    )

    val galleryPhotos = listOf(
        GalleryPhoto("1", R.drawable.baba_idol, "Sai Baba on Golden Throne", "Sri Sai Baba idol adorned with rich floral garlands on the consecrated golden throne", "Shrine"),
        GalleryPhoto("2", R.drawable.baba_idol_closeup, "Divine Darshan Close-up", "Serene facial darshan of Bhagwan Sri Sathya Sai Baba at the mandhiram", "Shrine"),
        GalleryPhoto("3", R.drawable.baba_darshan, "Shrine & Sannidhi", "Inner sanctum sanctorum showing devotees in reverent prayer", "Shrine"),
        GalleryPhoto("4", R.drawable.homam_fire, "Homam Fire Ahuti", "Temple priests offering sacred ahuti into the sanctified homam kunda", "Rituals"),
        GalleryPhoto("5", R.drawable.temple_entrance, "Mandhiram Entrance", "Grand entrance gopuram welcoming pilgrims on Nadakuduru Road", "Mandiram"),
        GalleryPhoto("6", R.drawable.temple_view, "Temple Compound", "Spacious open courtyard and peaceful surroundings of Puritigadda mandhiram", "Mandiram"),
        GalleryPhoto("7", R.drawable.temple_wall, "Sacred Wall & Sculptures", "Temple boundary walls adorned with saintly murals, Om, and sacred symbols", "Mandiram"),
        GalleryPhoto("8", R.drawable.temple_festival_lights, "Festival Illumination", "Mandhiram glowing brightly with golden decorative lights during celebrations", "Mandiram"),
        GalleryPhoto("9", R.drawable.temple_evening, "Twilight Peace", "Peaceful twilight atmosphere around the mandhiram grounds", "Mandiram"),
        GalleryPhoto("10", R.drawable.temple_pooja, "Devotee Pooja", "Devotees participating in holy archana and deeparadhana", "Rituals"),
        GalleryPhoto("11", R.drawable.temple_gate, "Morning Temple Gate", "Temple gateway opening at 5:15 AM as sunrise touches the shrine", "Mandiram"),
        GalleryPhoto("12", R.drawable.sai_baba_daily_abhishek, "Daily Morning Abhishek", "Sri Sai Baba adorned with holy bhasma, vibhuti, and fresh flower garlands", "Deities"),
        GalleryPhoto("13", R.drawable.sai_baba_thursday, "Thursday Guruvaram Alankaram", "Royal yellow silk vastram and fragrant rose garlands on Thursday", "Deities"),
        GalleryPhoto("14", R.drawable.dattatreya_swami, "Lord Dattatreya Swami", "Trishakti Lord Dattatreya adorned with golden ornaments and holy vibhuti", "Deities"),
        GalleryPhoto("15", R.drawable.ganpati_maharaj, "Sri Ganapathi Maharaj", "Vighnaharta Sri Ganesha shrine with special Wednesday floral alankaram", "Deities"),
        GalleryPhoto("16", R.drawable.sri_krishna, "Sri Krishna Bhagwan", "Lord Sri Krishna with peacock feather crown and sweet flute", "Deities")
    )

    val faqItems = listOf(
        FaqItem(
            id = "1",
            question = "What are the mandiram darshan timings?",
            questionTe = "మందిరం దర్శన వేళలు ఏమిటి?",
            answer = "Morning darshan is open from 5:15 am to 12:30 pm every day. Evening darshan is from 5:00 pm to 8:30 pm. The mandhiram remains closed in the afternoon from 12:30 pm to 5:00 pm for naivedyam and rest.",
            answerTe = "ప్రతిరోజూ ఉదయం 5:15 నుండి మధ్యాహ్నం 12:30 వరకు, సాయంత్రం 5:00 నుండి రాత్రి 8:30 వరకు దర్శనం ఉంటుంది. మధ్యాహ్నం 12:30 నుండి 5:00 వరకు ఆలయం మూసివేయబడుతుంది.",
            category = "Timings"
        ),
        FaqItem(
            id = "2",
            question = "When is the Thursday Bhajan held?",
            questionTe = "గురువారం సాయి భజన ఎప్పుడు జరుగుతుంది?",
            answer = "Every Thursday evening at 6:30 pm, devotees gather for divine Sai Bhajan and dhoop aarti. All devotees, families, and visitors are warmly welcome to participate.",
            answerTe = "ప్రతి గురువారం సాయంత్రం 6:30 గంటలకు భక్తులందరి సమక్షంలో సామూహిక సాయి భజన మరియు ధూప హారతి నిర్వహించబడుతుంది.",
            category = "Sevas"
        ),
        FaqItem(
            id = "3",
            question = "How to reach Sri Krishna Sai Dhyana Mandhiram, Puritigadda?",
            questionTe = "పురిటిగడ్డ సాయి మందిరానికి ఎలా చేరుకోవాలి?",
            answer = "The temple is situated on Nadakuduru Road in Puritigadda (Pin: 521126), Krishna District, Andhra Pradesh. It is easily accessible via Challapalli or Avanigadda roads. Frequent autos and local buses run from Challapalli, Machilipatnam, and Vijayawada.",
            answerTe = "ఆంధ్రప్రదేశ్, కృష్ణా జిల్లా, పురిటిగడ్డ గ్రామంలోని నడకుదురు రోడ్డులో ఈ మందిరం ఉంది. చల్లపల్లి, అవనిగడ్డ, మచిలీపట్నం మరియు విజయవాడ నుండి బస్సులు మరియు ఆటోల సౌకర్యం కలదు.",
            category = "Directions"
        ),
        FaqItem(
            id = "4",
            question = "Can devotees sponsor Lakshmi Ganapathi Homam or Abhishek?",
            questionTe = "లక్ష్మీ గణపతి హోమం లేదా అభిషేకం బుక్ చేసుకోవచ్చా?",
            answer = "Yes! Devotees can sponsor Lakshmi Ganapathi Homam, Satyanarayana Swamy Vratham, Thursday Guruvaram vastram, and daily abhishek. You can speak with the temple office or message directly on WhatsApp at +91 7981240451.",
            answerTe = "అవును, లక్ష్మీ గణపతి హోమం, సత్యనారాయణ వ్రతం, గురువారం వస్త్ర సేవ, అభిషేకాలను నిర్వహించవచ్చు. ఆలయ కార్యాలయం లేదా వాట్సాప్ (+91 7981240451) ద్వారా సంప్రదించవచ్చు.",
            category = "Sevas"
        ),
        FaqItem(
            id = "5",
            question = "What is Sri Krishna Sai Subhavedika function hall?",
            questionTe = "శ్రీ కృష్ణ సాయి శుభవేదిక అంటే ఏమిటి?",
            answer = "Sri Krishna Sai Subhavedika is a consecrated hall maintained by the mandhiram for traditional ceremonies, upanayanams, sacred birthdays, and auspicious family events. Strictly pure vegetarian functions only are permitted.",
            answerTe = "శుభకార్యాలు, ఉపనయనం, అన్నప్రాశన మరియు పుట్టినరోజుల కొరకు ఆలయ ప్రాంగణంలో శుభవేదిక కలదు. ఇక్కడ పూర్తిగా శాఖాహార కార్యక్రమాలకు మాత్రమే అనుమతి కలదు.",
            category = "Facilities"
        ),
        FaqItem(
            id = "6",
            question = "Is there a meditation hall (Dhyana Mandapam)?",
            questionTe = "ధ్యానం కొరకు మందిరంలో స్థలం ఉందా?",
            answer = "Yes, Dhyana Mandapam is situated right before Baba's holy sannidhi. It is a quiet, sacred space designed specifically for peaceful meditation, japa, and silent reflection in the natural cool breeze.",
            answerTe = "అవును, బాబా సన్నిధి ఎదుట ప్రశాంతమైన ధ్యాన మండపం కలదు. భక్తులు ఇక్కడ నిశ్శబ్దంగా కూర్చొని చల్లని గాలిలో ధ్యానం, నామస్మరణ చేసుకోవచ్చు.",
            category = "Facilities"
        ),
        FaqItem(
            id = "7",
            question = "How many devotees can visit the temple at a time?",
            questionTe = "మందిరంలో ఒకేసారి ఎంతమంది భక్తులు దర్శనం చేసుకోవచ్చు?",
            answer = "400 to 500 people can visit the temple at a time with ease. The spacious layout, open courtyards, and wide shaded mandapams allow large gatherings and families to visit comfortably without feeling crowded.",
            answerTe = "శ్రీ కృష్ణ సాయి ధ్యాన మందిరంలో ఒకేసారి 400 నుండి 500 మంది భక్తులు ఎలాంటి అసౌకర్యం లేకుండా విశాలంగా దర్శనం చేసుకోవచ్చు. విశాలమైన ప్రాంగణం మరియు మండపాలు కలవు.",
            category = "Facilities"
        ),
        FaqItem(
            id = "8",
            question = "Are there facilities for sitting, sleeping, and bathrooms?",
            questionTe = "కూర్చోవడానికి, నిద్రపోవడానికి మరియు మరుగుదొడ్ల సౌకర్యాలు ఉన్నాయా?",
            answer = "Yes! The mandhiram offers plenty of comfortable space for sitting, resting and sleeping areas for traveling pilgrims, clean and hygienic bathroom/toilet facilities with running water, and serene spots to meditate in the refreshing cool breeze.",
            answerTe = "అవును, భక్తులు సౌకర్యవంతంగా కూర్చోవడానికి, యాత్రికులు విశ్రాంతి తీసుకొని నిద్రపోవడానికి అనువైన స్థలాలు, పరిశుభ్రమైన మరుగుదొడ్లు మరియు స్నానపు గదులు, చల్లని ఆహ్లాదకరమైన గాలిలో ప్రశాంతంగా ధ్యానం చేసుకునే సదుపాయాలు ఉన్నాయి.",
            category = "Facilities"
        )
    )

    val saiPromises = listOf(
        SaiPromise(1, "No harm shall befall him who steps on the soil of Shirdi / Baba's temple.", "నా సన్నిధిలోనికి అడుగుపెట్టిన వారి ఆపదలు తొలగిపోతాయి."),
        SaiPromise(2, "He who comes to my feet will cast off all sorrow and despair.", "నా సమాధిని లేదా సన్నిధిని ఆశ్రయించిన వారికి దుఃఖం ఉండదు."),
        SaiPromise(3, "Though I cast off this mortal frame, I shall always come to the aid of my devotees.", "నేను భౌతిక దేహాన్ని విడిచినా, భక్తులను నిరంతరం రక్షిస్తూనే ఉంటాను."),
        SaiPromise(4, "My tomb shall speak and move with those who surrender unto me.", "నన్ను శరణు వేడిన వారితో నా సమాధి మాట్లాడుతుంది."),
        SaiPromise(5, "I am ever living to help and guide all who come to me.", "నా శరణు కోరిన వారికి సహాయం చేయడానికి నేను ఎల్లప్పుడూ సజీవంగా ఉంటాను."),
        SaiPromise(6, "If you look to me, I look to you.", "మీరు నా వైపు చూస్తే, నేను మీ వైపు చూస్తాను."),
        SaiPromise(7, "Cast all your burdens on me, and I shall bear them surely.", "మీ భారమంతా నాపై వేయండి, నేను మోస్తాను."),
        SaiPromise(8, "If you seek my advice and help, it shall be given unto you at once.", "నా సహాయం మరియు ఉపదేశం కోరిన క్షణమే లభిస్తుంది."),
        SaiPromise(9, "There shall be no want in the house of my devotee.", "నా భక్తుని ఇంట ఎన్నటికీ లేమి అనేది ఉండదు."),
        SaiPromise(10, "If any devotee asks with faith, he shall receive my boundless grace.", "భక్తితో అడిగిన ప్రతి భక్తునికి నా అనంత కృప లభిస్తుంది."),
        SaiPromise(11, "Blessed is he who has realized the oneness of Sai with all beings.", "సాయిని సర్వప్రాణులలో దర్శించేవాడే ధన్యుడు.")
    )

    fun getCurrentStatus(): Triple<Boolean, String, String> {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)
        val totalMinutes = hour * 60 + minute

        val morningStart = 5 * 60 + 15  // 5:15 AM
        val morningEnd = 12 * 60 + 30   // 12:30 PM
        val eveningStart = 17 * 60      // 5:00 PM
        val eveningEnd = 20 * 60 + 30   // 8:30 PM

        return when {
            totalMinutes in morningStart..morningEnd -> {
                val remainingMinutes = morningEnd - totalMinutes
                val hoursLeft = remainingMinutes / 60
                val minsLeft = remainingMinutes % 60
                val timeRemaining = if (hoursLeft > 0) "${hoursLeft}h ${minsLeft}m left" else "${minsLeft}m left"
                Triple(true, "Open Now (Morning Darshan)", "Closes at 12:30 PM ($timeRemaining)")
            }
            totalMinutes in eveningStart..eveningEnd -> {
                val remainingMinutes = eveningEnd - totalMinutes
                val hoursLeft = remainingMinutes / 60
                val minsLeft = remainingMinutes % 60
                val timeRemaining = if (hoursLeft > 0) "${hoursLeft}h ${minsLeft}m left" else "${minsLeft}m left"
                Triple(true, "Open Now (Evening Darshan)", "Closes at 8:30 PM ($timeRemaining)")
            }
            totalMinutes in (morningEnd + 1) until eveningStart -> {
                val waitMinutes = eveningStart - totalMinutes
                val hoursWait = waitMinutes / 60
                val minsWait = waitMinutes % 60
                val waitText = if (hoursWait > 0) "in ${hoursWait}h ${minsWait}m" else "in ${minsWait}m"
                Triple(false, "Afternoon Break (Closed)", "Reopens for Evening Darshan at 5:00 PM ($waitText)")
            }
            else -> {
                val waitMinutes = if (totalMinutes < morningStart) {
                    morningStart - totalMinutes
                } else {
                    (24 * 60 - totalMinutes) + morningStart
                }
                val hoursWait = waitMinutes / 60
                val minsWait = waitMinutes % 60
                val waitText = if (hoursWait > 0) "in ${hoursWait}h ${minsWait}m" else "in ${minsWait}m"
                Triple(false, "Closed for the Night", "Opens at 5:15 AM tomorrow ($waitText)")
            }
        }
    }
}
