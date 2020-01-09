package com.liang.http


interface Request {

    var baseUrl: String

    suspend fun post(
        tag: String,
        url: String,
        api: String,
        headers: Map<String, String> = HashMap(),
        params: Any?
    ): Response

    suspend fun get(
        tag: String,
        url: String,
        api: String,
        headers: Map<String, String> = HashMap(),
        params: Map<String, String> = HashMap()
    ): Response

    fun cancel(tag: String)
}