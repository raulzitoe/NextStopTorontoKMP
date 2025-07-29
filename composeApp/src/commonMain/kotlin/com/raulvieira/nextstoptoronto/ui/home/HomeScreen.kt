package com.raulvieira.nextstoptoronto.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulvieira.nextstoptoronto.domain.model.Line
import com.raulvieira.nextstoptoronto.ui.components.AnimatedSearchField
import com.raulvieira.nextstoptoronto.ui.components.ErrorScreen
import com.raulvieira.nextstoptoronto.ui.components.InternetStatusBar
import com.raulvieira.nextstoptoronto.ui.components.LoadingScreen
import com.raulvieira.nextstoptoronto.ui.components.ScrollToTopButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    var searchVisible by rememberSaveable { mutableStateOf(false) }
    val showUpdateDialog = false // TODO
    val updateProgress = 0.4f // TODO
    val isInternetOn = true // TODO
    var internetStatusBarVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isInternetOn) {
        internetStatusBarVisible = if (!isInternetOn) {
            true
        } else {
            viewModel.onRefresh()
            delay(2000)
            false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Next Stop Toronto")
                },
                actions = {
                    IconButton(onClick = { searchVisible = !searchVisible }) {
                        Icon(Icons.Filled.Search, contentDescription = "Localized description")
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                AnimatedVisibility(
                    visible = internetStatusBarVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    InternetStatusBar(isConnected = isInternetOn)
                }
                when (val state = uiState) {
                    is HomeUiSate.Loading -> LoadingScreen()
                    is HomeUiSate.Success -> {
                        HomeScreenContent(
                            lines = state.lines,
                            searchVisible = searchVisible,
                            onNavigate = { onNavigate(it) },
                            showUpdateDialog = showUpdateDialog,
                            updateProgress = updateProgress
                        )
                    }

                    is HomeUiSate.Error -> ErrorScreen(message = state.message)
                }
            }
        }
    }
}

@Composable
private fun HomeScreenContent(
    lines: List<Line>,
    searchVisible: Boolean,
    onNavigate: (String) -> Unit,
    showUpdateDialog: Boolean,
    updateProgress: Float
) {
    val focusRequester = remember { FocusRequester() }
    var searchedText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    LaunchedEffect(searchVisible) {
        if (searchVisible) {
            focusRequester.requestFocus()
        }
    }

    if (showUpdateDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = { Text("Updating Stops") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = (updateProgress * 100).toInt().toString() + "%"
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "Downloading updated stop locations from the TTC to show on your map, " +
                                "this happens every few months"
                    )
                }
            }
        )
    }

    Column {
        AnimatedSearchField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .focusRequester(focusRequester),
            searchVisible = searchVisible,
            searchedText = searchedText,
            onValueChange = { searchedText = it },
            onClear = { searchedText = TextFieldValue("") }
        )
        RouteGrid(
            modifier = Modifier.padding(horizontal = 5.dp),
            lines = lines,
            searchedText = searchedText,
            onClickRoute = { onNavigate(it) })
    }
}

@Composable
private fun RouteGrid(
    modifier: Modifier = Modifier,
    lines: List<Line>,
    searchedText: TextFieldValue,
    onClickRoute: (String) -> Unit
) {
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var showScrollButton by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.collect {
            if (it > 0) {
                showScrollButton = true
                delay(800)
                showScrollButton = false
            }
        }
    }

    Box {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Adaptive(minSize = 150.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            state = listState
        ) {
            items(items = lines.filter {
                val words = searchedText.text.split("\\s+".toRegex()).map { word ->
                    word.replace("""^[,.]|[,.]$""".toRegex(), "")
                }
                var containsWord = true
                words.forEach { word ->
                    containsWord =
                        containsWord && it.title.contains(word, ignoreCase = true)
                }
                containsWord

            },
                key = { it.routeTag }
            ) { line ->
                RouteCard(
                    line = line,
                    onClick = { onClickRoute(line.routeTag) }
                )
            }
        }
        ScrollToTopButton(
            onClick = { coroutineScope.launch { listState.animateScrollToItem(0) } },
            showButton = showScrollButton
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RouteCard(
    line: Line,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.wrapContentSize(), onClick = onClick) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Text(
                text = line.title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        lines = listOf(
            Line(title = "41 - Keele", routeTag = "41"),
            Line(title = "29 - Dufferin", routeTag = "29"),
            Line(title = "76 - Royal York", routeTag = "76"),

        ),
        searchVisible = true,
        onNavigate = {},
        showUpdateDialog = false,
        updateProgress = 0.5f
    )
}
