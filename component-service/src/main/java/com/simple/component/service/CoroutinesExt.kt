package com.simple.component.service

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchCollect(lifecycleOwner: LifecycleOwner, action: suspend (T) -> Unit) = lifecycleOwner.lifecycleScope.launch {
    collect { data ->
        action(data)
    }
}

fun <T> Flow<T>.launchCollect(scope: CoroutineScope, action: suspend (T) -> Unit) = scope.launch {
    collect { data ->
        action(data)
    }
}
