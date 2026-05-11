package com.example.tmdbshows.presentation.uistate

sealed class UIState<out T> {
    data class Success<T : Any>(val body: T): UIState<T>()
    data class Failed(val error: Throwable): UIState<Nothing>()
    object Loading: UIState<Nothing>()
}

enum class SuccessTransform {
    SORT_ALPHABETICALLY,
    DEFAULT
}