package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

data class CategorySlice(
    val name: String,
    val amount: Double,
    val color: Color
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DonutCategoryChart(
    slices: List<CategorySlice>,
    totalAmount: Double,
    currencySymbol: String,
    modifier: Modifier = Modifier
) {
    if (slices.isEmpty() || totalAmount <= 0) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(140.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No categorized expenses recorded yet", color = TextTertiary, fontSize = 13.sp)
        }
        return
    }

    val progress = remember { Animatable(0f) }
    LaunchedEffect(slices) {
        progress.snapTo(0f)
        progress.animateTo(1f, animationSpec = tween(800))
    }

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(150.dp)) {
                var startAngle = -90f
                val strokeWidth = 18.dp.toPx()

                slices.forEach { slice ->
                    val sweepAngle = ((slice.amount / totalAmount).toFloat() * 360f) * progress.value
                    if (sweepAngle > 0f) {
                        drawArc(
                            color = slice.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle - 2f, // subtle gap
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        startAngle += sweepAngle
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Total Outflow",
                    fontSize = 11.sp,
                    color = TextTertiary
                )
                Text(
                    text = "$currencySymbol${String.format("%,.0f", totalAmount)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Legend chips
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            slices.forEach { slice ->
                val percentage = (slice.amount / totalAmount * 100).toInt()
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(slice.color)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${slice.name} ($percentage%)",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}
