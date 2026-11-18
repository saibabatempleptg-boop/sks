package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.SevaKendramRepository
import com.example.data.TempleRepository
import com.example.data.firestore.DevoteeReservationRepository
import com.example.model.DevoteeReservation
import com.example.model.SevaItem
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun SevaReservationDialog(
    seva: SevaItem,
    onDismiss: () -> Unit,
    onReservationSaved: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val reservationRepository = remember { DevoteeReservationRepository(context) }

    var devoteeName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gotram by remember { mutableStateOf("") }
    var nakshatram by remember { mutableStateOf("") }
    var familyMembers by remember { mutableStateOf("") }
    var preferredDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var isSubmitting by remember { mutableStateOf(false) }
    var isSubmittedSuccess by remember { mutableStateOf(false) }
    var submitError by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
                .testTag("seva_reservation_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = TempleSurface,
            tonalElevation = 6.dp
        ) {
            if (isSubmittedSuccess) {
                // Success State View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color(0xFF16A34A),
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Seva Reserved Successfully!",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "పూజా సంకల్పం విజయవంతంగా నమోదు చేయబడింది",
                        fontFamily = MuktaFontFamily,
                        fontSize = 14.sp,
                        color = SacredGoldDark,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        color = SacredAmberContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = seva.name,
                                fontFamily = MarcellusFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Devotee: $devoteeName • Gotram: ${if (gotram.isNotBlank()) gotram else "Family"}",
                                fontFamily = MuktaFontFamily,
                                fontSize = 13.sp,
                                color = SacredTextDark
                            )
                            Text(
                                text = "Date: ${if (preferredDate.isNotBlank()) preferredDate else "Auspicious Day"}",
                                fontFamily = MuktaFontFamily,
                                fontSize = 13.sp,
                                color = SacredTextDark
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "✓ Stored in Temple Firebase Cloud Database",
                                fontFamily = MuktaFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF15803D)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Optional WhatsApp forwarding button
                    Button(
                        onClick = {
                            val formattedMsg = SevaKendramRepository.formatWhatsAppReservationMessage(
                                devoteeName = devoteeName,
                                phone = phone,
                                gotram = gotram,
                                nakshatram = nakshatram,
                                familyMembers = familyMembers,
                                preferredDate = preferredDate,
                                seva = seva,
                                notes = notes
                            )
                            val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=${Uri.encode(formattedMsg)}"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            onReservationSaved()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("whatsapp_confirm_btn")
                    ) {
                        Text(
                            text = "Send Copy to Mandhiram Office (WhatsApp)",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = {
                            onReservationSaved()
                            onDismiss()
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("done_reservation_btn")
                    ) {
                        Text(
                            text = "View in My Reservations",
                            fontFamily = MuktaFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                // Input Form
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    // Header with Close
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Seva Reservation",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark
                            )
                            Text(
                                text = "శ్రీ కృష్ణ సాయి సేవా కేంద్రం",
                                fontFamily = MuktaFontFamily,
                                fontSize = 12.sp,
                                color = SacredGoldDark
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("close_reservation_dialog_btn")
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = SacredTextDark)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Seva Summary Banner
                    Surface(
                        color = SacredAmberContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = seva.icon, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = seva.name,
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredTerracottaDark
                                )
                                Text(
                                    text = "⏰ ${seva.timing}",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 11.sp,
                                    color = SacredTextDark
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (submitError != null) {
                        Surface(
                            color = MaterialTheme.colorScheme.errorContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = submitError!!,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Form Fields
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = devoteeName,
                            onValueChange = { devoteeName = it },
                            label = { Text("Devotee Name (భక్తుని పేరు) *") },
                            placeholder = { Text("e.g. Rama Rao") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_seva_devotee_name"),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Mobile Number (ఫోన్ నెంబర్) *") },
                            placeholder = { Text("10-digit number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_seva_phone"),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = gotram,
                                onValueChange = { gotram = it },
                                label = { Text("Gotram (గోత్రం)") },
                                placeholder = { Text("e.g. Kashyapa") },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("input_seva_gotram"),
                                shape = RoundedCornerShape(10.dp)
                            )

                            OutlinedTextField(
                                value = nakshatram,
                                onValueChange = { nakshatram = it },
                                label = { Text("Nakshatram (నక్షత్రం)") },
                                placeholder = { Text("e.g. Rohini") },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("input_seva_nakshatram"),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        OutlinedTextField(
                            value = familyMembers,
                            onValueChange = { familyMembers = it },
                            label = { Text("Family Members for Sankalpam") },
                            placeholder = { Text("e.g. Self, Spouse & Children") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_seva_family"),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = preferredDate,
                            onValueChange = { preferredDate = it },
                            label = { Text("Preferred Date / Tithi (తేదీ)") },
                            placeholder = { Text("e.g. 2026-11-20 or Upcoming Thursday") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_seva_date"),
                            shape = RoundedCornerShape(10.dp)
                        )

                        // Quick Date Chips
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            SuggestionChip(
                                onClick = { preferredDate = "Today / ఈరోజు" },
                                label = { Text("Today", fontSize = 11.sp) }
                            )
                            SuggestionChip(
                                onClick = { preferredDate = "Upcoming Thursday (గురువారం)" },
                                label = { Text("Thursday", fontSize = 11.sp) }
                            )
                            SuggestionChip(
                                onClick = { preferredDate = "Upcoming Purnima (పౌర్ణమి)" },
                                label = { Text("Purnima", fontSize = 11.sp) }
                            )
                        }

                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            label = { Text("Special Prayer / Sankalpam Request") },
                            placeholder = { Text("Birthday, wedding anniversary, health prayer, peace...") },
                            minLines = 2,
                            maxLines = 4,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_seva_notes"),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            isSubmitting = true
                            submitError = null
                            coroutineScope.launch {
                                try {
                                    val reservation = DevoteeReservation(
                                        sevaId = seva.id,
                                        sevaTitle = seva.name,
                                        devoteeName = devoteeName.trim(),
                                        devoteePhone = phone.trim(),
                                        gotram = if (gotram.isNotBlank()) gotram.trim() else "Devotee Gotram",
                                        nakshatram = nakshatram.trim(),
                                        preferredDate = if (preferredDate.isNotBlank()) preferredDate.trim() else "Auspicious Date",
                                        specialPrayer = if (notes.isNotBlank()) notes.trim() else if (familyMembers.isNotBlank()) "Family: ${familyMembers.trim()}" else ""
                                    )
                                    reservationRepository.createReservation(reservation)
                                    isSubmitting = false
                                    isSubmittedSuccess = true
                                } catch (e: Exception) {
                                    isSubmitting = false
                                    submitError = e.localizedMessage ?: "Failed to save reservation to Firebase"
                                }
                            }
                        },
                        enabled = devoteeName.isNotBlank() && phone.isNotBlank() && !isSubmitting,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SacredTerracotta,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_seva_reservation_btn")
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Confirm & Save Seva Reservation",
                                fontFamily = MuktaFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
