package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.TempleRepository
import com.example.ui.theme.*

@Composable
fun SacredHeader(
    modifier: Modifier = Modifier,
    onNavigateToTimings: () -> Unit = {},
    onNavigateToAsk: () -> Unit = {},
    onNavigateToPanchangam: () -> Unit = {},
    onOpenDirections: () -> Unit = {}
) {
    // Gentle breathing halo animation
    val infiniteTransition = rememberInfiniteTransition(label = "halo")
    val haloPulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "haloPulse"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        SacredTerracottaDark,
                        SacredTerracotta,
                        Color(0xFF5E1708)
                    )
                )
            )
            .testTag("sacred_header")
    ) {
        GarlandTicker()

        // Divine Symbols & Centered Baba Shrine Medallion
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Left sacred symbols
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "ॐ",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 24.sp,
                    color = SacredGold
                )
                Text(
                    text = "卐",
                    fontSize = 18.sp,
                    color = Color(0xFFFDE68A)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Center Medallion of Baba
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(86.dp)
                        .scale(haloPulse)
                ) {
                    // Outer glowing halo
                    Box(
                        modifier = Modifier
                            .size(84.dp)
                            .drawBehind {
                                drawCircle(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFFFD54F).copy(alpha = 0.6f),
                                            Color(0xFFB45309).copy(alpha = 0.2f),
                                            Color.Transparent
                                        )
                                    )
                                )
                                drawCircle(
                                    color = Color(0xFFF59E0B),
                                    style = Stroke(
                                        width = 2.dp.toPx()
                                    )
                                )
                            }
                    )

                    // Rounded Idol Image
                    Image(
                        painter = painterResource(id = R.drawable.baba_idol),
                        contentDescription = "Sri Sai Baba idol adorned with garlands",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFFFDE68A), CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Sri Sai Baba",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "శ్రీ సాయి బాబా",
                    fontFamily = MuktaFontFamily,
                    fontSize = 11.sp,
                    color = Color(0xFFFDE68A)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Right sacred symbols
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "卍",
                    fontSize = 18.sp,
                    color = Color(0xFFFDE68A)
                )
                Text(
                    text = "ॐ",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 24.sp,
                    color = SacredGold
                )
            }
        }

        // Hero Title & Description
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = Color(0xFF451A03).copy(alpha = 0.6f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "PURITIGADDA • ANDHRA PRADESH",
                    fontFamily = MuktaFontFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.5.sp,
                    color = Color(0xFFFDE68A),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = TempleRepository.TEMPLE_NAME,
                fontFamily = MarcellusFontFamily,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = TempleRepository.TEMPLE_NAME_TELUGU,
                fontFamily = MuktaFontFamily,
                fontSize = 16.sp,
                color = Color(0xFFFCD34D),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "A sacred Sai Baba temple on Nadakuduru Road, welcoming devotees for darshan, homams, and quiet meditation every day.",
                fontFamily = MuktaFontFamily,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = Color(0xFFF5EFE3),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Sacred Action Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = onNavigateToTimings,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SacredMarigold,
                        contentColor = Color(0xFF3E1700)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("action_timings_chip")
                ) {
                    Text(
                        text = "Timings",
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onNavigateToPanchangam,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFEF3C7),
                        contentColor = SacredTerracottaDark
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("action_panchangam_chip")
                ) {
                    Text(
                        text = "🗓️ పంచాంగం",
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = onNavigateToAsk,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(SacredGold, Color(0xFFFDE68A)))
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("action_ask_chip")
                ) {
                    Text(
                        text = "Ask / Seva Q&A",
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedButton(
                    onClick = onOpenDirections,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFFDE68A)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(Color(0xFFFDE68A), SacredGold))
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("action_directions_chip")
                ) {
                    Text(
                        text = "Get Directions",
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
