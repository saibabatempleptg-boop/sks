package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.PaymentMethod
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun MockPaymentProcessingDialog(
    amount: Int,
    sevaTitle: String,
    method: PaymentMethod,
    onPaymentSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    var stage by remember { mutableStateOf("connecting") } // connecting -> authorizing -> success

    LaunchedEffect(Unit) {
        delay(1200)
        stage = "authorizing"
        delay(1400)
        stage = "success"
        delay(1000)
        onPaymentSuccess()
    }

    Dialog(
        onDismissRequest = { if (stage != "authorizing") onCancel() },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = TempleSurface,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .testTag("mock_payment_processing_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Security Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Encrypted",
                        tint = SacredGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "256-Bit Encrypted Payment Simulation",
                        fontFamily = MuktaFontFamily,
                        fontSize = 11.sp,
                        color = SacredGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Progress Indicator / Check Icon
                Box(
                    modifier = Modifier.size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (stage == "success") {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = SacredGreen,
                            modifier = Modifier.size(72.dp)
                        )
                    } else {
                        CircularProgressIndicator(
                            color = SacredTerracotta,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = method.icon,
                            fontSize = 24.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = when (stage) {
                        "connecting" -> "Connecting to Temple Gateway..."
                        "authorizing" -> "Authorizing ₹$amount with ${method.title}..."
                        else -> "Payment Authorized Successfully!"
                    },
                    fontFamily = MarcellusFontFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTerracottaDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "$sevaTitle • Sri Krishna Sai Mandhiram",
                    fontFamily = MuktaFontFamily,
                    fontSize = 13.sp,
                    color = SacredTextMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = TempleCream,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Mock Gateway: Simulated Transaction",
                            fontFamily = MuktaFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = SacredTextMuted
                        )
                        Text(
                            text = "Amount: ₹$amount  •  Beneficiary: Sri Krishna Sai Trust",
                            fontFamily = MuktaFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTerracottaDark
                        )
                    }
                }

                if (stage != "success") {
                    Spacer(modifier = Modifier.height(14.dp))
                    TextButton(
                        onClick = onCancel,
                        modifier = Modifier.testTag("cancel_mock_payment_btn")
                    ) {
                        Text("Cancel Payment", fontFamily = MuktaFontFamily, color = SacredTextMuted)
                    }
                }
            }
        }
    }
}
