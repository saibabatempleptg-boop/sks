package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TempleRepository
import com.example.model.DevoteeAmenity
import com.example.ui.theme.*

@Composable
fun DevoteeAmenitiesCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("devotee_amenities_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TempleSurface),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = Brush.horizontalGradient(listOf(SacredGold, Color(0xFFFDE68A)))
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Main Capacity Headline Badge
            Surface(
                color = SacredAmberContainer,
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🏛️", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "PILGRIM CAPACITY & COMFORT",
                        fontFamily = MuktaFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = SacredTerracottaDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "400 to 500 Devotees at a Time with Ease",
                fontFamily = MarcellusFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = SacredTerracottaDark
            )

            Text(
                text = "ఒకేసారి 400 నుండి 500 మంది భక్తులకు విశాలమైన వసతులు",
                fontFamily = MuktaFontFamily,
                fontSize = 13.sp,
                color = SacredGoldDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Sri Krishna Sai Dhyana Mandhiram offers an expansive, peaceful environment designed to welcome 400 to 500 devotees simultaneously without congestion. Pilgrims enjoy ample room for sitting, resting/sleeping, clean modern washrooms, and open meditation halls filled with cool refreshing breeze.",
                fontFamily = MuktaFontFamily,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = SacredTextDark
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Amenities List
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TempleRepository.devoteeAmenities.forEach { amenity ->
                    AmenityItemRow(amenity = amenity)
                }
            }
        }
    }
}

@Composable
fun AmenityItemRow(amenity: DevoteeAmenity) {
    Surface(
        color = TempleCream,
        shape = RoundedCornerShape(12.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = Brush.horizontalGradient(listOf(SacredBorder, Color.White))
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(SacredAmberContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = amenity.icon, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = amenity.title,
                        fontFamily = MarcellusFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )

                    Surface(
                        color = Color(0xFFFEF3C7),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = amenity.tag,
                            fontFamily = MuktaFontFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredTerracotta,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = amenity.titleTe,
                    fontFamily = MuktaFontFamily,
                    fontSize = 11.sp,
                    color = SacredGoldDark
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = amenity.description,
                    fontFamily = MuktaFontFamily,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = SacredTextMuted
                )
            }
        }
    }
}
