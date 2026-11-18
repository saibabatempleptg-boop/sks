package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TempleRepository
import com.example.model.GalleryPhoto
import com.example.ui.components.GarlandTicker
import com.example.ui.components.PhotoViewerDialog
import com.example.ui.theme.*

@Composable
fun GalleryScreen() {
    var selectedCategory by remember { mutableStateOf("All") }
    var viewingPhoto by remember { mutableStateOf<GalleryPhoto?>(null) }

    val categories = listOf("All", "Shrine", "Rituals", "Mandiram", "Deities")

    val filteredPhotos = remember(selectedCategory) {
        if (selectedCategory == "All") {
            TempleRepository.galleryPhotos
        } else {
            TempleRepository.galleryPhotos.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .testTag("gallery_screen"),
            contentPadding = PaddingValues(bottom = 96.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                GarlandTicker()
            }

            // Header Banner
            item(span = { GridItemSpan(2) }) {
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
                            text = "DIVINE DARSHAN & ALBUM",
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
                        text = "Sacred Photo Gallery",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "పురిటిగడ్డ శ్రీ కృష్ణ సాయి ధ్యాన మందిర దర్శనం",
                        fontFamily = MuktaFontFamily,
                        fontSize = 14.sp,
                        color = Color(0xFFFCD34D)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Explore sanctum photographs, the golden throne of Sri Sai Baba, Ganapathi Homam, festival illuminations, and daily abhishekam.",
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        color = Color(0xFFF5EFE3)
                    )
                }
            }

            // Category Filter Row
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.forEach { cat ->
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
                            modifier = Modifier.testTag("gallery_filter_$cat")
                        )
                    }
                }
            }

            // Grid Items
            items(filteredPhotos, key = { it.id }) { photo ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable { viewingPhoto = photo }
                        .testTag("gallery_item_${photo.id}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = TempleSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = photo.resId),
                            contentDescription = photo.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        )
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = photo.title,
                                fontFamily = MarcellusFontFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredTerracottaDark,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = photo.category,
                                fontFamily = MuktaFontFamily,
                                fontSize = 11.sp,
                                color = SacredGoldDark
                            )
                        }
                    }
                }
            }
        }

        viewingPhoto?.let { photo ->
            PhotoViewerDialog(
                photo = photo,
                onDismiss = { viewingPhoto = null }
            )
        }
    }
}
