package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamContainer
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.MetallicGoldBorder
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SageGreen
import com.example.ui.theme.SageGreenAccent
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateDark
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.TrustBlue
import com.example.ui.theme.TrustBlueHover
import kotlinx.coroutines.launch

/**
 * Apple Human Interface Guidelines: Spring-based Tactile Scale Feedback.
 * Provides an effortless, tactile physical response on press using iOS-like spring physics.
 */
fun Modifier.appleSpringPress(
    targetScale: Float = 0.96f,
    onClick: (() -> Unit)? = null
): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) targetScale else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "apple_spring_scale"
    )

    this
        .scale(scale)
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    awaitFirstDown(requireUnconsumed = false)
                    isPressed = true
                    val up = waitForUpOrCancellation()
                    isPressed = false
                    if (up != null && onClick != null) {
                        onClick()
                    }
                }
            }
        }
}

/**
 * Modern Glassmorphism Card for Apple HIG Clean Luxury Aesthetic.
 * Features a frosted translucent surface, subtle specular gradient border, and soft diffused ambient shadow.
 */
@Composable
fun AppleGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(22.dp),
    backgroundColor: Color = Color.White.copy(alpha = 0.85f),
    borderColor: Color = Color.White.copy(alpha = 0.95f),
    elevation: Dp = 8.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val clickableModifier = if (onClick != null) {
        Modifier
            .appleSpringPress(targetScale = 0.98f, onClick = onClick)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                spotColor = Color(0x18000000),
                ambientColor = Color(0x0A000000)
            )
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    listOf(
                        backgroundColor,
                        backgroundColor.copy(alpha = 0.92f)
                    )
                )
            )
            .border(
                BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            borderColor,
                            Color.White.copy(alpha = 0.4f),
                            borderColor.copy(alpha = 0.7f)
                        )
                    )
                ),
                shape = shape
            )
            .then(clickableModifier)
    ) {
        content()
    }
}

/**
 * High-Converting Citadel Lucrative Attention-Seeking Button.
 * Designed to visceral human psychology: pulsating aura, shimmering light sweep across metallic gold & trust blue,
 * real-time participant badge, and tactile spring responsiveness.
 */
@Composable
fun LucrativeAttentionButton(
    modifier: Modifier = Modifier,
    title: String = "INITIATE MARKET MASTERY",
    subtitle: String = "Start Level 1 Free • 14,820 Traders Compounding Daily",
    badgeText: String = "HIGH ASYMMETRY OPPORTUNITY",
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "lucrative_anim")

    // Pulsating halo scale
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    // Shimmer sweep across the button surface
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -300f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_sweep"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(pulseScale)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = BrushedGold.copy(alpha = 0.45f),
                ambientColor = TrustBlue.copy(alpha = 0.2f)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        ObsidianBlack,
                        Color(0xFF16151E),
                        ObsidianBlack
                    )
                )
            )
            .border(
                BorderStroke(
                    1.5.dp,
                    Brush.linearGradient(
                        colors = listOf(
                            MetallicGoldBorder,
                            GoldGleam,
                            TrustBlueHover,
                            MetallicGoldBorder
                        )
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .appleSpringPress(targetScale = 0.95f, onClick = onClick)
            .testTag("lucrative_masterclass_cta")
            .padding(18.dp)
    ) {
        // Shimmer specular highlight sweep
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawBehind {
                    val brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            GoldGleam.copy(alpha = 0.18f),
                            Color.White.copy(alpha = 0.35f),
                            GoldGleam.copy(alpha = 0.18f),
                            Color.Transparent
                        ),
                        start = Offset(shimmerOffset, 0f),
                        end = Offset(shimmerOffset + 240f, size.height)
                    )
                    drawRect(brush = brush)
                }
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            // Top Row: Urgency & Status Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Live active dot & badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(EmeraldProfit)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LIVE SESSION OPEN",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = EmeraldProfit,
                        letterSpacing = 1.sp
                    )
                }

                // Asymmetry opportunity badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(BrushedGold.copy(alpha = 0.2f))
                        .border(BorderStroke(1.dp, MetallicGoldBorder.copy(alpha = 0.6f)), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = GoldGleam,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = badgeText,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldGleam
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main CTA Content Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color(0xFFE2E2EC),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Official Action Emblem
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    BrushedGold,
                                    GoldDark
                                )
                            )
                        )
                        .border(BorderStroke(1.dp, GoldGleam), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Start Learning",
                        tint = ObsidianBlack,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}
