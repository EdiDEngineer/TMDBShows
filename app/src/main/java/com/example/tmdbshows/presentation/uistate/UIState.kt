package com.example.tmdbshows.presentation.uistate

sealed class UIState<out T> {
    data class Success<T : Any>(val body: T, val displayBody: T = body, val transform: Transform = Transform.DEFAULT) :
        UIState<T>()
    data class Failed(val error: Throwable?) : UIState<Nothing>()
    object Loading : UIState<Nothing>()
}

enum class Transform {
    SORT_ALPHABETICALLY,
    DEFAULT
}