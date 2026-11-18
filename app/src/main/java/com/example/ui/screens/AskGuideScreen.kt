package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TempleRepository
import com.example.model.FaqItem
import com.example.model.SaiPromise
import com.example.ui.components.GarlandTicker
import com.example.ui.theme.*

@Composable
fun AskGuideScreen() {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("All") }
    var useTelugu by remember { mutableStateOf(false) }

    // Sacred Japa Counter state
    var japaCount by remember { mutableIntStateOf(0) }
    val malasCompleted = japaCount / 108
    val currentMalaBeads = japaCount % 108

    val categories = listOf("All", "Timings", "Directions", "Sevas", "Facilities")

    val filteredFaqs = remember(selectedCategory) {
        if (selectedCategory == "All") {
            TempleRepository.faqItems
        } else {
            TempleRepository.faqItems.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("ask_guide_screen"),
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
                        text = "DEVOTEE GUIDE & SACRED CHANTS",
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
                    text = "Devotee Q&A & Spiritual Guide",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "భక్తుల సందేహాలు, బాబా 11 సూక్తులు మరియు జప సాధన",
                    fontFamily = MuktaFontFamily,
                    fontSize = 14.sp,
                    color = Color(0xFFFCD34D)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Find answers to pilgrimage questions, routes to Puritigadda, pooja bookings, and engage in sacred Om Sai Ram japa meditation.",
                    fontFamily = MuktaFontFamily,
                    fontSize = 13.sp,
                    color = Color(0xFFF5EFE3)
                )
            }
        }

        // Sacred Japa Counter (ఓం సాయి రామ్ 108 నామస్మరణ)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .testTag("japa_counter_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TempleSurface),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(SacredGold, Color(0xFFFDE68A)))
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "🪔 Sacred Japa Counter",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark
                            )
                            Text(
                                text = "ఓం సాయి రామ్ నామస్మరణ (108 Mala)",
                                fontFamily = MuktaFontFamily,
                                fontSize = 12.sp,
                                color = SacredGoldDark
                            )
                        }

                        if (japaCount > 0) {
                            TextButton(
                                onClick = { japaCount = 0 },
                                modifier = Modifier.testTag("reset_japa_btn")
                            ) {
                                Text("Reset", fontFamily = MuktaFontFamily, color = SacredTextMuted)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Big Tap Chanting Bead Button
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(
                                        Color(0xFFFBBF24),
                                        SacredGold,
                                        SacredTerracotta
                                    )
                                )
                            )
                            .clickable {
                                japaCount++
                                try {
                                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        vibrator?.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
                                    } else {
                                        @Suppress("DEPRECATION")
                                        vibrator?.vibrate(30)
                                    }
                                } catch (_: Exception) {}
                            }
                            .testTag("japa_tap_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$currentMalaBeads",
                                fontFamily = MarcellusFontFamily,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "/ 108",
                                fontFamily = MuktaFontFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFFEF3C7)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Tap circle to chant: 'ఓం శ్రీ సాయి రామ్'",
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = SacredTerracotta
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Total Chants: $japaCount  •  Completed Malas: $malasCompleted",
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        color = SacredTextMuted
                    )
                }
            }
        }

        // Q&A Language Toggle & Topic Filter
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Devotee FAQs (ప్రశ్నోత్తరాలు)",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )

                    // English / Telugu switch
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (useTelugu) "తెలుగు" else "English",
                            fontFamily = MuktaFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTerracotta
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Switch(
                            checked = useTelugu,
                            onCheckedChange = { useTelugu = it },
                            modifier = Modifier.testTag("faq_language_switch")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontFamily = MuktaFontFamily, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SacredTerracotta,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.testTag("faq_filter_$cat")
                        )
                    }
                }
            }
        }

        // Faq List Items
        items(filteredFaqs, key = { it.id }) { item ->
            FaqCardItem(item = item, useTelugu = useTelugu)
        }

        // Shirdi Sai Baba's 11 Holy Promises
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Surface(
                    color = SacredTerracottaDark,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("✨", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Sri Sai Baba's 11 Holy Promises",
                                    fontFamily = MarcellusFontFamily,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "శ్రీ సాయి బాబా 11 సూక్తులు",
                                    fontFamily = MuktaFontFamily,
                                    fontSize = 12.sp,
                                    color = Color(0xFFFDE68A)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        TempleRepository.saiPromises.forEach { promise ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Surface(
                                    color = SacredAmberContainer,
                                    shape = CircleShape,
                                    modifier = Modifier.size(22.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${promise.number}",
                                            fontFamily = MuktaFontFamily,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SacredTerracottaDark
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = promise.telugu,
                                        fontFamily = MuktaFontFamily,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFFFDE68A)
                                    )
                                    Text(
                                        text = promise.english,
                                        fontFamily = MuktaFontFamily,
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FaqCardItem(
    item: FaqItem,
    useTelugu: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable { expanded = !expanded }
            .testTag("faq_item_${item.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TempleSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (useTelugu) item.questionTe else item.question,
                    fontFamily = MuktaFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTerracottaDark,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = SacredGoldDark
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Divider(color = SacredBorder.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (useTelugu) item.answerTe else item.answer,
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = SacredTextDark
                    )
                }
            }
        }
    }
}
