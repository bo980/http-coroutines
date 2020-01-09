package com.liang.http

import android.app.Application
import com.liang.http.coroutines.launch
import com.liang.http.coroutines.launchAsync
import kotlinx.coroutines.runBlocking


var Application.request: Request?
    get() {
        return Http.request
    }
    set(value) {
        Http.request = value
    }

private object Http {
    var request: Request? = null
}

suspend fun Any.httpGet(
    tag: String = this::class.java.simpleName,
    url: String = Http.request?.baseUrl ?: "",
    api: String = "",
    headers: Map<String, String> = HashMap(),
    params: Map<String, String> = HashMap()
): Response? {
    return Http.request?.get(tag, url, api, headers, params)
}

fun Any.httpGet(
    tag: String = this::class.java.simpleName,
    url: String = Http.request?.baseUrl ?: "",
    api: String = "",
    headers: Map<String, String> = HashMap(),
    params: Map<String, String> = HashMap(),
    action: (response: Response?) -> Unit
) {
    runBlocking {
        val response = launchAsync {
            httpGet(tag, url, api, headers, params)
        }
        action(response.await())
    }
}

suspend fun Any.httpPost(
    tag: String = this::class.java.simpleName,
    url: String = Http.request?.baseUrl ?: "",
    api: String = "",
    headers: Map<String, String> = HashMap(),
    params: Any?
): Response? {
    return Http.request?.post(tag, url, api, headers, params)
}

fun Any.httpPost(
    tag: String = this::class.java.simpleName,
    url: String = Http.request?.baseUrl ?: "",
    api: String = "",
    headers: Map<String, String> = HashMap(),
    params: Any?,
    action: (response: Response?) -> Unit
) {
    launch {
        val response = launchAsync {
            httpPost(tag, url, api, headers, params)
        }
        action(response.await())
    }
}

fun Any.cancelHttpRequest(tag: String = this::class.java.simpleName) {
    Http.request?.cancel(tag)
}