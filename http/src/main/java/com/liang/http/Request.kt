package com.liang.http


interface Request {

    var baseUrl: String

    suspend fun<T> post(
        tag: String,
        url: String,
        api: String,
        headers: Map<String, String> = HashMap(),
        params: Any?
    ): T

    suspend fun<T> get(
        tag: String,
        url: String,
        api: String,
        headers: Map<String, String> = HashMap(),
        params: Map<String, String> = HashMap()
    ): T

    fun cancel(tag: String)
}