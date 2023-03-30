package com.hcl.upskill.util

sealed class NetworkResult<out T> {
    object Loading : NetworkResult<Nothing>()
    object Default : NetworkResult<Nothing>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val error: String) : NetworkResult<Nothing>()
}