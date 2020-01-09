package com.liang.http

import android.app.Application
import com.liang.http.coroutines.launch
import com.liang.http.coroutines.launchAsync
import kotlinx.coroutines.runBlocking


private var Application.request: Request?
    get() {
        return Http.request
    }
    set(value) {
        Http.request = value
    }

private object Http {
    var request: Request? = null
}

suspend fun <T> Any.httpGet(
    tag: String = this::class.java.simpleName,
    url: String = Http.request?.baseUrl ?: "",
    api: String = "",
    headers: Map<String, String> = HashMap(),
    params: Map<String, String> = HashMap()
): T? {
    return Http.request?.get(tag, url, api, headers, params)
}

fun <T> Any.httpGet(
    tag: String = this::class.java.simpleName,
    url: String = Http.request?.baseUrl ?: "",
    api: String = "",
    headers: Map<String, String> = HashMap(),
    params: Map<String, String> = HashMap(),
    action: (response: T?) -> Unit
) {
    runBlocking {
        val response = launchAsync {
            httpGet<T>(tag, url, api, headers, params)
        }
        action(response.await())
    }
}

suspend fun <T> Any.httpPost(
    tag: String = this::class.java.simpleName,
    url: String = Http.request?.baseUrl ?: "",
    api: String = "",
    headers: Map<String, String> = HashMap(),
    params: Any?
): T? {
    return Http.request?.post(tag, url, api, headers, params)
}

fun <T> Any.httpPost(
    tag: String = this::class.java.simpleName,
    url: String = Http.request?.baseUrl ?: "",
    api: String = "",
    headers: Map<String, String> = HashMap(),
    params: Any?,
    action: (response: T?) -> Unit
) {
    launch {
        val response = launchAsync {
            httpPost<T>(tag, url, api, headers, params)
        }
        action(response.await())
    }
}

fun Any.cancelHttpRequest(tag: String = this::class.java.simpleName) {
    Http.request?.cancel(tag)
}