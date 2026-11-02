package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.BrushedGold
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.SageGreen
import com.example.ui.theme.TrustBlue
import kotlin.math.cos
import kotlin.math.sin

/**
 * Living Ambient Mesh Gradient Background.
 * Hardware-accelerated GPU canvas drawing that creates a breathing, subtle ambient atmosphere.
 * Dynamically shifts with organic, slow-phase floating focal points inspired by Apple HIG luxury interfaces.
 */
@Composable
fun AmbientLivingMeshBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_mesh_transition")

    // Slow, organic floating motion parameters (12-16 second harmonic cycles)
    val phaseA by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28318f, // 2 * PI
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "mesh_phase_a"
    )

    val phaseB by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28318f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "mesh_phase_b"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Calculate smoothly drifting centers for ambient gradient orbs
            val orb1X = width * (0.35f + 0.18f * sin(phaseA))
            val orb1Y = height * (0.22f + 0.12f * cos(phaseA * 0.8f))

            val orb2X = width * (0.75f - 0.20f * cos(phaseB))
            val orb2Y = height * (0.35f + 0.14f * sin(phaseB * 0.7f))

            val orb3X = width * (0.50f + 0.15f * cos(phaseA * 0.5f))
            val orb3Y = height * (0.78f - 0.10f * sin(phaseB))

            // 1. Soft Oceanic Blue Trust Glow (upper quadrant)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        TrustBlue.copy(alpha = 0.07f),
                        TrustBlue.copy(alpha = 0.025f),
                        Color.Transparent
                    ),
                    center = Offset(orb1X, orb1Y),
                    radius = width * 0.75f
                ),
                center = Offset(orb1X, orb1Y),
                radius = width * 0.75f
            )

            // 2. Soft Brushed Gold / Champagne Highlight (upper right)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        BrushedGold.copy(alpha = 0.08f),
                        BrushedGold.copy(alpha = 0.02f),
                        Color.Transparent
                    ),
                    center = Offset(orb2X, orb2Y),
                    radius = width * 0.70f
                ),
                center = Offset(orb2X, orb2Y),
                radius = width * 0.70f
            )

            // 3. Living Sage Emerald / Prosperity Accent (lower base)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        SageGreen.copy(alpha = 0.06f),
                        SageGreen.copy(alpha = 0.015f),
                        Color.Transparent
                    ),
                    center = Offset(orb3X, orb3Y),
                    radius = width * 0.65f
                ),
                center = Offset(orb3X, orb3Y),
                radius = width * 0.65f
            )
        }

        // Render main screen contents over the living ambient backdrop
        content()
    }
}
