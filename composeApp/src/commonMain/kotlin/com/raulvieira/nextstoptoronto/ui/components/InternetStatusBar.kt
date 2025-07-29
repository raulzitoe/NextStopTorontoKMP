package com.raulvieira.nextstoptoronto.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifi4Bar
import androidx.compose.material.icons.filled.SignalWifiBad
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raulvieira.nextstoptoronto.ui.theme.NextStopTorontoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InternetStatusBar(isConnected: Boolean) {
    val backgroundColor by animateColorAsState(if (isConnected) Color(0xFF3B9B3F) else Color.Red)
    val message = if (isConnected) "Back Online" else "No Internet Connection"
    val iconResource = if (isConnected) {
        Icons.Filled.SignalWifi4Bar
    } else {
        Icons.Filled.SignalWifiBad
    }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(vertical = 8.dp), Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(iconResource, "Connectivity Icon", modifier = Modifier.padding(horizontal = 5.dp))
            Text(message, fontSize = 15.sp)
        }

    }
}

@Preview
@Composable
fun InternetStatusBarPreview() {
    NextStopTorontoTheme {
        Surface {
            Column {
                InternetStatusBar(false)
                Spacer(modifier = Modifier.size(10.dp))
                InternetStatusBar(true)
            }
        }
    }
}
