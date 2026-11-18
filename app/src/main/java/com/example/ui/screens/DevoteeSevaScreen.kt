package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SevaRepository
import com.example.data.TempleRepository
import com.example.model.ContributionRecord
import com.example.model.PaymentMethod
import com.example.model.SevaService
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun DevoteeSevaScreen() {
    val context = LocalContext.current
    var selectedSubTab by remember { mutableIntStateOf(0) } // 0: Online Sevas, 1: Visit & Contact

    // Dialog States for Mock Payment Flow
    var activeSevaForContribution by remember { mutableStateOf<SevaService?>(null) }
    var pendingPaymentData by remember {
        mutableStateOf<Triple<Int, SevaService, PaymentMethod>?>(null)
    }
    var currentReceiptRecord by remember { mutableStateOf<ContributionRecord?>(null) }

    val contributionHistory by SevaRepository.contributionHistory.collectAsState()

    // Form states for Visit / inquiry form
    var inquiryName by remember { mutableStateOf("") }
    var inquiryPhone by remember { mutableStateOf("") }
    var inquiryMessage by remember { mutableStateOf("") }
    var selectedSevaType by remember { mutableStateOf("General Inquiry") }
    var isSubmitted by remember { mutableStateOf(false) }

    val sevaOptions = listOf(
        "General Inquiry",
        "Lakshmi Ganapathi Homam",
        "Daily Abhishek Seva",
        "Thursday Guruvaram Vastram",
        "Subhavedika Hall Booking",
        "Annadanam Contribution"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("devotee_seva_screen"),
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
                            text = "DEVOTEE SEVA & TEMPLE CONTRIBUTIONS",
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
                        text = "Devotee Seva & Offerings",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "ఆలయ సేవలు, విరాళాలు & సందర్శన వివరాలు",
                        fontFamily = MuktaFontFamily,
                        fontSize = 14.sp,
                        color = Color(0xFFFCD34D)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sponsor Annadanam, sacred abhishek, homams, and temple maintenance with instant digital e-receipts and Baba's blessings.",
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        color = Color(0xFFF5EFE3)
                    )
                }
            }

            // Sub-Tab Switcher
            item {
                Surface(
                    color = TempleCream,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TabRow(
                        selectedTabIndex = selectedSubTab,
                        containerColor = TempleCream,
                        contentColor = SacredTerracotta,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("seva_subtab_row")
                    ) {
                        Tab(
                            selected = selectedSubTab == 0,
                            onClick = { selectedSubTab = 0 },
                            text = {
                                Text(
                                    text = "🪔 Sevas & Contributions",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 13.sp,
                                    fontWeight = if (selectedSubTab == 0) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            modifier = Modifier.testTag("tab_online_sevas")
                        )
                        Tab(
                            selected = selectedSubTab == 1,
                            onClick = { selectedSubTab = 1 },
                            text = {
                                Text(
                                    text = "📍 Visit & Facilities",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 13.sp,
                                    fontWeight = if (selectedSubTab == 1) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            modifier = Modifier.testTag("tab_visit_facilities")
                        )
                    }
                }
            }

            // TAB 0: ONLINE SEVAS & CONTRIBUTIONS
            if (selectedSubTab == 0) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Temple Sevas & Maintenance Activities",
                            fontFamily = MarcellusFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTerracottaDark
                        )
                        Text(
                            text = "Select any consecrated seva below to participate and support temple upkeep.",
                            fontFamily = MuktaFontFamily,
                            fontSize = 12.sp,
                            color = SacredTextMuted
                        )
                    }
                }

                // List of Sevas
                items(SevaRepository.sevaServices, key = { it.id }) { seva ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("seva_card_${seva.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = TempleSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = Brush.horizontalGradient(listOf(SacredBorder, Color.White))
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(SacredAmberContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = seva.icon, fontSize = 22.sp)
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = seva.title,
                                        fontFamily = MarcellusFontFamily,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SacredTerracottaDark
                                    )
                                    Text(
                                        text = seva.titleTe,
                                        fontFamily = MuktaFontFamily,
                                        fontSize = 12.sp,
                                        color = SacredGoldDark
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = seva.description,
                                fontFamily = MuktaFontFamily,
                                fontSize = 13.sp,
                                color = SacredTextDark,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    seva.suggestedAmounts.take(3).forEach { amt ->
                                        Surface(
                                            color = TempleCream,
                                            shape = RoundedCornerShape(8.dp),
                                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                                brush = androidx.compose.ui.graphics.SolidColor(SacredBorder)
                                            )
                                        ) {
                                            Text(
                                                text = "₹$amt",
                                                fontFamily = MuktaFontFamily,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SacredTerracotta,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                }

                                Button(
                                    onClick = { activeSevaForContribution = seva },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SacredTerracotta,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                    modifier = Modifier.testTag("contribute_btn_${seva.id}")
                                ) {
                                    Text(
                                        text = "Offer Seva ➔",
                                        fontFamily = MuktaFontFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // Recent Seva Contributions Section
                if (contributionHistory.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Text(
                                text = "Recent Contributions & E-Receipts (నా రసీదులు)",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            contributionHistory.forEach { record ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { currentReceiptRecord = record }
                                        .testTag("history_item_${record.receiptId}"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = TempleSurface),
                                    border = ButtonDefaults.outlinedButtonBorder.copy(
                                        brush = androidx.compose.ui.graphics.SolidColor(SacredBorder)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = record.sevaTitle,
                                                fontFamily = MarcellusFontFamily,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SacredTerracottaDark
                                            )
                                            Text(
                                                text = "${record.receiptId} • ${record.devoteeName}",
                                                fontFamily = MuktaFontFamily,
                                                fontSize = 11.sp,
                                                color = SacredTextMuted
                                            )
                                            Text(
                                                text = SevaRepository.formatDate(record.timestampMillis),
                                                fontFamily = MuktaFontFamily,
                                                fontSize = 10.sp,
                                                color = SacredTextMuted
                                            )
                                        }

                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(
                                                text = "₹${record.amount}",
                                                fontFamily = MarcellusFontFamily,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SacredGreen
                                            )
                                            Text(
                                                text = "View E-Receipt ➔",
                                                fontFamily = MuktaFontFamily,
                                                fontSize = 11.sp,
                                                color = SacredTerracotta,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAB 1: VISIT, CAPACITY & CONTACT
            if (selectedSubTab == 1) {
                // Location & Directions Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .testTag("location_info_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = TempleSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(SacredAmberContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("📍", fontSize = 18.sp)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Mandhiram Location & Address",
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredTerracottaDark
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = TempleRepository.TEMPLE_ADDRESS,
                                fontFamily = MuktaFontFamily,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SacredTextDark,
                                lineHeight = 22.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Plus Code: 4VFQ+P77, Puritigadda, Andhra Pradesh",
                                fontFamily = MuktaFontFamily,
                                fontSize = 13.sp,
                                color = SacredTextMuted
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TempleRepository.MAPS_URL))
                                        context.startActivity(intent)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SacredTerracotta,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("open_maps_btn")
                                ) {
                                    Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Google Maps", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${TempleRepository.TEMPLE_PHONE}"))
                                        context.startActivity(intent)
                                    },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SacredTerracotta),
                                    border = ButtonDefaults.outlinedButtonBorder.copy(
                                        brush = androidx.compose.ui.graphics.SolidColor(SacredTerracotta)
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("call_temple_btn")
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Call Mandhiram", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                // WhatsApp Direct Connect
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("whatsapp_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SacredGreenLight),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(SacredGreen)
                        )
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
                                    .background(SacredGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("💬", fontSize = 20.sp)
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "WhatsApp Mandhiram Office",
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF14532D)
                                )
                                Text(
                                    text = "Direct chat for pooja bookings, seva timings & questions",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    color = Color(0xFF166534)
                                )
                            }

                            Button(
                                onClick = {
                                    val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=Jai%20Sai%20Ram"
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SacredGreen,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(14.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("chat_whatsapp_btn")
                            ) {
                                Text("Chat", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Pilgrim Capacity & Devotee Amenities Section (400 to 500 people with ease)
                item {
                    DevoteeAmenitiesCard(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                // Devotee Message / Seva Booking Form
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .testTag("message_form_card"),
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
                                text = "Send us a message / Offline Seva Inquiry",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark
                            )

                            Text(
                                text = "Share your name and details — the mandhiram team will receive your message and connect via WhatsApp.",
                                fontFamily = MuktaFontFamily,
                                fontSize = 12.sp,
                                color = SacredTextMuted
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            AnimatedVisibility(visible = isSubmitted) {
                                Surface(
                                    color = SacredAmberContainer,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "ధన్యవాదాలు — Thank you!",
                                            fontFamily = MarcellusFontFamily,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SacredTerracottaDark
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Your request has been drafted. WhatsApp has opened so you can send it directly to the mandhiram.",
                                            fontFamily = MuktaFontFamily,
                                            fontSize = 13.sp,
                                            color = SacredTextDark,
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        TextButton(
                                            onClick = { isSubmitted = false },
                                            modifier = Modifier.testTag("send_another_msg_btn")
                                        ) {
                                            Text("Send Another Request", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }

                            if (!isSubmitted) {
                                OutlinedTextField(
                                    value = inquiryName,
                                    onValueChange = { inquiryName = it },
                                    label = { Text("Your Full Name (పేరు)") },
                                    placeholder = { Text("e.g. Rama Rao") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_devotee_name"),
                                    singleLine = true,
                                    shape = RoundedCornerShape(10.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = inquiryPhone,
                                    onValueChange = { inquiryPhone = it },
                                    label = { Text("Mobile Number (ఫోన్ నంబర్)") },
                                    placeholder = { Text("+91 98765 43210") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_devotee_phone"),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    singleLine = true,
                                    shape = RoundedCornerShape(10.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                var expanded by remember { mutableStateOf(false) }
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedButton(
                                        onClick = { expanded = true },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(selectedSevaType, fontFamily = MuktaFontFamily, color = SacredTextDark)
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        sevaOptions.forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(option, fontFamily = MuktaFontFamily) },
                                                onClick = {
                                                    selectedSevaType = option
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = inquiryMessage,
                                    onValueChange = { inquiryMessage = it },
                                    label = { Text("Your Message or Gotram / Nakshatram") },
                                    placeholder = { Text("Please write your details or prayer request...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_devotee_message"),
                                    minLines = 3,
                                    maxLines = 5,
                                    shape = RoundedCornerShape(10.dp)
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                Button(
                                    onClick = {
                                        if (inquiryName.isNotBlank()) {
                                            isSubmitted = true
                                            val fullMsg = """
                                                *Devotee Seva Inquiry*
                                                Mandhiram: Sri Krishna Sai Dhyana Mandhiram, Puritigadda
                                                
                                                *Name:* $inquiryName
                                                *Phone:* $inquiryPhone
                                                *Seva:* $selectedSevaType
                                                *Details:* $inquiryMessage
                                                
                                                Om Sai Ram 🙏
                                            """.trimIndent()
                                            val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=${Uri.encode(fullMsg)}"
                                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                        }
                                    },
                                    enabled = inquiryName.isNotBlank(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SacredTerracotta,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("submit_message_btn")
                                ) {
                                    Text(
                                        text = "Submit & Open on WhatsApp",
                                        fontFamily = MuktaFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Social Media & Mandiram Channels
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Follow Sri Krishna Sai Mandhiram",
                            fontFamily = MarcellusFontFamily,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTerracottaDark
                        )

                        Text(
                            text = "Connect on official community channels for live darshan, bhajans & announcements",
                            fontFamily = MuktaFontFamily,
                            fontSize = 12.sp,
                            color = SacredTextMuted
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SocialChannelCard(
                                icon = "📢",
                                title = "WhatsApp",
                                subtitle = "Channel",
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TempleRepository.WHATSAPP_CHANNEL_URL)))
                                }
                            )
                            SocialChannelCard(
                                icon = "▶️",
                                title = "YouTube",
                                subtitle = "@puritigadda",
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TempleRepository.YOUTUBE_URL)))
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SocialChannelCard(
                                icon = "📷",
                                title = "Instagram",
                                subtitle = "@krishnasaimandhiram",
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TempleRepository.INSTAGRAM_URL)))
                                }
                            )
                            SocialChannelCard(
                                icon = "📘",
                                title = "Facebook",
                                subtitle = "Official Page",
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TempleRepository.FACEBOOK_PAGE_URL)))
                                }
                            )
                        }
                    }
                }

                // Temple & Developer Attribution
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ఓం సాయి రామ్",
                            fontFamily = MarcellusFontFamily,
                            fontSize = 16.sp,
                            color = SacredTerracotta
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sri Krishna Sai Dhyana Mandhiram • Puritigadda",
                            fontFamily = MuktaFontFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = SacredTextDark
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Website Creator Vishnu Dutta",
                            fontFamily = MuktaFontFamily,
                            fontSize = 11.sp,
                            color = SacredTextMuted
                        )
                    }
                }
            }
        }

        // 1. Contribution Input & Details Dialog
        activeSevaForContribution?.let { seva ->
            ContributionDialog(
                seva = seva,
                onDismiss = { activeSevaForContribution = null },
                onProceedToPayment = { amount, name, gotram, nakshatram, phone, email, occasion, method ->
                    activeSevaForContribution = null
                    // Save and initiate mock payment
                    val record = SevaRepository.recordContribution(
                        seva = seva,
                        amount = amount,
                        devoteeName = name,
                        gotram = gotram,
                        nakshatram = nakshatram,
                        phone = phone,
                        email = email,
                        occasion = occasion,
                        paymentMethod = method
                    )
                    pendingPaymentData = Triple(amount, seva, method)
                    currentReceiptRecord = record
                }
            )
        }

        // 2. Mock Payment Gateway Processing Simulation
        pendingPaymentData?.let { (amount, seva, method) ->
            MockPaymentProcessingDialog(
                amount = amount,
                sevaTitle = seva.title,
                method = method,
                onPaymentSuccess = {
                    pendingPaymentData = null
                    // currentReceiptRecord will now display the receipt
                },
                onCancel = {
                    pendingPaymentData = null
                    currentReceiptRecord = null
                }
            )
        }

        // 3. Consecrated E-Receipt Dialog (Shows after payment success or when tapping history)
        if (pendingPaymentData == null && currentReceiptRecord != null) {
            ContributionReceiptDialog(
                record = currentReceiptRecord!!,
                onDismiss = { currentReceiptRecord = null }
            )
        }
    }
}

@Composable
fun SocialChannelCard(
    icon: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TempleSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontFamily = MarcellusFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTerracottaDark
                )
                Text(
                    text = subtitle,
                    fontFamily = MuktaFontFamily,
                    fontSize = 11.sp,
                    color = SacredTextMuted,
                    maxLines = 1
                )
            }
        }
    }
}
