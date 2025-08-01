package com.sina.news

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sina.home.details.NewsDetailScreen
import com.sina.home.home.HomeScreen
import com.sina.home.home.HomeVM
import com.sina.home.home.NewsAction
import org.koin.androidx.compose.koinViewModel
import kotlinx.coroutines.launch
import kotlin.let

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveHomeListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: HomeVM = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val coroutineScope = rememberCoroutineScope()

    ObserveAsEvents(events = viewModel.intents) { intent ->
        when (intent) {
            is HomeVM.Intents.ShowError ->
                Toast.makeText(context, intent.message.asString(context), Toast.LENGTH_SHORT).show()
        }
    }

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                HomeScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)

                        when (action) {
                            is NewsAction.OnNewsClicked -> {
                                coroutineScope.launch {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail
                                    )
                                }
                            }

                            is NewsAction.OnSearchChanged -> {}
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        detailPane = {
            AnimatedPane {
                state.selectedNews?.let { article ->
                    NewsDetailScreen(article = article)
                } ?: Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No article selected", style = MaterialTheme.typography.bodyLarge)
                }
            }
        },
        modifier = modifier
    )
}

