package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CitadelCard
import com.example.ui.theme.CitadelCardElevated
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.MetallicGoldBorder
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

// Sharp geometric angle shape for the Citadel at the End of Time aesthetic
val CitadelCardShape = CutCornerShape(topStart = 14.dp, bottomEnd = 14.dp, topEnd = 3.dp, bottomStart = 3.dp)
val CitadelBadgeShape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp, topEnd = 2.dp, bottomStart = 2.dp)

@Composable
fun LuxuryCard(
    modifier: Modifier = Modifier,
    hasGoldGlow: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val borderBrush = if (hasGoldGlow) {
        Brush.linearGradient(
            colors = listOf(MetallicGoldBorder, Color(0xFF6B531B), MetallicGoldBorder, Color(0xFF3B2F10))
        )
    } else {
        Brush.linearGradient(
            colors = listOf(MetallicGoldBorder.copy(alpha = 0.8f), Color(0xFF282830), MetallicGoldBorder.copy(alpha = 0.4f))
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.2.dp, borderBrush, CitadelCardShape),
        shape = CitadelCardShape,
        colors = CardDefaults.cardColors(containerColor = CitadelCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Kang's Citadel Black & Gold Marble Texture
            Image(
                painter = painterResource(id = R.drawable.bg_marble_card),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = if (hasGoldGlow) 0.42f else 0.28f,
                modifier = Modifier.matchParentSize()
            )

            // Deep Obsidian Tint Overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                ObsidianBlack.copy(alpha = 0.65f),
                                CitadelCard.copy(alpha = 0.88f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier.padding(18.dp),
                content = content
            )
        }
    }
}

@Composable
fun StatMetricPill(
    label: String,
    value: String,
    icon: ImageVector,
    iconTint: Color = BrushedGold,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CitadelBadgeShape)
            .background(CitadelCardElevated)
            .border(1.dp, MetallicGoldBorder.copy(alpha = 0.65f), CitadelBadgeShape)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CutCornerShape(6.dp))
                    .background(iconTint.copy(alpha = 0.15f))
                    .border(0.8.dp, iconTint.copy(alpha = 0.6f), CutCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = TextTertiary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
fun GoldSectionHeader(
    title: String,
    subtitle: String? = null,
    badgeText: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(18.dp)
                .clip(CutCornerShape(1.dp))
                .background(BrushedGold)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                letterSpacing = 0.4.sp
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }
        if (badgeText != null) {
            Box(
                modifier = Modifier
                    .clip(CitadelBadgeShape)
                    .background(BrushedGold.copy(alpha = 0.15f))
                    .border(BorderStroke(1.dp, BrushedGold), CitadelBadgeShape)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = badgeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldGleam
                )
            }
        }
    }
}
