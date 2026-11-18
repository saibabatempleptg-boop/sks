package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MarcellusFontFamily
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SacredTerracottaDark
import kotlinx.coroutines.delay

@Composable
fun GarlandTicker(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Gentle automated auto-scroll for continuous sacred darshan feel
    LaunchedEffect(Unit) {
        while (true) {
            val max = scrollState.maxValue
            if (max > 0) {
                val next = (scrollState.value + 2) % max
                scrollState.scrollTo(next)
            }
            delay(40)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        SacredTerracottaDark,
                        Color(0xFF882914),
                        Color(0xFFA6391F),
                        SacredTerracottaDark
                    )
                )
            )
            .padding(vertical = 6.dp)
            .testTag("garland_ticker"),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState, enabled = false)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(10) {
                Text(
                    text = "🕉️ ఓం జ్యోతి ఓం శాంతి ఓం సాయి రామ్ 🪔   ",
                    fontFamily = MarcellusFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp,
                    color = Color(0xFFFDE68A)
                )
                Text(
                    text = "•   ",
                    fontSize = 12.sp,
                    color = SacredGold
                )
            }
        }
    }
}
