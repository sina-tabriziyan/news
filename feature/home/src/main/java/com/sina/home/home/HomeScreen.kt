package com.sina.home.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sina.home.components.ArticleItem


@Composable
fun HomeScreen(
    state: HomeVM.States,
    onAction: (NewsAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.search,
            onValueChange = { onAction(NewsAction.OnSearchChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search articles...") }
        )

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.newsList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No news found.")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.newsList) { article ->
                    ArticleItem(article = article, onClick = {
                        onAction(NewsAction.OnNewsClicked(article))
                    })
                }
            }
        }

        state.error?.let {
            Text(
                text = it.asString(),
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}