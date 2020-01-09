package com.liang.http.coroutines

import kotlinx.coroutines.*


@JvmOverloads
fun launch(
    delay: Long = 0,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend () -> Unit
): Job {
    return GlobalScope.launch(start = start) {
        delay(delay)
        block()
    }
}

fun <T> launchAsync(
    block: suspend () -> T
): Deferred<T> {
    return GlobalScope.async(start = CoroutineStart.LAZY) {
        block()
    }

}
