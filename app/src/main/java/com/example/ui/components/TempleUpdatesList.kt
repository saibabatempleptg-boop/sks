package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.data.TempleRepository
import com.example.model.MandiramUpdate
import com.example.ui.theme.*

@Composable
fun TempleUpdatesList(
    modifier: Modifier = Modifier,
    onNavigateToTimings: () -> Unit = {},
    onNavigateToFacilities: () -> Unit = {}
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Darshan Timings", "Weekly Worship", "Facilities & Comfort", "Pooja Sevas")

    val filteredUpdates = remember(selectedCategory) {
        if (selectedCategory == "All") {
            TempleRepository.updates
        } else {
            TempleRepository.updates.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("temple_updates_section")
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SacredAmberContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Campaign,
                    contentDescription = null,
                    tint = SacredTerracotta,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Temple Updates & Announcements",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredTerracottaDark
                )
                Text(
                    text = "ఆలయ విశేషాలు & తాజా ప్రకటనలు",
                    fontFamily = MuktaFontFamily,
                    fontSize = 13.sp,
                    color = SacredGoldDark
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Latest news regarding darshan hours, weekly bhajans, pilgrim facilities, and temple sevas.",
            fontFamily = MuktaFontFamily,
            fontSize = 13.sp,
            color = SacredTextMuted,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category Filter Chips
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    label = {
                        Text(
                            text = cat,
                            fontFamily = MuktaFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SacredTerracotta,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("update_filter_$cat")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // List Layout of Updates
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            filteredUpdates.forEach { update ->
                TempleUpdateListItem(
                    update = update,
                    onActionClick = {
                        when (update.id) {
                            "morning_darshan", "evening_darshan" -> onNavigateToTimings()
                            "pilgrim_capacity" -> onNavigateToFacilities()
                            "thursday_bhajan" -> onNavigateToTimings()
                            "homam_pooja" -> {
                                val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=Jai%20Sai%20Ram.%20I%20would%20like%20to%20inquire%20about%20Pooja%20/%20Homam%20seva."
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            }
                            "subhavedika_hall" -> {
                                val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=Jai%20Sai%20Ram.%20I%20would%20like%20to%20inquire%20about%20Subhavedika%20Hall%20booking."
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            }
                            else -> onNavigateToTimings()
                        }
                    },
                    onShareClick = {
                        val shareText = """
                            *Sri Krishna Sai Dhyana Mandhiram - Temple Update*
                            ${update.title}
                            ${update.titleTe}
                            
                            ${update.text}
                            
                            ${update.textTe}
                            
                            📍 Puritigadda, Andhra Pradesh
                            📞 +91 7981240451
                        """.trimIndent()
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareText)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Share Temple Update"))
                    }
                )
            }
        }
    }
}

@Composable
fun TempleUpdateListItem(
    update: MandiramUpdate,
    onActionClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .testTag("update_list_item_${update.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TempleSurface),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = Brush.horizontalGradient(
                if (update.isImportant) listOf(SacredAmberContainer, Color(0xFFFDE68A))
                else listOf(SacredBorder, Color.White)
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (update.isImportant) 3.dp else 1.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Badges row: Category, Date, and Important tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFFF5EFE3),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = update.category,
                            fontFamily = MuktaFontFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTextMuted,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Surface(
                        color = SacredAmberContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = update.dateDisplay,
                            fontFamily = MuktaFontFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SacredGoldDark,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                if (update.isImportant) {
                    Surface(
                        color = SacredTerracotta,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "IMPORTANT",
                            fontFamily = MuktaFontFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Icon + Title Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(
                            if (update.isImportant) Color(0xFFFEF3C7) else Color(0xFFF5EFE3)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = update.icon, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = update.title,
                        fontFamily = MarcellusFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )
                    Text(
                        text = update.titleTe,
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        color = SacredGoldDark
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = SacredTextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Body text
            Text(
                text = update.text,
                fontFamily = MuktaFontFamily,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = SacredTextDark,
                maxLines = if (expanded) Int.MAX_VALUE else 2
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Divider(color = SacredBorder.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = update.textTe,
                        fontFamily = MuktaFontFamily,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = SacredTextDark.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Bottom Actions Row: Action Button + Share Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                update.actionText?.let { action ->
                    OutlinedButton(
                        onClick = onActionClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = SacredTerracotta
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(SacredTerracotta)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = action,
                            fontFamily = MuktaFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } ?: Spacer(modifier = Modifier.width(1.dp))

                IconButton(
                    onClick = onShareClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share update",
                        tint = SacredTextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
