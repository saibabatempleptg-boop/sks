package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.TempleRepository
import com.example.model.AbhishekDay
import com.example.model.AbhishekEntry
import com.example.ui.components.GarlandTicker
import com.example.ui.theme.*
import com.example.ui.viewmodel.TempleActivityViewModel
import java.util.Calendar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AbhishekScreen(
    onSelectPhoto: (Int) -> Unit = {},
    activityViewModel: TempleActivityViewModel = viewModel()
) {
    val context = LocalContext.current
    var selectedFilterId by remember { mutableStateOf("all") }
    val roomAbhishekSchedules by activityViewModel.abhishekSchedules.collectAsStateWithLifecycle()
    val roomHomams by activityViewModel.homams.collectAsStateWithLifecycle()

    val currentDayOfWeek = remember {
        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    }

    val filteredDays = remember(selectedFilterId) {
        if (selectedFilterId == "all") {
            TempleRepository.abhishekDays
        } else {
            TempleRepository.abhishekDays.filter { it.id == selectedFilterId }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("abhishek_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        item {
            GarlandTicker()
        }

        // Top Header Banner
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(SacredTerracottaDark, SacredTerracotta)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Surface(
                    color = Color(0xFF451A03).copy(alpha = 0.6f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "WEEKLY WORSHIP & RITUALS",
                        fontFamily = MuktaFontFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = Color(0xFFFDE68A),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Abhishek Timeline",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "వారం వారి విశేష అభిషేకములు & సేవల వివరాలు",
                    fontFamily = MuktaFontFamily,
                    fontSize = 14.sp,
                    color = Color(0xFFFCD34D)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "हर हफ़्ते दोहराए जाने वाले अभिषेक और पूजा — रोज़, बुधवार, गुरुवार और शनिवार के कार्य। Each day features sacred dravyas, vastrams, and archana.",
                    fontFamily = MuktaFontFamily,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color(0xFFF5EFE3)
                )
            }
        }

        // Horizontal Day Selector Filter Chips
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedFilterId == "all",
                        onClick = { selectedFilterId = "all" },
                        label = { Text("All Days", fontFamily = MuktaFontFamily, fontWeight = FontWeight.SemiBold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SacredTerracotta,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("filter_all_days")
                    )
                }

                items(TempleRepository.abhishekDays) { day ->
                    val isToday = day.weekday == currentDayOfWeek || (day.isDaily)
                    FilterChip(
                        selected = selectedFilterId == day.id,
                        onClick = { selectedFilterId = day.id },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(day.icon)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(day.dayNameEn, fontFamily = MuktaFontFamily)
                                if (isToday) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("⭐", fontSize = 10.sp)
                                }
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SacredTerracotta,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("filter_day_${day.id}")
                    )
                }
            }
        }

        // Abhishek Days List
        items(filteredDays, key = { it.id }) { day ->
            val isToday = day.weekday == currentDayOfWeek || day.isDaily

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("abhishek_card_${day.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TempleSurface),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        if (isToday) listOf(SacredGold, Color(0xFFFDE68A))
                        else listOf(SacredBorder, Color.White)
                    )
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isToday) 4.dp else 2.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Day Title Header Bar
                    Surface(
                        color = if (isToday) SacredAmberContainer else Color(0xFFF5EFE3),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val matchedSchedule = roomAbhishekSchedules.find { it.scheduleKey == day.id }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = day.icon, fontSize = 22.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "${day.dayNameHi} • ${day.dayNameEn}",
                                        fontFamily = MarcellusFontFamily,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SacredTerracottaDark
                                    )
                                    Text(
                                        text = matchedSchedule?.let { "${day.dayNameTe} • ${it.timings}" } ?: day.dayNameTe,
                                        fontFamily = MuktaFontFamily,
                                        fontSize = 12.sp,
                                        color = SacredTextMuted
                                    )
                                }
                            }

                            if (isToday) {
                                Surface(
                                    color = SacredTerracotta,
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = "TODAY • आज",
                                        fontFamily = MuktaFontFamily,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Special Day Note (e.g., Thursday Bhajan)
                    day.note?.let { note ->
                        Surface(
                            color = Color(0xFFFEF3C7),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = note,
                                fontFamily = MuktaFontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // Entries inside this day
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        day.entries.forEach { entry ->
                            AbhishekEntryView(
                                entry = entry,
                                onWhatsAppBook = {
                                    val msg = "Jai Sai Ram. I would like to inquire about ${entry.deity} seva / abhishek at Sri Krishna Sai Dhyana Mandhiram."
                                    val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=${Uri.encode(msg)}"
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                }
                            )
                        }
                    }
                }
            }
        }

        // Homams & Sacred Fire Rituals Directory (Room-backed)
        if (roomHomams.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .testTag("abhishek_homams_section")
                ) {
                    Text(
                        text = "Sacred Homams Schedule",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )
                    Text(
                        text = "హోమాలు మరియు ప్రత్యేక యజ్ఞాలు",
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        color = SacredGoldDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    roomHomams.forEach { homam ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .testTag("abhishek_homam_card_${homam.id}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = TempleSurface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🔥 ${homam.name}",
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredTerracotta
                                )
                                Text(
                                    text = homam.nameTe,
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    color = SacredGoldDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "⏰ ${homam.schedule}",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SacredTextDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = homam.sankalpamDetails,
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    color = SacredTextMuted
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Offerings: ${homam.offerings}",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SacredTerracottaDark
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AbhishekEntryView(
    entry: AbhishekEntry,
    onWhatsAppBook: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("entry_${entry.deity}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = entry.imageRes),
                contentDescription = entry.altText,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.5.dp, SacredBorder, RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.deity,
                    fontFamily = MarcellusFontFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTerracotta
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = onWhatsAppBook,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SacredAmberContainer,
                        contentColor = SacredTerracottaDark
                    ),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Book / Offer Seva",
                        fontFamily = MuktaFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = entry.text,
            fontFamily = MuktaFontFamily,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            color = SacredTextDark
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Sacred Offerings & Dravyas (సమర్పణలు):",
            fontFamily = MuktaFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = SacredTextMuted
        )

        Spacer(modifier = Modifier.height(6.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            entry.offerings.forEach { offering ->
                Surface(
                    color = TempleCream,
                    shape = RoundedCornerShape(8.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(SacredBorder, Color.White))
                    )
                ) {
                    Text(
                        text = offering,
                        fontFamily = MuktaFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = SacredTextDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}
