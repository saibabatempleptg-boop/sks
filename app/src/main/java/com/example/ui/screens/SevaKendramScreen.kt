package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.SevaKendramRepository
import com.example.data.TempleRepository
import com.example.data.firestore.DevoteeReservationRepository
import com.example.model.DevoteeReservation
import com.example.model.SevaItem
import com.example.ui.components.DevoteeAmenitiesCard
import com.example.ui.components.GarlandTicker
import com.example.ui.components.SevaReservationDialog
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SevaKendramScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val reservationRepo = remember { DevoteeReservationRepository(context) }
    val myReservations by reservationRepo.observeReservations()
        .collectAsStateWithLifecycle(initialValue = emptyList())

    var mainViewTab by remember { mutableStateOf(0) } // 0: Catalog, 1: My Reservations
    var selectedCategory by remember { mutableStateOf("All") }
    var reservingSeva by remember { mutableStateOf<SevaItem?>(null) }
    var deletingId by remember { mutableStateOf<String?>(null) }

    val categories = listOf(
        "All",
        "Prasadam & Nivedan",
        "Gotra Name Puja",
        "Daily Puja",
        "Wednesday Puja",
        "Thursday Puja",
        "Saturday Puja",
        "Full Moon Puja",
        "Monthly Puja",
        "Special Puja"
    )

    val filteredSevas = remember(selectedCategory) {
        if (selectedCategory == "All") {
            SevaKendramRepository.sevasList
        } else {
            SevaKendramRepository.sevasList.filter { it.category == selectedCategory }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("seva_kendram_screen"),
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
                            text = "PUJA & PRASADAM RESERVATIONS",
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
                        text = SevaKendramRepository.KENDRAM_TITLE,
                        fontFamily = MarcellusFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = SevaKendramRepository.KENDRAM_TITLE_TELUGU,
                        fontFamily = MuktaFontFamily,
                        fontSize = 14.sp,
                        color = Color(0xFFFCD34D)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Reserve sacred worship in your Gotra and family names: Daily Midday Nivedan Baba Prasadam, Santra Prasadam, Morning Prasadam, Guruvaram abhishek, and auspicious monthly homams.",
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        color = Color(0xFFF5EFE3)
                    )
                }
            }

            // Top Segmented Control (Catalog vs My Reservations)
            item {
                TabRow(
                    selectedTabIndex = mainViewTab,
                    containerColor = TempleSurface,
                    contentColor = SacredTerracotta,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Tab(
                        selected = mainViewTab == 0,
                        onClick = { mainViewTab = 0 },
                        text = {
                            Text(
                                "Seva Catalog (${SevaKendramRepository.sevasList.size})",
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier.testTag("tab_seva_catalog")
                    )
                    Tab(
                        selected = mainViewTab == 1,
                        onClick = { mainViewTab = 1 },
                        text = {
                            Text(
                                "My Bookings (${myReservations.size})",
                                fontFamily = MuktaFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier.testTag("tab_my_reservations")
                    )
                }
            }

            if (mainViewTab == 1) {
                // My Reservations Section (Connected to Firestore)
                if (myReservations.isEmpty()) {
                    item {
                        Surface(
                            color = TempleSurface,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("📜", fontSize = 40.sp)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "No Active Reservations",
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredTerracottaDark
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "You haven't reserved any pujas or prasadam yet. Browse our catalog and book Gotra Sankalpam, Midday Nivedan Prasadam, or Special Homams with instant cloud synchronization.",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 13.sp,
                                    color = SacredTextMuted,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Button(
                                    onClick = { mainViewTab = 0 },
                                    colors = ButtonDefaults.buttonColors(containerColor = SacredTerracotta),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Explore Seva Catalog", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                } else {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Your Confirmed Puja Bookings:",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark
                            )
                            Surface(
                                color = Color(0xFFDCFCE7),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "☁️ Firebase Cloud Sync",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    items(myReservations, key = { it.id }) { reservation ->
                        DevoteeReservationCard(
                            reservation = reservation,
                            isDeleting = deletingId == reservation.id,
                            onDelete = {
                                deletingId = reservation.id
                                coroutineScope.launch {
                                    try {
                                        reservationRepo.deleteReservation(reservation.id)
                                    } finally {
                                        deletingId = null
                                    }
                                }
                            },
                            onSendWhatsApp = {
                                val text = "*🙏 Sri Krishna Sai Mandhiram - Booking Confirmation*\n" +
                                        "Seva: ${reservation.sevaTitle}\n" +
                                        "Devotee: ${reservation.devoteeName}\n" +
                                        "Phone: ${reservation.devoteePhone}\n" +
                                        "Gotram: ${reservation.gotram}\n" +
                                        "Date: ${reservation.preferredDate}\n" +
                                        "Special Prayer: ${reservation.specialPrayer}\n" +
                                        "Status: ${reservation.status}\n" +
                                        "Om Sai Ram 🙏"
                                val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=${Uri.encode(text)}"
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            }
                        )
                    }
                }
            } else {
                // Catalog Section
                // Quick Highlight Chips for Requested Primary Sevas
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Featured Prasadam & Gotra Reservations:",
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
                            FeaturedSevaBadge(
                                icon = "🍚",
                                title = "Midday Baba Prasadam",
                                onClick = {
                                    reservingSeva = SevaKendramRepository.sevasList.find { it.id == "daily_midday_prasadam" }
                                }
                            )
                            FeaturedSevaBadge(
                                icon = "📜",
                                title = "Gotra Name Puja",
                                onClick = {
                                    reservingSeva = SevaKendramRepository.sevasList.find { it.id == "gotra_puja" }
                                }
                            )
                            FeaturedSevaBadge(
                                icon = "🍊",
                                title = "Santra Prasadam",
                                onClick = {
                                    reservingSeva = SevaKendramRepository.sevasList.find { it.id == "santra_prasadam" }
                                }
                            )
                            FeaturedSevaBadge(
                                icon = "🥣",
                                title = "Morning Prasadam",
                                onClick = {
                                    reservingSeva = SevaKendramRepository.sevasList.find { it.id == "morning_prasadam" }
                                }
                            )
                            FeaturedSevaBadge(
                                icon = "🪔",
                                title = "Thursday Guruvaram",
                                onClick = {
                                    reservingSeva = SevaKendramRepository.sevasList.find { it.id == "thursday_puja" }
                                }
                            )
                            FeaturedSevaBadge(
                                icon = "🌕",
                                title = "Full Moon Puja",
                                onClick = {
                                    reservingSeva = SevaKendramRepository.sevasList.find { it.id == "full_moon_puja" }
                                }
                            )
                        }
                    }
                }

                // 400 to 500 Devotee Capacity Card
                item {
                    DevoteeAmenitiesCard()
                }

                // Category Filter Pills
                item {
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        Text(
                            text = "Filter Seva Types",
                            fontFamily = MuktaFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTextDark,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            items(categories) { category ->
                                val isSelected = category == selectedCategory
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedCategory = category },
                                    label = {
                                        Text(
                                            text = category,
                                            fontFamily = MuktaFontFamily,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 12.sp
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SacredTerracotta,
                                        selectedLabelColor = Color.White
                                    ),
                                    modifier = Modifier.testTag("filter_chip_${category.lowercase().replace(" ", "_")}")
                                )
                            }
                        }
                    }
                }

                // Seva Items List
                items(filteredSevas, key = { it.id }) { seva ->
                    SevaItemCard(
                        seva = seva,
                        onReserve = { reservingSeva = seva }
                    )
                }
            }
        }

        // Active Reservation Dialog
        reservingSeva?.let { seva ->
            SevaReservationDialog(
                seva = seva,
                onDismiss = { reservingSeva = null },
                onReservationSaved = {
                    mainViewTab = 1
                }
            )
        }
    }
}

