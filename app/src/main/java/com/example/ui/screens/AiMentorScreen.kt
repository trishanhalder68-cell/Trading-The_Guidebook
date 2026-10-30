package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ai.ChatMessage
import com.example.data.ai.MessageSender
import com.example.ui.components.GoldSectionHeader
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.ImperialGold
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.WealthViewModel

@Composable
fun AiMentorScreen(
    viewModel: WealthViewModel
) {
    val messages by viewModel.chatMessages.collectAsState()
    val isThinking by viewModel.isAiThinking.collectAsState()
    val region by viewModel.currentRegion.collectAsState()

    var textInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val quickQuestions = listOf(
        "How should I invest ${region.currencySymbol}10,000 this month?",
        "Compare ${region.primaryStockIndex} vs Bank Fixed Deposit",
        "Explain Stop-Loss for swing trading",
        "What side hustle has highest margin in ${region.name}?"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Mentor Header
        GoldSectionHeader(
            title = "Aura Market Mentor",
            subtitle = "Powered by Gemini 3.8 Flash • Benevolent Wealth Advisor",
            badgeText = "Online"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Quick Suggestion Chips
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickQuestions) { q ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(ObsidianCardElevated)
                        .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable { viewModel.sendChatMessage(q) }
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = q,
                        fontSize = 11.sp,
                        color = GoldGleam,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Scrollable Chat Thread
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(message = msg)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = { Text("Ask your wealth or trading question...") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ImperialGold,
                    unfocusedBorderColor = GoldBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (textInput.isNotBlank()) {
                        val toSend = textInput.trim()
                        textInput = ""
                        viewModel.sendChatMessage(toSend)
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(ImperialGold),
                enabled = textInput.isNotBlank() && !isThinking
            ) {
                if (isThinking) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = ObsidianBlack, strokeWidth = 2.dp)
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = ObsidianBlack
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isUser = message.sender == MessageSender.USER

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(ImperialGold.copy(alpha = 0.2f))
                    .border(1.dp, ImperialGold, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = null,
                    tint = ImperialGold,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .border(
                    1.dp,
                    if (isUser) GoldBorder else ImperialGold.copy(alpha = 0.35f),
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp
                    )
                ),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isUser) Color(0xFF1E1E26) else ObsidianCardElevated
            )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = if (isUser) "You" else "Aura Market Mentor",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isUser) TextTertiary else ImperialGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.text,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 19.sp
                )
            }
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2A2A38)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = TextPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
