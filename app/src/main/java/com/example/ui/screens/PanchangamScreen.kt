package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PanchangamCalculator
import com.example.data.TempleRepository
import com.example.ui.components.GarlandTicker
import com.example.ui.theme.*
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanchangamScreen() {
    val context = LocalContext.current
    var selectedCalendar by remember { mutableStateOf(Calendar.getInstance()) }
    var showDatePicker by remember { mutableStateOf(false) }

    val panchangam = remember(selectedCalendar) {
        PanchangamCalculator.calculatePanchangam(selectedCalendar)
    }

    val isToday = remember(selectedCalendar) {
        val now = Calendar.getInstance()
        now.get(Calendar.YEAR) == selectedCalendar.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == selectedCalendar.get(Calendar.DAY_OF_YEAR)
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedCalendar.timeInMillis
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val cal = Calendar.getInstance()
                            cal.timeInMillis = millis
                            selectedCalendar = cal
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Select Date", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", fontFamily = MuktaFontFamily)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("panchangam_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        item {
            GarlandTicker()
        }

        // Header Banner
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
                        text = "DAILY VEDIC PANCHANGAM",
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
                    text = "Daily Temple Panchangam",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "శ్రీ కృష్ణ సాయి ధ్యాన మందిర నిత్య పంచాంగం",
                    fontFamily = MuktaFontFamily,
                    fontSize = 14.sp,
                    color = Color(0xFFFCD34D)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tithi, Nakshatra, Yoga, Karana, Abhijit Muhurtham, and Rahu Kalam calculated for Puritigadda, Andhra Pradesh.",
                    fontFamily = MuktaFontFamily,
                    fontSize = 13.sp,
                    color = Color(0xFFF5EFE3)
                )
            }
        }

        // Date Navigator Controls
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .testTag("date_navigator_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TempleSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val prev = selectedCalendar.clone() as Calendar
                            prev.add(Calendar.DAY_OF_YEAR, -1)
                            selectedCalendar = prev
                        },
                        modifier = Modifier.testTag("prev_day_btn")
                    ) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Day", tint = SacredTerracotta)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = panchangam.dateDisplay,
                            fontFamily = MarcellusFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTerracottaDark,
                            textAlign = TextAlign.Center
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = panchangam.dayOfWeekTe,
                                fontFamily = MuktaFontFamily,
                                fontSize = 12.sp,
                                color = SacredGoldDark
                            )
                            if (isToday) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = SacredAmberContainer,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "TODAY",
                                        fontFamily = MuktaFontFamily,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SacredTerracottaDark,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    Row {
                        IconButton(
                            onClick = { showDatePicker = true },
                            modifier = Modifier.testTag("calendar_picker_btn")
                        ) {
                            Icon(Icons.Default.CalendarMonth, contentDescription = "Pick Date", tint = SacredGoldDark)
                        }

                        IconButton(
                            onClick = {
                                val next = selectedCalendar.clone() as Calendar
                                next.add(Calendar.DAY_OF_YEAR, 1)
                                selectedCalendar = next
                            },
                            modifier = Modifier.testTag("next_day_btn")
                        ) {
                            Icon(Icons.Default.ChevronRight, contentDescription = "Next Day", tint = SacredTerracotta)
                        }
                    }
                }
            }
        }

        // Vedic Calendar Header (Samvatsara, Ayana, Ruthu, Masa, Sunrise & Sunset)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("vedic_calendar_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TempleSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Vedic Chronology & Celestial Overview",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    VedicInfoRow("సంవత్సరం (Year)", panchangam.samvatsara)
                    VedicInfoRow("ఆయనం & ఋతువు", "${panchangam.ayana}\n${panchangam.ruthu}")
                    VedicInfoRow("మాసం (Month)", panchangam.masa)
                    VedicInfoRow("పక్షం (Paksha)", panchangam.paksha)

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = SacredBorder.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(10.dp))

                    // Sunrise & Sunset Banner
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🌅", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("సూర్యోదయం (Sunrise)", fontFamily = MuktaFontFamily, fontSize = 11.sp, color = SacredTextMuted)
                                Text(panchangam.sunrise, fontFamily = MarcellusFontFamily, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredTerracottaDark)
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🌇", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("సూర్యాస్తమయం (Sunset)", fontFamily = MuktaFontFamily, fontSize = 11.sp, color = SacredTextMuted)
                                Text(panchangam.sunset, fontFamily = MarcellusFontFamily, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredTerracottaDark)
                            }
                        }
                    }
                }
            }
        }

        // The Five Limbs of Panchangam (పంచాంగం)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "The Five Limbs (పంచాంగం - పంచ అంగాలు)",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTerracottaDark
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PanchangamLimbCard(
                        limbNameEn = "Tithi (తిథి)",
                        namePrimary = panchangam.tithiTe,
                        nameSecondary = panchangam.tithi,
                        icon = "🌙",
                        modifier = Modifier.weight(1f)
                    )
                    PanchangamLimbCard(
                        limbNameEn = "Nakshatra (నక్షత్రం)",
                        namePrimary = panchangam.nakshatraTe,
                        nameSecondary = panchangam.nakshatra,
                        icon = "⭐",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PanchangamLimbCard(
                        limbNameEn = "Yoga (యోగం)",
                        namePrimary = panchangam.yogaTe,
                        nameSecondary = panchangam.yoga,
                        icon = "🧘",
                        modifier = Modifier.weight(1f)
                    )
                    PanchangamLimbCard(
                        limbNameEn = "Karana (కరణం)",
                        namePrimary = panchangam.karanaTe,
                        nameSecondary = panchangam.karana,
                        icon = "☸️",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Auspicious Timings Card (శుభ సమయాలు)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("auspicious_timings_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(SacredGreen, Color(0xFF86EFAC)))
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✨", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Auspicious Timings (శుభ సమయాలు)",
                            fontFamily = MarcellusFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF14532D)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    MuhurthaRow("అభిజిత్ ముహూర్తం (Abhijit Muhurtham)", panchangam.abhijitMuhurtham, isAuspicious = true)
                    MuhurthaRow("అమృత కాలం (Amrita Kalam)", panchangam.amritaKalam, isAuspicious = true)
                    MuhurthaRow("బ్రాహ్మీ ముహూర్తం (Brahma Muhurtham)", panchangam.brahmaMuhurtham, isAuspicious = true)
                }
            }
        }

        // Inauspicious Timings Card (రాహుకాలం & వర్జ్యం)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("inauspicious_timings_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(SacredGold, Color(0xFFFDE68A)))
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⚠️", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Inauspicious Slots (అశుభ సమయాలు)",
                            fontFamily = MarcellusFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredGoldDark
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    MuhurthaRow("రాహు కాలం (Rahu Kalam)", panchangam.rahuKalam, isAuspicious = false)
                    MuhurthaRow("యమగండం (Yama Gandam)", panchangam.yamaGandam, isAuspicious = false)
                    MuhurthaRow("గుళిక కాలం (Gulika Kalam)", panchangam.gulikaKalam, isAuspicious = false)
                    MuhurthaRow("దుర్ముహూర్తం (Durmuhurtham)", panchangam.durmuhurtham, isAuspicious = false)
                }
            }
        }

        // Temple Special Observance & Seva Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .testTag("special_observance_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SacredAmberContainer),
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
                        text = "🪔 Mandhiram Seva & Daily Observance",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )

                    panchangam.specialDayNote?.let { note ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = Color.White.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = note,
                                fontFamily = MuktaFontFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Recommended Seva for Today:",
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SacredTextMuted
                    )

                    Text(
                        text = panchangam.recommendedSeva,
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        color = SacredTextDark,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            val msg = "Jai Sai Ram. I want to inquire about seva for ${panchangam.dateDisplay} (${panchangam.tithiTe}, ${panchangam.nakshatraTe})."
                            val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=${Uri.encode(msg)}"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SacredTerracotta,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("book_panchangam_seva_btn")
                    ) {
                        Text(
                            text = "Inquire / Book Seva on WhatsApp",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PanchangamLimbCard(
    limbNameEn: String,
    namePrimary: String,
    nameSecondary: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TempleSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(icon, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = limbNameEn,
                    fontFamily = MuktaFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTextMuted
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = namePrimary,
                fontFamily = MarcellusFontFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = SacredTerracotta
            )
            Text(
                text = nameSecondary,
                fontFamily = MuktaFontFamily,
                fontSize = 11.sp,
                color = SacredTextDark.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun VedicInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontFamily = MuktaFontFamily,
            fontSize = 12.sp,
            color = SacredTextMuted,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            fontFamily = MuktaFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = SacredTextDark,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.6f)
        )
    }
}

@Composable
fun MuhurthaRow(label: String, time: String, isAuspicious: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = MuktaFontFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if (isAuspicious) Color(0xFF14532D) else SacredTextDark
        )
        Surface(
            color = if (isAuspicious) Color(0xFFDCFCE7) else Color(0xFFFEF3C7),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = time,
                fontFamily = MarcellusFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isAuspicious) Color(0xFF15803D) else SacredTerracottaDark,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}
