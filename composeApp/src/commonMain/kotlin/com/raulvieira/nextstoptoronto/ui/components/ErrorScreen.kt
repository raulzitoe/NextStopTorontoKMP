package com.raulvieira.nextstoptoronto.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.raulvieira.nextstoptoronto.ui.theme.NextStopTorontoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ErrorScreen(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    NextStopTorontoTheme {
        Surface {
            ErrorScreen(message = "Something went wrong")
        }
    }
}