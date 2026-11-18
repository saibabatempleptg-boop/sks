package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.SevaRepository
import com.example.data.TempleRepository
import com.example.model.ContributionRecord
import com.example.ui.theme.*

@Composable
fun ContributionReceiptDialog(
    record: ContributionRecord,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val shareText = """
        🪔 SRI KRISHNA SAI DHYANA MANDHIRAM, PURITIGADDA
        Official Seva Contribution Receipt
        
        Receipt No: ${record.receiptId}
        Date: ${SevaRepository.formatDate(record.timestampMillis)}
        
        Seva: ${record.sevaTitle}
        Contribution: ₹${record.amount}
        Devotee: ${record.devoteeName}
        ${if (record.gotram.isNotBlank()) "Gotram: ${record.gotram}" else ""}
        ${if (record.nakshatram.isNotBlank()) "Nakshatram: ${record.nakshatram}" else ""}
        Sankalpam: ${record.occasion}
        Payment: ${record.paymentMethod.title} (Success)
        Txn Ref: ${record.transactionRef}
        
        "శ్రద్ధ & సబూరి — May Baba shower divine grace and protection upon you and your family."
        Nadakuduru Road, Puritigadda, AP 521126
    """.trimIndent()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(20.dp))
                .testTag("contribution_receipt_dialog"),
            color = TempleSurface,
            tonalElevation = 8.dp
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top Sacred Header
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
                        Column {
                            Text(
                                text = "Official E-Receipt • రసీదు",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = record.receiptId,
                                fontFamily = MuktaFontFamily,
                                fontSize = 12.sp,
                                color = Color(0xFFFDE68A)
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("close_receipt_btn")
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    }
                }

                // Scrollable Receipt Body
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Success Stamp Banner
                    Surface(
                        color = Color(0xFFDCFCE7),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SacredGreen,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Contribution Received Successfully",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF14532D)
                                )
                                Text(
                                    text = "సమర్పించబడింది • Consecrated Devotion",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 11.sp,
                                    color = SacredGreen
                                )
                            }
                        }
                    }

                    // Mandhiram Header Block
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = TempleRepository.TEMPLE_NAME,
                            fontFamily = MarcellusFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTerracottaDark,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = TempleRepository.TEMPLE_NAME_TELUGU,
                            fontFamily = MuktaFontFamily,
                            fontSize = 12.sp,
                            color = SacredGoldDark,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Puritigadda, Krishna District, Andhra Pradesh",
                            fontFamily = MuktaFontFamily,
                            fontSize = 11.sp,
                            color = SacredTextMuted
                        )
                    }

                    Divider(color = SacredBorder)

                    // Receipt Fields Table
                    ReceiptLine("Seva Category", record.sevaTitle)
                    ReceiptLine("Contribution Amount", "₹${record.amount}")
                    ReceiptLine("Devotee Name", record.devoteeName)
                    if (record.gotram.isNotBlank()) {
                        ReceiptLine("Gotram (గోత్రం)", record.gotram)
                    }
                    if (record.nakshatram.isNotBlank()) {
                        ReceiptLine("Nakshatram (నక్షత్రం)", record.nakshatram)
                    }
                    ReceiptLine("Occasion / Sankalpam", record.occasion)
                    ReceiptLine("Mobile Number", record.phone)
                    ReceiptLine("Payment Mode", record.paymentMethod.title)
                    ReceiptLine("Date & Time", SevaRepository.formatDate(record.timestampMillis))
                    ReceiptLine("Txn Reference", record.transactionRef)

                    Divider(color = SacredBorder)

                    // Baba's Divine Blessing Message
                    Surface(
                        color = SacredAmberContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🕉️ శ్రీ సాయి ఆశీస్సులు",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"శ్రద్ధ & సబూరి — May Bhagwan Sri Sai Baba shower everlasting health, prosperity, and spiritual peace upon you and your family.\"",
                                fontFamily = MuktaFontFamily,
                                fontSize = 12.sp,
                                color = SacredTextDark,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Bottom Actions
                Surface(
                    color = TempleCream,
                    tonalElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, "Share Temple Receipt")
                                context.startActivity(shareIntent)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("share_receipt_btn")
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Share", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=${Uri.encode(shareText)}"
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SacredGreen,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1.3f)
                                .testTag("whatsapp_receipt_btn")
                        ) {
                            Text("💬 WhatsApp Mandhiram", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReceiptLine(label: String, value: String) {
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
            modifier = Modifier.weight(0.45f)
        )
        Text(
            text = value,
            fontFamily = MuktaFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = SacredTextDark,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.55f)
        )
    }
}
