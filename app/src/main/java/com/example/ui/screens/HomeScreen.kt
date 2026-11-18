package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.data.PanchangamCalculator
import com.example.data.TempleRepository
import com.example.model.SevaFacility
import com.example.ui.components.DevoteeAmenitiesCard
import com.example.ui.components.SacredHeader
import com.example.ui.components.TempleUpdatesList
import com.example.ui.theme.*
import com.example.ui.viewmodel.TempleActivityViewModel
import java.util.Calendar

@Composable
fun HomeScreen(
    onNavigateToTimings: () -> Unit = {},
    onNavigateToAsk: () -> Unit = {},
    onNavigateToSeva: () -> Unit = {},
    onNavigateToPanchangam: () -> Unit = {},
    activityViewModel: TempleActivityViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentStatus = remember { TempleRepository.getCurrentStatus() }
    val (isOpen, statusTitle, statusSubtitle) = currentStatus
    val roomDarshanTimings by activityViewModel.darshanTimings.collectAsStateWithLifecycle()
    val roomHomams by activityViewModel.homams.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Sacred Header with Baba's Medallion and Continuous Chant
        item {
            SacredHeader(
                onNavigateToTimings = onNavigateToTimings,
                onNavigateToAsk = onNavigateToAsk,
                onNavigateToPanchangam = onNavigateToPanchangam,
                onOpenDirections = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TempleRepository.MAPS_URL))
                    context.startActivity(intent)
                }
            )
        }

        // Real-Time Live Status Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .testTag("live_status_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isOpen) Color(0xFFF0FDF4) else Color(0xFFFFFBEB)
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        if (isOpen) listOf(SacredGreen, Color(0xFF86EFAC))
                        else listOf(SacredGold, Color(0xFFFDE68A))
                    )
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                if (isOpen) SacredGreen.copy(alpha = 0.15f)
                                else SacredGold.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isOpen) "🪔" else "⏳",
                            fontSize = 22.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isOpen) SacredGreen else SacredGold)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = statusTitle,
                                fontFamily = MarcellusFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isOpen) SacredGreen else SacredGoldDark
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = statusSubtitle,
                            fontFamily = MuktaFontFamily,
                            fontSize = 13.sp,
                            color = SacredTextDark.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        // Today's Daily Panchangam Card
        item {
            val todayPanchangam = remember { PanchangamCalculator.calculatePanchangam(Calendar.getInstance()) }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToPanchangam() }
                    .testTag("today_panchangam_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TempleSurface),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(SacredGold, Color(0xFFFDE68A)))
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🗓️", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "నేటి పంచాంగం • Today's Panchangam",
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredTerracottaDark
                                )
                                Text(
                                    text = todayPanchangam.dayOfWeekTe,
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 11.sp,
                                    color = SacredGoldDark
                                )
                            }
                        }

                        Surface(
                            color = SacredAmberContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "View Full ➔",
                                fontFamily = MuktaFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = TempleCream,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("తిథి (Tithi)", fontFamily = MuktaFontFamily, fontSize = 10.sp, color = SacredTextMuted)
                                Text(todayPanchangam.tithiTe, fontFamily = MarcellusFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredTerracotta)
                                Text(todayPanchangam.tithi, fontFamily = MuktaFontFamily, fontSize = 11.sp, color = SacredTextDark, maxLines = 1)
                            }
                        }

                        Surface(
                            color = TempleCream,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("నక్షత్రం (Nakshatra)", fontFamily = MuktaFontFamily, fontSize = 10.sp, color = SacredTextMuted)
                                Text(todayPanchangam.nakshatraTe, fontFamily = MarcellusFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredTerracotta)
                                Text(todayPanchangam.nakshatra, fontFamily = MuktaFontFamily, fontSize = 11.sp, color = SacredTextDark, maxLines = 1)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "రాహుకాలం: ${todayPanchangam.rahuKalam}",
                            fontFamily = MuktaFontFamily,
                            fontSize = 11.sp,
                            color = SacredTextDark
                        )
                        Text(
                            text = "అభిజిత్ ముహూర్తం: 11:48 AM – 12:38 PM",
                            fontFamily = MuktaFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SacredGreen
                        )
                    }
                }
            }
        }

        // Daily Darshan Timings Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daily Darshan Timings",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )
                    Text(
                        text = "రోజూ వేళలు",
                        fontFamily = MuktaFontFamily,
                        fontSize = 14.sp,
                        color = SacredGoldDark
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val displayTimings = roomDarshanTimings.filter { it.category != "Break" }
                    displayTimings.forEach { timing ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .testTag("timing_card_${timing.label}"),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = TempleSurface),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = Brush.verticalGradient(listOf(SacredBorder, Color.White))
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = timing.label,
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp,
                                    color = SacredTextMuted,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = timing.timeRange,
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredTerracotta,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Temple closed from 12:30 pm to 5:00 pm daily for rest & naivedyam.",
                    fontFamily = MuktaFontFamily,
                    fontSize = 12.sp,
                    color = SacredTextMuted,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        // Sri Krishna Sai Seva Kendram Feature Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToSeva() }
                    .testTag("home_seva_kendram_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TempleSurface),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(SacredTerracotta, SacredGold))
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🕉️", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Sri Krishna Sai Seva Kendram",
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredTerracottaDark
                                )
                                Text(
                                    text = "శ్రీ కృష్ణ సాయి సేవా కేంద్రం • పూజలు & ప్రసాదం",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 11.sp,
                                    color = SacredGoldDark
                                )
                            }
                        }

                        Surface(
                            color = SacredAmberContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Reserve Seva ➔",
                                fontFamily = MuktaFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Reservations open for Gotra Name Puja, Daily Midday Nivedan Baba Prasadam, Santra Prasadam, Morning Prasadam, and Special Pujas.",
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = SacredTextDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            color = SacredAmberContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🍚 Midday Prasadam", fontFamily = MuktaFontFamily, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SacredTerracottaDark)
                                Text("బాబా మహా ప్రసాదం", fontFamily = MuktaFontFamily, fontSize = 10.sp, color = SacredTextMuted)
                            }
                        }
                        Surface(
                            color = TempleCream,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📜 Gotra Puja", fontFamily = MuktaFontFamily, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SacredTerracottaDark)
                                Text("గోత్ర నామ సంకల్పం", fontFamily = MuktaFontFamily, fontSize = 10.sp, color = SacredTextMuted)
                            }
                        }
                        Surface(
                            color = TempleCream,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🍊 Santra Prasadam", fontFamily = MuktaFontFamily, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SacredTerracottaDark)
                                Text("సాయంత్ర ప్రసాదం", fontFamily = MuktaFontFamily, fontSize = 10.sp, color = SacredTextMuted)
                            }
                        }
                    }
                }
            }
        }

        // Temple Updates Section (List Layout)
        item {
            TempleUpdatesList(
                onNavigateToTimings = onNavigateToTimings,
                onNavigateToFacilities = onNavigateToSeva,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        // Temple Capacity & Devotee Amenities Section
        item {
            DevoteeAmenitiesCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // Seva & Facilities Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Seva & Facilities at Mandiram",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTerracottaDark
                )
                Text(
                    text = "ఆలయ సేవలు మరియు భవన ప్రాంగణం",
                    fontFamily = MuktaFontFamily,
                    fontSize = 13.sp,
                    color = SacredGoldDark
                )

                Spacer(modifier = Modifier.height(12.dp))

                TempleRepository.facilities.forEach { facility ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { onNavigateToSeva() }
                            .testTag("facility_card_${facility.title}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = TempleSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            facility.imageRes?.let { imgRes ->
                                Image(
                                    painter = painterResource(id = imgRes),
                                    contentDescription = facility.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                )
                            }
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = facility.title,
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredTerracotta
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = facility.text,
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                    color = SacredTextMuted
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sacred Homams Section (Local Room Database)
        if (roomHomams.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("home_homams_section")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Sacred Homams & Yagnas",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark
                            )
                            Text(
                                text = "ఆలయ హోమములు మరియు సంకల్ప వివరాలు",
                                fontFamily = MuktaFontFamily,
                                fontSize = 13.sp,
                                color = SacredGoldDark
                            )
                        }
                        Surface(
                            color = SacredAmberContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Offline Ready",
                                fontFamily = MuktaFontFamily,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    roomHomams.forEach { homam ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable { onNavigateToSeva() }
                                .testTag("homam_card_${homam.id}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = TempleSurface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "🔥 ${homam.name}",
                                        fontFamily = MarcellusFontFamily,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SacredTerracotta,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Surface(
                                        color = TempleCream,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = homam.category,
                                            fontFamily = MuktaFontFamily,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = SacredGoldDark,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = homam.nameTe,
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    color = SacredGoldDark
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "🗓️ ${homam.schedule}",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SacredTextDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = homam.significance,
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    color = SacredTextMuted
                                )
                            }
                        }
                    }
                }
            }
        }

        // Devotee Reviews Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .testTag("reviews_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(SacredGold, Color(0xFFFDE68A)))
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "DEVOTEE EXPERIENCES & BLESSINGS",
                        fontFamily = MuktaFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        color = SacredGoldDark
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = TempleRepository.RATING_VALUE,
                            fontFamily = MarcellusFontFamily,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTerracottaDark
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row {
                                repeat(5) {
                                    Text(text = "⭐", fontSize = 14.sp)
                                }
                            }
                            Text(
                                text = TempleRepository.RATING_COUNT,
                                fontFamily = MuktaFontFamily,
                                fontSize = 12.sp,
                                color = SacredTextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = Color.White.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = TempleRepository.FEATURED_QUOTE,
                            fontFamily = MuktaFontFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = SacredTerracottaDark,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        // Quick Bottom Action - Connect with Mandhiram
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = SacredTerracottaDark
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ఓం సాయి రామ్",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 18.sp,
                        color = Color(0xFFFDE68A)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Have questions or want to offer a pooja?",
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=Jai%20Sai%20Ram%20-%20Sri%20Krishna%20Sai%20Dhyana%20Mandhiram"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SacredGreen,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.testTag("home_whatsapp_chat_btn")
                        ) {
                            Text(
                                text = "💬 Chat on WhatsApp",
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${TempleRepository.TEMPLE_PHONE}"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFFDE68A)
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFDE68A))
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.testTag("home_call_btn")
                        ) {
                            Text(
                                text = "📞 Call Mandiram",
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
