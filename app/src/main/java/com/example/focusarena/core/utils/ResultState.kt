package com.example.focusarena.core.utils



sealed class ResultState<out T>{
    object Loading: ResultState<Nothing>();
    data class Success<out T>(val data: T): ResultState<Nothing>()
    data class Error(val message: String): ResultState<Nothing>()
}