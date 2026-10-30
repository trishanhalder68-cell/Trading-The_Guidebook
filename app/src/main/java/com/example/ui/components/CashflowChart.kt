package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.ImperialGold
import com.example.ui.theme.OutlineDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

data class ChartPoint(
    val label: String,
    val value: Float
)

@Composable
fun CashflowChart(
    points: List<ChartPoint>,
    currencySymbol: String,
    modifier: Modifier = Modifier,
    lineColor: Color = ImperialGold,
    fillStartColor: Color = ImperialGold.copy(alpha = 0.35f),
    fillEndColor: Color = Color.Transparent
) {
    if (points.isEmpty()) return

    var selectedIndex by remember { mutableIntStateOf(points.lastIndex) }
    val progress = remember { Animatable(0f) }

    LaunchedEffect(points) {
        progress.snapTo(0f)
        progress.animateTo(1f, animationSpec = tween(900))
    }

    val selectedPoint = points.getOrNull(selectedIndex) ?: points.last()

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Cashflow Velocity (${selectedPoint.label})",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                Text(
                    text = "$currencySymbol${String.format("%,.0f", selectedPoint.value)}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldGleam
                )
            }
            Text(
                text = "Tap nodes to inspect",
                fontSize = 11.sp,
                color = TextTertiary
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .pointerInput(points) {
                        detectTapGestures { offset ->
                            val step = size.width / (points.size - 1).coerceAtLeast(1)
                            val index = ((offset.x + step / 2) / step).toInt().coerceIn(0, points.lastIndex)
                            selectedIndex = index
                        }
                    }
            ) {
                val width = size.width
                val height = size.height
                val paddingBottom = 28f
                val effectiveHeight = height - paddingBottom

                val maxVal = points.maxOfOrNull { it.value }?.takeIf { it > 0 } ?: 1000f
                val minVal = (points.minOfOrNull { it.value } ?: 0f).coerceAtLeast(0f)
                val range = (maxVal - minVal).coerceAtLeast(1f)

                // Draw horizontal subtle grid lines
                val gridSteps = 3
                for (i in 0..gridSteps) {
                    val y = effectiveHeight * (i.toFloat() / gridSteps)
                    drawLine(
                        color = OutlineDark,
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
                    )
                }

                val coords = points.mapIndexed { index, point ->
                    val x = if (points.size > 1) {
                        index * (width / (points.size - 1))
                    } else width / 2
                    val normalized = (point.value - minVal) / range
                    val y = effectiveHeight - (normalized * effectiveHeight * progress.value)
                    Offset(x, y)
                }

                if (coords.size > 1) {
                    val path = Path().apply {
                        moveTo(coords.first().x, coords.first().y)
                        for (i in 0 until coords.size - 1) {
                            val p0 = coords[i]
                            val p1 = coords[i + 1]
                            val cx = (p0.x + p1.x) / 2
                            cubicTo(cx, p0.y, cx, p1.y, p1.x, p1.y)
                        }
                    }

                    val fillPath = Path().apply {
                        addPath(path)
                        lineTo(coords.last().x, effectiveHeight)
                        lineTo(coords.first().x, effectiveHeight)
                        close()
                    }

                    // Draw gradient fill
                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(fillStartColor, fillEndColor),
                            startY = 0f,
                            endY = effectiveHeight
                        )
                    )

                    // Draw glowing stroke line
                    drawPath(
                        path = path,
                        color = lineColor,
                        style = Stroke(width = 3.5f.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                // Draw point markers
                coords.forEachIndexed { i, offset ->
                    val isSelected = i == selectedIndex
                    val radius = if (isSelected) 6.dp.toPx() else 3.5.dp.toPx()
                    val pointColor = if (isSelected) GoldGleam else lineColor

                    drawCircle(
                        color = if (isSelected) ImperialGold.copy(alpha = 0.4f) else Color.Transparent,
                        radius = radius * 2.2f,
                        center = offset
                    )
                    drawCircle(
                        color = pointColor,
                        radius = radius,
                        center = offset
                    )
                    drawCircle(
                        color = Color(0xFF090A0C),
                        radius = radius * 0.45f,
                        center = offset
                    )
                }
            }
        }

        // Horizontal Axis labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            points.forEachIndexed { index, point ->
                val isSelected = index == selectedIndex
                Text(
                    text = point.label,
                    fontSize = 11.sp,
                    color = if (isSelected) ImperialGold else TextTertiary,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
