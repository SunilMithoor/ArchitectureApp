package com.sunil.app.domain.utils

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val networkState: Flow<NetworkState>
}

data class NetworkState(
    val isOnline: Boolean,
    val shouldRefresh: Boolean
)
