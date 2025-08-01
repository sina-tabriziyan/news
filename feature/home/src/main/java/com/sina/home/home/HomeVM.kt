package com.sina.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain.INewsRepository
import com.sina.domain.model.News
import com.sina.library.response.Result
import com.sina.library.response.UiText
import com.sina.library.response.asUiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeVM(private val repository: INewsRepository) : ViewModel() {


    private val _state = MutableStateFlow(States.Initial)
    val state: StateFlow<States> = _state.asStateFlow()

    private val _intents: Channel<Intents> = Channel(Channel.BUFFERED)
    val intents: Flow<Intents> = _intents.receiveAsFlow()
    private val _searchQuery = MutableStateFlow("")

    sealed interface Intents {
        data class ShowError(val message: UiText, val onEvent: (Intents) -> Unit) : Intents
    }

    init {
        fetchData()
    }

    private fun fetchData() {
        _state.update { copy(isLoading = true, error = null) }

        launchIo {
            _searchQuery.debounce(300)
                .distinctUntilChanged()
                .onEach { query ->
                    _state.update { copy(isLoading = true) }
                    when (val result = repository.getYesterdayNews(query)) {
                        is Result.Success -> {
                            _state.update {
                                copy(
                                    newsList = result.data.articles,
                                    isLoading = false,
                                    search = query
                                )
                            }
                        }

                        is Result.Error -> {
                            _state.update {
                                copy(
                                    error = result.error.asUiText(),
                                    isLoading = false,
                                    search = query,
                                    newsList = emptyList()
                                )
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun updateSearchQuery(search: String) {
        _state.update { copy(search = search) }
    }

    private fun launchIo(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    fun <T> MutableStateFlow<T>.update(update: T.() -> T) {
        value = value.update()
    }

    fun onAction(action: NewsAction) {
        launchIo {
            when (action) {
                is NewsAction.OnNewsClicked -> selectNews(action.article)
                is NewsAction.OnSearchChanged -> {
                    _searchQuery.value = action.query
                    _state.update { copy(search = action.query) }
                }
            }
        }
    }

    private fun selectNews(article: News.Article) {
        _state.update { copy(selectedNews = article) }
        launchIo {
            when(val result= repository.getArticle(article.id)){
                is Result.Success -> {
                    _state.update { copy(selectedNews = result.data) }
                }
                is Result.Error -> {
                    _state.update { copy(error = result.error.asUiText()) }
                }
            }
        }
    }


    data class States(
        val isLoading: Boolean,
        val search: String = "",
        val error: UiText? = null,
        val newsList: List<News.Article> = emptyList(),
        val selectedNews: News.Article? = null
    ) {
        companion object {
            val Initial = States(true)
        }
    }

}
sealed interface NewsAction {
    data class OnSearchChanged(val query: String) : NewsAction
    data class OnNewsClicked(val article: News.Article) : NewsAction
}
