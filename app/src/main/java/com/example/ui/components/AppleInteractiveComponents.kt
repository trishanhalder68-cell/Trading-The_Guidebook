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
import com.example.ui.theme.Squircle12
import com.example.ui.theme.Squircle16
import com.example.ui.theme.Squircle20
import com.example.ui.theme.Squircle24
import com.example.ui.theme.Squircle28
import com.example.ui.theme.TrustBlue
import com.example.ui.theme.TrustBlueHover
import kotlinx.coroutines.launch

/**
 * Apple Human Interface Guidelines: Spring-based Tactile Scale Feedback.
 * Provides organic physical response using authentic spring parameters (mass, damping, stiffness).
 * Accelerates smoothly, overshoots naturally, and settles without abrupt cuts.
 */
fun Modifier.appleSpringPress(
    targetScale: Float = 0.965f,
    onClick: (() -> Unit)? = null
): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) targetScale else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, // 0.75f - lively natural tactile overshoot
            stiffness = Spring.StiffnessMediumLow          // 400f - fluid responsiveness
        ),
        label = "apple_spring_scale"
    )

    this
        .scale(scale)
        .pointerInput(onClick) {
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
 * Modern Glassmorphism Card for Apple HIG Clean Luxury Aesthetic with continuous Squircle geometry.
 * Features a frosted translucent surface, subtle specular gradient border, and soft diffused ambient shadow.
 */
@Composable
fun AppleGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = Squircle24,
    backgroundColor: Color = Color.White.copy(alpha = 0.92f),
    borderColor: Color = Color(0xFFE2E8F0),
    elevation: Dp = 6.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val pressModifier = if (onClick != null) {
        Modifier.appleSpringPress(targetScale = 0.985f, onClick = onClick)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                spotColor = Color(0x12000000),
                ambientColor = Color(0x06000000)
            )
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    listOf(
                        backgroundColor,
                        backgroundColor.copy(alpha = 0.94f)
                    )
                )
            )
            .border(
                BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            borderColor,
                            Color.White.copy(alpha = 0.6f),
                            borderColor.copy(alpha = 0.85f)
                        )
                    )
                ),
                shape = shape
            )
            .then(pressModifier)
    ) {
        content()
    }
}

/**
 * High-Converting Citadel Lucrative Attention-Seeking Button with Squircle Radii.
 * Designed to visceral human psychology: pulsating halo, shimmering light sweep,
 * and tactile spring responsiveness.
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
        targetValue = 1.025f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    // Shimmer sweep across the button surface
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -300f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_sweep"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(pulseScale)
            .shadow(
                elevation = 14.dp,
                shape = Squircle24,
                spotColor = BrushedGold.copy(alpha = 0.35f),
                ambientColor = TrustBlue.copy(alpha = 0.15f)
            )
            .clip(Squircle24)
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
                Squircle24
            )
            .appleSpringPress(targetScale = 0.96f, onClick = onClick)
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(EmeraldProfit)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LIVE CURRICULUM ACTIVE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = EmeraldProfit,
                        letterSpacing = 1.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(Squircle12)
                        .background(BrushedGold.copy(alpha = 0.2f))
                        .border(BorderStroke(1.dp, MetallicGoldBorder.copy(alpha = 0.6f)), Squircle12)
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
                        fontSize = 18.sp,
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

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(Squircle16)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    BrushedGold,
                                    GoldDark
                                )
                            )
                        )
                        .border(BorderStroke(1.dp, GoldGleam), Squircle16),
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
