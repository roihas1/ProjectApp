package com.example.projectapp
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


fun generatePurpleThemeColors(count: Int): List<Color> {
    val baseHue = 200f // Base blue hue
    return List(count) { index ->
        val hue = (baseHue + index * 30f) % 360f // Shift hue towards green
        val saturation = 0.5f + Random.nextFloat() * 0.2f // 50-80% saturation
        val lightness = 0.4f + Random.nextFloat() * 0.4f // 40-70% lightness
        Color.hsl(hue, saturation, lightness)
    }

}


fun createSlices(labels: List<String>, fractions: List<Double>): List<Slice> {
    require(labels.size == fractions.size) { "Labels and fractions must have the same size" }
    val colors = generatePurpleThemeColors(labels.size)
    return labels.zip(fractions).mapIndexed { index, (label, fraction) ->
        Slice(label, fraction, colors[index])
    }
}
@Composable
fun PieChartScreen(labels: List<String>, fractions: List<Double>, investAmount: Double) {
    var selectedSlice by remember { mutableStateOf<Slice?>(null) }
    var selectedAngle by remember { mutableStateOf(0f) }

    val slices = remember(labels, fractions) {
        createSlices(labels, fractions)
    }

    Box(
        modifier = Modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center,

    ) {
        PieChart(
            modifier = Modifier
                .width(250.dp)
                .height(250.dp)
                ,
            slices = slices,
            onSliceClicked = { slice, angle ->
                selectedSlice = slice
                selectedAngle = angle
            },
            selectedSlice = selectedSlice,
            investAmount = investAmount
        )

    }
    Table(slices = slices, investAmount = investAmount)
}
@Composable
fun Table(slices: List<Slice>, investAmount: Double) {
    val total = slices.sumOf { it.value.toDouble() }
    val sortedSlices = slices.sortedByDescending { it.value * investAmount / total }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Label",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Amount to invest",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Divider(color = Color.Black, thickness = 1.dp)

        // Display two slices per row
        repeat(sortedSlices.size / 2) { index ->
            val slice1 = sortedSlices[index * 2]
            val slice2 = if (index * 2 + 1 < sortedSlices.size) sortedSlices[index * 2 + 1] else null

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // First slice
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(slice1.color, CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = slice1.label,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "%.1f".format(investAmount * (slice1.value / total.toFloat())),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Second slice (if exists)
                slice2?.let { slice ->
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(slice.color, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = slice.label,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "%.1f".format(investAmount * (slice.value / total.toFloat())),
                        fontSize = 14.sp
                    )
                }
            }
        }

        // If there's an odd number of slices, display the last slice in a separate row
        if (sortedSlices.size % 2 == 1) {
            val lastSlice = sortedSlices.last()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(lastSlice.color, CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = lastSlice.label,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "%.1f".format(investAmount * (lastSlice.value / total.toFloat())),
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    slices: List<Slice>,
    onSliceClicked: (slice: Slice, angle: Float) -> Unit,
    selectedSlice: Slice?,
    investAmount: Double
) {
    val total = slices.sumOf { it.value.toDouble() }
    var startAngle = 0f
    var popupOffset by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val canvasSize = size.height
                    val radius = canvasSize / 2F
                    val center = Offset(radius, radius)
                    val touchAngle = (atan2(offset.y - center.y, offset.x - center.x) * (180 / PI)).toFloat()
                    val normalizedAngle = (touchAngle + 360) % 360

                    var cumulativeAngle = 0f
                    for (slice in slices) {
                        val sweepAngle = 360 * (slice.value / total)
                        if (normalizedAngle in cumulativeAngle..((cumulativeAngle + sweepAngle).toFloat())) {
                            onSliceClicked(slice, normalizedAngle)
                            val sliceAngle = cumulativeAngle + sweepAngle / 2
                            val popupRadius = radius * 0.7 // Adjust this value to position the popup
                            popupOffset = Offset(
                                (center.x + popupRadius * cos(sliceAngle * PI / 180).toFloat()).toFloat(),
                                (center.y + popupRadius * sin(sliceAngle * PI / 180).toFloat()).toFloat()
                            )
                            break
                        }
                        cumulativeAngle += sweepAngle.toFloat()
                    }
                }
            }
        ) {
            val canvasSize = size.minDimension
            val radius = canvasSize / 2
            val center = Offset(radius, radius)
            val strokeWidth = canvasSize * 0.002f

            for (slice in slices) {
                val sweepAngle = 360 * (slice.value / total)
                drawArc(
                    color = slice.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle.toFloat(),
                    useCenter = true,
                    size = Size(canvasSize, canvasSize),
                    topLeft = Offset.Zero
                )

                if (slice == selectedSlice) {
                    // Draw selection indicator
                    // Draw improved selection indicator
                    drawSelectedSliceIndicator(
                        startAngle = startAngle,
                        sweepAngle = sweepAngle.toFloat(),
                        size = Size(canvasSize, canvasSize),
                        strokeWidth = strokeWidth
                    )
                }
                startAngle += sweepAngle.toFloat()
            }
        }

        selectedSlice?.let { slice ->
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(popupOffset.x.toInt(), popupOffset.y.toInt())
            ) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(4.dp),
                    shadowElevation =  10.dp
                ) {
                    Text(
                        text = "${slice.label}: ${"%.1f".format((slice.value / total.toFloat()) * 100)}%",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
fun DrawScope.drawSelectedSliceIndicator(startAngle: Float, sweepAngle: Float, size: Size, strokeWidth: Float) {
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(size.width / 2, size.height / 2)
        arcTo(
            rect = Rect(0f, 0f, size.width, size.height),
            startAngleDegrees = startAngle,
            sweepAngleDegrees = sweepAngle,
            forceMoveTo = false
        )
        close()
    }

    // Draw outer stroke
    drawPath(
        path = path,
        color = Color.White,
        style = Stroke(width = strokeWidth * 3)
    )

    // Draw inner stroke
    drawPath(
        path = path,
        color = Color.Black.copy(alpha = 0.5f),
        style = Stroke(width = strokeWidth)
    )
}

data class Slice(
    val label: String,
    val value: Double,
    val color: Color
)

//@Composable
//@Preview(showBackground = true)
//fun PieChartScreenPreview() {
//    PieChartScreen()
//}
