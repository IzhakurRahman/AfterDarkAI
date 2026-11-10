
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

val bg = Color(0xFF0B0912)
val purple = Color(0xFFB56CFF)

data class Message(val text: String, val user: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ChatApp() }
    }
}

@Composable
fun ChatApp() {
    var input by remember { mutableStateOf("") }
    var accepted by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            Message("Welcome to AfterDark AI! Choose a character and start chatting.", false)
        )
    }

    MaterialTheme(colorScheme = darkColorScheme(primary = purple)) {
        if (!accepted) {
            Column(
                Modifier.fillMaxSize().background(bg).padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("AfterDark AI", color = Color.White, fontSize = 30.sp,
                    fontWeight = FontWeight.Bold)
                Text("18+ ADULTS ONLY", color = purple)
                Spacer(Modifier.height(20.dp))
                Text("This app is for adults. All roleplay must be fictional, consensual and adults-only.",
                    color = Color.White)
                var checked by remember { mutableStateOf(false) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked, { checked = it })
                    Text("I confirm I am 18 or older", color = Color.White)
                }
                Button(
                    onClick = { accepted = true },
                    enabled = checked
                ) {
                    Text("Continue")
                }
            }
        } else {
            Column(
                Modifier.fillMaxSize().background(bg).padding(16.dp)
            ) {
                Text("AfterDark AI", color = Color.White,
                    fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Text("PRIVATE AI CHAT · 18+", color = purple)
                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    Modifier.weight(1f).fillMaxWidth()
                        .background(Color(0xFF171322), RoundedCornerShape(16.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(messages) { msg ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = if (msg.user)
                                Arrangement.End else Arrangement.Start
                        ) {
                            Text(
                                msg.text,
                                Modifier.background(
                                    if (msg.user) Color(0xFF38234D)
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
                        placeholder = { Text("Write a message...") },
                        singleLine = true
                    )
                    IconButton(onClick = {
                        if (input.isNotBlank()) {
                            messages.add(Message(input.trim(), true))
                            messages.add(Message(
                                "Demo mode: Live AI connection will be added separately.",
                                false
                            ))
                            input = ""
                        }
                    }) {
                        Icon(Icons.Default.Send, "Send", tint = purple)
                    }
                }
            }
        }
    }
}
