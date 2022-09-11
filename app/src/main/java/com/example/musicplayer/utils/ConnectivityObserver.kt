package com.example.musicplayer.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observer(): Flow<StatusInternet>

    enum class StatusInternet {
        AVAILABLE, UNAVAILABLE, LOSING, LOST
    }
}