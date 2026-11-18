package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.PaymentMethod
import com.example.model.SevaService
import com.example.ui.theme.*

@Composable
fun ContributionDialog(
    seva: SevaService,
    onDismiss: () -> Unit,
    onProceedToPayment: (
        amount: Int,
        name: String,
        gotram: String,
        nakshatram: String,
        phone: String,
        email: String,
        occasion: String,
        method: PaymentMethod
    ) -> Unit
) {
    var selectedAmount by remember { mutableIntStateOf(seva.suggestedAmounts.firstOrNull() ?: 501) }
    var customAmountText by remember { mutableStateOf("") }
    var isCustomAmount by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gotram by remember { mutableStateOf("") }
    var nakshatram by remember { mutableStateOf("") }
    var occasion by remember { mutableStateOf("Family Well-being & Peace") }
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.UPI_GPAY) }

    val effectiveAmount = if (isCustomAmount) {
        customAmountText.toIntOrNull() ?: seva.minimumAmount
    } else {
        selectedAmount
    }

    val occasions = listOf(
        "Family Well-being & Peace",
        "Birthday Celebrations",
        "Wedding Anniversary",
        "In Memory of Elders",
        "New Endeavour / Success",
        "Special Prayer / Sankalpam"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.9f)
                .clip(RoundedCornerShape(20.dp))
                .testTag("contribution_dialog"),
            color = TempleSurface,
            tonalElevation = 6.dp
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header Bar
                Surface(
                    color = SacredTerracottaDark,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(seva.icon, fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = seva.title,
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = seva.titleTe,
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    color = Color(0xFFFDE68A)
                                )
                            }
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("close_contribution_dialog_btn")
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    }
                }

                // Scrollable Form Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Seva Description
                    Text(
                        text = seva.description,
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        color = SacredTextDark,
                        lineHeight = 18.sp
                    )

                    // 1. Select Amount
                    Text(
                        text = "1. Select Seva Amount (విరాళం మొత్తం):",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracotta
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        seva.suggestedAmounts.forEach { amt ->
                            val isSelected = !isCustomAmount && selectedAmount == amt
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) SacredTerracotta else TempleCream,
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = androidx.compose.ui.graphics.SolidColor(
                                        if (isSelected) SacredTerracotta else SacredBorder
                                    )
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        isCustomAmount = false
                                        selectedAmount = amt
                                    }
                            ) {
                                Box(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "₹$amt",
                                        fontFamily = MarcellusFontFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else SacredTerracottaDark
                                    )
                                }
                            }
                        }
                    }

                    // Custom Amount Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isCustomAmount,
                            onCheckedChange = { isCustomAmount = it },
                            modifier = Modifier.testTag("custom_amount_checkbox")
                        )
                        Text(
                            text = "Enter other amount (ఇతర మొత్తం)",
                            fontFamily = MuktaFontFamily,
                            fontSize = 13.sp,
                            color = SacredTextDark
                        )
                    }

                    if (isCustomAmount) {
                        OutlinedTextField(
                            value = customAmountText,
                            onValueChange = { customAmountText = it.filter { ch -> ch.isDigit() } },
                            label = { Text("Contribution Amount (₹)") },
                            placeholder = { Text("Min ₹${seva.minimumAmount}") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("custom_amount_input"),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Divider(color = SacredBorder.copy(alpha = 0.5f))

                    // 2. Devotee Sankalpam Details
                    Text(
                        text = "2. Devotee Sankalpam Details (భక్తుని వివరాలు):",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracotta
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name (భక్తుని పేరు) *") },
                        placeholder = { Text("e.g. Ramesh Chandra") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_seva_name"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Mobile Number *") },
                            placeholder = { Text("+91 98765 43210") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_seva_phone"),
                            shape = RoundedCornerShape(10.dp)
                        )

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
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
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

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email (for e-receipt)") },
                            placeholder = { Text("optional") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_seva_email"),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    // Occasion
                    var occasionExpanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { occasionExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Sankalpam: $occasion", fontFamily = MuktaFontFamily, fontSize = 13.sp, color = SacredTextDark)
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                        DropdownMenu(
                            expanded = occasionExpanded,
                            onDismissRequest = { occasionExpanded = false }
                        ) {
                            occasions.forEach { occ ->
                                DropdownMenuItem(
                                    text = { Text(occ, fontFamily = MuktaFontFamily) },
                                    onClick = {
                                        occasion = occ
                                        occasionExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Divider(color = SacredBorder.copy(alpha = 0.5f))

                    // 3. Payment Method
                    Text(
                        text = "3. Payment Method (చెల్లింపు విధానం):",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracotta
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        PaymentMethod.values().forEach { method ->
                            val isSelected = selectedPaymentMethod == method
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) SacredAmberContainer else TempleCream,
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = androidx.compose.ui.graphics.SolidColor(
                                        if (isSelected) SacredGold else SacredBorder
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedPaymentMethod = method }
                                    .testTag("method_${method.name}")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedPaymentMethod = method }
                                    )
                                    Text(method.icon, fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = method.title,
                                            fontFamily = MuktaFontFamily,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SacredTerracottaDark
                                        )
                                        Text(
                                            text = method.description,
                                            fontFamily = MuktaFontFamily,
                                            fontSize = 11.sp,
                                            color = SacredTextMuted
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Bottom Action Bar
                Surface(
                    color = TempleCream,
                    tonalElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total Contribution",
                                fontFamily = MuktaFontFamily,
                                fontSize = 11.sp,
                                color = SacredTextMuted
                            )
                            Text(
                                text = "₹$effectiveAmount",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracotta
                            )
                        }

                        Button(
                            onClick = {
                                if (name.isNotBlank() && phone.isNotBlank()) {
                                    onProceedToPayment(
                                        effectiveAmount,
                                        name,
                                        gotram,
                                        nakshatram,
                                        phone,
                                        email,
                                        occasion,
                                        selectedPaymentMethod
                                    )
                                }
                            },
                            enabled = name.isNotBlank() && phone.isNotBlank() && effectiveAmount >= seva.minimumAmount,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SacredTerracotta,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("proceed_payment_btn")
                        ) {
                            Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Proceed to Pay",
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
}
