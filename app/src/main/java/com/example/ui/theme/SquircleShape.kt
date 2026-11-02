package com.example.ui.theme

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Continuous Curvature "Squircle" Shape inspired by Apple Human Interface Guidelines.
 * Eliminates standard circular corner discontinuities by smoothing curvature acceleration
 * across the corner tangents with cubic bezier tension.
 */
class SquircleShape(
    val cornerRadius: Dp,
    val smoothness: Float = 0.82f
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val r = with(density) { cornerRadius.toPx() }.coerceAtMost(minOf(size.width, size.height) / 2f)
        if (r <= 0.5f) {
            return Outline.Rectangle(Rect(0f, 0f, size.width, size.height))
        }

        val w = size.width
        val h = size.height

        // Smooth continuous curvature bezier tension constants
        val p = (r * (1f + smoothness * 0.18f)).coerceAtMost(minOf(w, h) / 2f)
        val c = p * 0.56f

        val path = Path().apply {
            moveTo(p, 0f)
            lineTo(w - p, 0f)
            cubicTo(w - p + c, 0f, w, p - c, w, p)
            lineTo(w, h - p)
            cubicTo(w, h - p + c, w - p + c, h, w - p, h)
            lineTo(p, h)
            cubicTo(p - c, h, 0f, h - p + c, 0f, h - p)
            lineTo(0f, p)
            cubicTo(0f, p - c, p - c, 0f, p, 0f)
            close()
        }
        return Outline.Generic(path)
    }

    override fun toString(): String = "SquircleShape(cornerRadius=$cornerRadius, smoothness=$smoothness)"
}

// Global Apple-HIG Squircle Presets
val Squircle8 = SquircleShape(8.dp)
val Squircle12 = SquircleShape(12.dp)
val Squircle16 = SquircleShape(16.dp)
val Squircle20 = SquircleShape(20.dp)
val Squircle24 = SquircleShape(24.dp)
val Squircle28 = SquircleShape(28.dp)
val Squircle32 = SquircleShape(32.dp)
val Squircle40 = SquircleShape(40.dp)
