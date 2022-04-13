package com.danielomeara.clock.features.timer.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RoundSlider(
    angle: MutableState<Double>,
    modifier: Modifier = Modifier
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



    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    handleCenter += dragAmount

                    angle.value = getRotationAngle(handleCenter, shapeCenter)
                    change.consumeAllChanges()
                }
            }
            .padding(30.dp)

    ) {
        shapeCenter = center

        radius = size.minDimension / 2

        val x = (shapeCenter.x + cos(Math.toRadians(angle.value)) * radius).toFloat()
        val y = (shapeCenter.y + sin(Math.toRadians(angle.value)) * radius).toFloat()

        handleCenter = Offset(x, y)

        drawCircle(color = Color.Black.copy(alpha = 0.10f), style = Stroke(20f), radius = radius)

        drawCircle(color = Color.Cyan, center = handleCenter, radius = 60f)
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