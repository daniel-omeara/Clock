package com.danielomeara.clock.features.timer.presentation.components.timer

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.danielomeara.clock.core.util.rememberWindowInfo
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RoundSlider(
    sliderValue: Double,
    onSelectedTimerValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    isSetTimerMode: Boolean
) {
    var radius by remember {
        mutableStateOf(0f)
    }

    var shapeCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var handleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    val arcColor = MaterialTheme.colors.secondary
    val handleColor = MaterialTheme.colors.primary

    val windowInfo = rememberWindowInfo()

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    handleCenter += dragAmount
                    change.consumeAllChanges()
                    onSelectedTimerValueChange(getRotationAngle(handleCenter, shapeCenter))
                }
            }
            .padding(30.dp)
    ) {
        shapeCenter = center

        radius = size.minDimension / 2

        val x = (shapeCenter.x + cos(Math.toRadians(sliderValue)) * radius).toFloat()
        val y = (shapeCenter.y + sin(Math.toRadians(sliderValue)) * radius).toFloat()

        handleCenter = Offset(x, y)

        drawCircle(color = Color.Black.copy(alpha = 0.10f), style = Stroke(20f), radius = radius)

        val topLeft = when(windowInfo.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Offset(
                    x = 0f,
                    y = (size.maxDimension - size.minDimension) / 2
                )
            }
            else -> {
                Offset(
                    x = (size.maxDimension - size.minDimension) / 2,
                    y = 0f
                )
            }

        }
        drawArc(
            color = arcColor,
            startAngle = 0f,
            sweepAngle = sliderValue.toFloat(),
            useCenter = false,
            topLeft = topLeft,
            size = Size(size.minDimension, size.minDimension),
            style = Stroke(20f)
        )

        if (isSetTimerMode) {
            drawCircle(color = handleColor, center = handleCenter, radius = 60f)
        }
    }
}

private fun getRotationAngle(currentPosition: Offset, center: Offset): Double {
    val (dx, dy) = currentPosition - center
    val theta = atan2(dy, dx).toDouble()

    var angle = Math.toDegrees(theta)

    if (angle < 0) {
        angle += 360.0
    }
    return angle
}