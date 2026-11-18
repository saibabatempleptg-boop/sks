package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.credentials.CredentialManager
import com.example.auth.AuthManager
import com.example.data.TempleRepository
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

enum class AppTab(
    val title: String,
    val titleTe: String,
    val iconFilled: androidx.compose.ui.graphics.vector.ImageVector,
    val iconOutlined: androidx.compose.ui.graphics.vector.ImageVector
) {
    HOME("Darshan", "దర్శనం", Icons.Filled.Home, Icons.Outlined.Home),
    PANCHANGAM("Panchangam", "పంచాంగం", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
    ABHISHEK("Abhishek", "అభిషేకం", Icons.Filled.WaterDrop, Icons.Outlined.WaterDrop),
    GALLERY("Gallery", "గ్యాలరీ", Icons.Filled.PhotoLibrary, Icons.Outlined.PhotoLibrary),
    SEVA("Seva Kendram", "సేవా కేంద్రం", Icons.Filled.VolunteerActivism, Icons.Outlined.VolunteerActivism),
    GUIDE("Guide & Japa", "జపం", Icons.Filled.AutoStories, Icons.Outlined.AutoStories)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SriKrishnaSaiTheme {
                RootApp()
            }
        }
    }
}

@Composable
fun RootApp() {
    var currentUser by remember { mutableStateOf(Firebase.auth.currentUser) }

    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            currentUser = auth.currentUser
        }
        Firebase.auth.addAuthStateListener(listener)
        onDispose {
            Firebase.auth.removeAuthStateListener(listener)
        }
    }

    if (currentUser == null) {
        AuthScreen(
            onAuthSuccess = {
                currentUser = Firebase.auth.currentUser
            }
        )
    } else {
        val uid = currentUser!!.uid
        key(uid) {
            MainApp(currentUser = currentUser!!)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(currentUser: FirebaseUser) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember { CredentialManager.create(context) }
    var currentTab by remember { mutableStateOf(AppTab.HOME) }
    var showProfileDialog by remember { mutableStateOf(false) }

    // Android back handler to return to home tab if on a sub-screen
    BackHandler(enabled = currentTab != AppTab.HOME) {
        currentTab = AppTab.HOME
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Sri Krishna Sai Mandhiram",
                            fontFamily = MarcellusFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Puritigadda, Andhra Pradesh",
                            fontFamily = MuktaFontFamily,
                            fontSize = 11.sp,
                            color = Color(0xFFFDE68A)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { currentTab = AppTab.PANCHANGAM },
                        modifier = Modifier.testTag("topbar_panchangam_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Panchangam",
                            tint = if (currentTab == AppTab.PANCHANGAM) Color(0xFFFDE68A) else Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TempleRepository.MAPS_URL))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.testTag("topbar_map_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Directions,
                            contentDescription = "Get directions",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            val url = "https://wa.me/${TempleRepository.TEMPLE_PHONE_CLEAN}?text=Jai%20Sai%20Ram"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        },
                        modifier = Modifier.testTag("topbar_whatsapp_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "WhatsApp chat",
                            tint = Color(0xFF86EFAC)
                        )
                    }
                    // Devotee Profile Icon Button
                    IconButton(
                        onClick = { showProfileDialog = true },
                        modifier = Modifier.testTag("topbar_profile_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Devotee Profile",
                            tint = Color(0xFFFCD34D)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SacredTerracottaDark
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = TempleSurface,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_navigation_bar")
            ) {
                AppTab.values().forEach { tab ->
                    val isSelected = currentTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.iconFilled else tab.iconOutlined,
                                contentDescription = tab.title,
                                tint = if (isSelected) SacredTerracotta else SacredTextMuted
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontFamily = MuktaFontFamily,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) SacredTerracotta else SacredTextMuted,
                                maxLines = 1
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = SacredAmberContainer
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(TempleCream)
        ) {
            Crossfade(targetState = currentTab, label = "tab_crossfade") { tab ->
                when (tab) {
                    AppTab.HOME -> HomeScreen(
                        onNavigateToTimings = { currentTab = AppTab.ABHISHEK },
                        onNavigateToAsk = { currentTab = AppTab.GUIDE },
                        onNavigateToSeva = { currentTab = AppTab.SEVA },
                        onNavigateToPanchangam = { currentTab = AppTab.PANCHANGAM }
                    )
                    AppTab.PANCHANGAM -> PanchangamScreen()
                    AppTab.ABHISHEK -> AbhishekScreen()
                    AppTab.GALLERY -> GalleryScreen()
                    AppTab.SEVA -> SevaKendramScreen()
                    AppTab.GUIDE -> AskGuideScreen()
                }
            }
        }
    }

    if (showProfileDialog) {
        Dialog(onDismissRequest = { showProfileDialog = false }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = TempleSurface,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = SacredAmberContainer,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = SacredTerracotta,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentUser.displayName ?: "Devotee of Sai",
                        fontFamily = MarcellusFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredTerracottaDark
                    )

                    Text(
                        text = currentUser.email ?: "",
                        fontFamily = MuktaFontFamily,
                        fontSize = 13.sp,
                        color = SacredTextMuted
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        color = Color(0xFFDCFCE7),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "✓ Authenticated with Google & Firebase",
                            fontFamily = MuktaFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF15803D),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            showProfileDialog = false
                            AuthManager.signOut(credentialManager, coroutineScope)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("sign_out_btn")
                    ) {
                        Text("Sign Out (లాగ్ అవుట్)", fontFamily = MuktaFontFamily, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { showProfileDialog = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Close", fontFamily = MuktaFontFamily, color = SacredTextMuted)
                    }
                }
            }
        }
    }
}