@Composable
fun DevoteeReservationCard(
    reservation: DevoteeReservation,
    isDeleting: Boolean,
    onDelete: () -> Unit,
    onSendWhatsApp: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("reservation_card_${reservation.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TempleSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reservation.sevaTitle,
                    fontFamily = MarcellusFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTerracottaDark,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = Color(0xFFDCFCE7),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = reservation.status,
                        fontFamily = MuktaFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF15803D),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Devotee: ${reservation.devoteeName} • Phone: ${reservation.devoteePhone}",
                fontFamily = MuktaFontFamily,
                fontSize = 13.sp,
                color = SacredTextDark
            )

            Text(
                text = "Gotram: ${reservation.gotram}${if (reservation.nakshatram.isNotBlank()) " • Nakshatram: ${reservation.nakshatram}" else ""}",
                fontFamily = MuktaFontFamily,
                fontSize = 13.sp,
                color = SacredTextDark
            )

            Text(
                text = "Scheduled Date: ${reservation.preferredDate}",
                fontFamily = MuktaFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = SacredGoldDark
            )

            if (reservation.specialPrayer.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Special Prayer: ${reservation.specialPrayer}",
                    fontFamily = MuktaFontFamily,
                    fontSize = 12.sp,
                    color = SacredTextMuted
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onDelete,
                    enabled = !isDeleting,
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    if (isDeleting) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                    } else {
                        Text("Cancel Booking", fontFamily = MuktaFontFamily, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilledTonalButton(
                    onClick = onSendWhatsApp,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(containerColor = SacredAmberContainer)
                ) {
                    Text("WhatsApp Copy", fontFamily = MuktaFontFamily, fontSize = 12.sp, color = SacredTerracottaDark)
                }
            }
        }
    }
}

