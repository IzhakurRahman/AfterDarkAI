package com.afterdarkai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

private val Bg = Color(0xFF0B0912)
private val Panel = Color(0xFF171322)
private val Accent = Color(0xFFB56CFF)
private val Muted = Color(0xFFB9B0C8)

data class ChatMessage(val text: String, val fromUser: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AfterDarkApp() }
    }
}

@Composable
fun AfterDarkApp() {
    var accepted by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("Nova · The storyteller") }
    var input by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Welcome to AfterDark AI. Choose a character and start a fictional conversation.", false)
        )
    }

    MaterialTheme(colorScheme = darkColorScheme(
        background = Bg, surface = Panel, primary = Accent,
        onBackground = Color.White, onSurface = Color.White
    )) {
        if (!accepted) {
            AgeGate(onContinue = { accepted = true })
        } else {
            Column(
                Modifier.fillMaxSize().background(Bg).padding(16.dp)
            ) {
                Text("AfterDark AI", fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("PRIVATE AI ROLEPLAY · 18+", color = Accent, fontSize = 11.sp, letterSpacing = 1.5.sp)
                Spacer(Modifier.height(18.dp))
                Text("Choose your character", color = Muted, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Nova · The storyteller", "Alex · The companion", "Mira · The adventurer").forEach {
                        FilterChip(
                            selected = selected == it,
                            onClick = { selected = it },
                            label = { Text(it.substringBefore(" · "), fontSize = 12.sp) }
                        )
                    }
                }
                Text(selected, color = Accent, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 10.dp))
                Spacer(Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth()
                        .background(Panel, RoundedCornerShape(18.dp)).padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(messages) { msg ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = if (msg.fromUser) Arrangement.End else Arrangement.Start
                        ) {
                            Text(
                                msg.text,
                                modifier = Modifier.widthIn(max = 290.dp)
                                    .background(if (msg.fromUser) Color(0xFF38234D) else Color(0xFF282234),
                                        RoundedCornerShape(16.dp)).padding(12.dp),
                                color = Color.White, fontSize = 14.sp
                            )
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = input, onValueChange = { input = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Write a message…", color = Muted) },
                        shape = RoundedCornerShape(24.dp), singleLine = true
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            val text = input.trim()
                            if (text.isNotEmpty()) {
                                messages.add(ChatMessage(text, true))
                                messages.add(ChatMessage(
                                    "Demo mode: connect an AI provider in the next setup step to receive live replies. Keep roleplay fictional, consensual, and adults-only.",
                                    false
                                ))
                                input = ""
                            }
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = Accent)
                    ) { Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White) }
                }
                Text(
                    "Demo interface · No live AI connection configured",
                    color = Muted, fontSize = 10.sp, modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun AgeGate(onContinue: () -> Unit) {
    var confirmed by remember { mutableStateOf(false) }
    Column(
        Modifier.fillMaxSize().background(Bg).padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("AfterDark AI", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("18+ ADULTS ONLY", color = Accent, fontSize = 13.sp, letterSpacing = 2.sp)
        Spacer(Modifier.height(24.dp))
        Text("This app is intended for adults. Characters and scenarios must be fictional, consensual, and involve adults only.",
            color = Muted, fontSize = 14.sp)
        Spacer(Modifier.height(18.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = confirmed, onCheckedChange = { confirmed = it })
            Text("I confirm that I am 18 or older", color = Color.White, fontSize = 13.sp)
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onContinue, enabled = confirmed,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Accent)
        ) { Text("Continue") }
    }
}
