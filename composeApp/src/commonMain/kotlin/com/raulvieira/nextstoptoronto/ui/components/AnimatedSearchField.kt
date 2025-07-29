package com.raulvieira.nextstoptoronto.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.raulvieira.nextstoptoronto.ui.theme.NextStopTorontoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedSearchField(
    searchVisible: Boolean,
    searchedText: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = searchVisible) {
        TextField(
            modifier = modifier,
            value = searchedText,
            onValueChange = { onValueChange(it) },
            label = { Text("Search") },
            trailingIcon = {
                IconButton(onClick = { onClear() }) {
                    Icon(
                        Icons.Outlined.Cancel,
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun AnimatedSearchFieldPreview() {
    NextStopTorontoTheme {
        Surface {
            AnimatedSearchField(
                searchVisible = true,
                searchedText = TextFieldValue(""),
                onValueChange = {},
                onClear = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
