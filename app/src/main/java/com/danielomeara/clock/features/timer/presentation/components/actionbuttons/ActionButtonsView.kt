package com.danielomeara.clock.features.timer.presentation.components.actionbuttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danielomeara.clock.core.util.rememberWindowInfo

@Composable
fun ActionButtonsView(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val windowInfo = rememberWindowInfo()
    when(windowInfo.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                content()
            }
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            Column(
                modifier = modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                content()
            }
        }
    }
}