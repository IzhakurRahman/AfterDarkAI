
package com.afterdarkai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val bg = Color(0xFF0B0912)
private val purple = Color(0xFFB56CFF)

data class Character(
    val name: String,
    val bn: String,
    val en: String,
    val personality: String
)

data class Message(val text: String, val user: Boolean)

private val characters = listOf(
    Character("Sophia", "বন্ধুত্বপূর্ণ ও রোমান্টিক",
        "Friendly & Romantic", "warm"),
    Character("Mia", "রহস্যময় ও বুদ্ধিমান",
        "Mysterious & Smart", "mysterious"),
    Character("Emma", "আত্মবিশ্বাসী ও মজার",
        "Confident & Playful", "playful"),
    Character("Luna", "সৃজনশীল ও যত্নশীল",
        "Creative & Caring", "caring")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AfterDarkApp() }
    }
}

@Composable
fun AfterDarkApp() {
    var accepted by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    var bengali by remember { mutableStateOf(true) }
    var input by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(characters[0]) }
    var menuOpen by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf<Message>()
    }

    MaterialTheme(
        colorScheme = darkColorScheme(primary = purple)
    ) {
        if (!accepted) {
            Column(
                Modifier.fillMaxSize().background(bg).padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "AfterDark AI",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("PRIVATE AI CHAT · 18+", color = purple)
                Spacer(Modifier.height(24.dp))

                Text(
                    if (bengali)
                        "এই অ্যাপটি শুধুমাত্র ১৮ বছর বা তার বেশি বয়সীদের জন্য।"
                    else
                        "This app is only for adults aged 18 or older.",
                    color = Color.White
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked, { checked = it })
                    Text(
                        if (bengali) "আমার বয়স ১৮+" else "I am 18 or older",
                        color = Color.White
                    )
                }

                Button(
                    onClick = { accepted = true },
                    enabled = checked
                ) {
                    Text(if (bengali) "প্রবেশ করুন" else "Continue")
                }

                TextButton(onClick = { bengali = !bengali }) {
                    Text(if (bengali) "English" else "বাংলা")
                }
            }
        } else {
            Column(
                Modifier.fillMaxSize()
                    .background(bg)
                    .padding(16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "AfterDark AI",
                        color = Color.White,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )

                    TextButton(onClick = { bengali = !bengali }) {
                        Text(if (bengali) "EN" else "বাংলা")
                    }
                }

                Text(
                    "PRIVATE TEXT CHAT · 18+",
                    color = purple
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    if (bengali) "তোমার Character বেছে নাও"
                    else "Choose Your Character",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Box {
                    OutlinedButton(
                        onClick = { menuOpen = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            selected.name + " — " +
                                if (bengali) selected.bn else selected.en
                        )
                    }

                    DropdownMenu(
                        expanded = menuOpen,
                        onDismissRequest = { menuOpen = false }
                    ) {
                        characters.forEach { character ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        character.name + " — " +
                                            if (bengali) character.bn
                                            else character.en
                                    )
                                },
                                onClick = {
                                    selected = character
                                    messages.clear()
                                    menuOpen = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                LazyColumn(
                    Modifier.weight(1f).fillMaxWidth()
                        .background(
                            Color(0xFF171322),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (messages.isEmpty()) {
                        item {
                            Text(
                                if (bengali)
                                    "হ্যালো! আমি ${selected.name}। " +
                                    "আমার সাথে কথা শুরু করো!"
                                else
                                    "Hello! I'm ${selected.name}. " +
                                    "Let's start chatting!",
                                color = Color.White
                            )
                        }
                    }

                    items(messages) { message ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                if (message.user) Arrangement.End
                                else Arrangement.Start
                        ) {
                            Text(
                                message.text,
                                Modifier.background(
                                    if (message.user)
                                        Color(0xFF38234D)
                                    else Color(0xFF282234),
                                    RoundedCornerShape(14.dp)
                                ).padding(12.dp),
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                if (bengali) "মেসেজ লিখো..."
                                else "Write a message..."
                            )
                        },
                        singleLine = true
                    )

                    IconButton(onClick = {
                        if (input.isNotBlank()) {
                            messages.add(
                                Message(input.trim(), true)
                            )
                            messages.add(
                                Message(
                                    if (bengali)
                                        "এটি এখন Demo Mode। Live AI সংযোগ পরবর্তী আপডেটে যুক্ত হবে।"
                                    else
                                        "This is Demo Mode. Live AI will be connected in a future update.",
                                    false
                                )
                            )
                            input = ""
                        }
                    }) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Send",
                            tint = purple
                        )
                    }
                }
            }
        }
    }
}