@Composable
fun FeaturedSevaBadge(
    icon: String,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = SacredAmberContainer,
        shape = RoundedCornerShape(20.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = Brush.horizontalGradient(listOf(SacredGold, SacredGoldLight))
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 13.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                fontFamily = MuktaFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SacredTerracottaDark
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SevaItemCard(
    seva: SevaItem,
    onReserve: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("seva_card_${seva.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TempleSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Surface(
                    shape = CircleShape,
                    color = SacredAmberContainer,
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(seva.icon, fontSize = 22.sp)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = SacredTerracotta.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = seva.category.uppercase(),
                                fontFamily = MuktaFontFamily,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracotta,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        if (seva.isPrasadam) {
                            Surface(
                                color = Color(0xFFDCFCE7),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "PRASADAM",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = seva.name,
                        fontFamily = MarcellusFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )

                    Text(
                        text = seva.nameTe,
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        color = SacredGoldDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                color = TempleCream,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("⏰", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = seva.timing,
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SacredTextDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = seva.description,
                fontFamily = MuktaFontFamily,
                fontSize = 12.sp,
                color = SacredTextDark,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = seva.descriptionTe,
                fontFamily = MuktaFontFamily,
                fontSize = 11.sp,
                color = SacredTextMuted,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Offerings pills
            Text(
                text = "Sacred Offerings Included:",
                fontFamily = MuktaFontFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SacredTextMuted
            )

            Spacer(modifier = Modifier.height(4.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                seva.offerings.forEach { offering ->
                    Surface(
                        color = SacredAmberContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = offering,
                            fontFamily = MuktaFontFamily,
                            fontSize = 10.sp,
                            color = SacredTextDark,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onReserve,
                colors = ButtonDefaults.buttonColors(containerColor = SacredTerracotta),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("reserve_btn_${seva.id}")
            ) {
                Text(
                    text = "Reserve Seva (పూజను బుక్ చేయండి)",
                    fontFamily = MuktaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }
    }
}
