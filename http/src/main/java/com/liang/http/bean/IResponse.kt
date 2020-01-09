package com.liang.http.bean

//服务器返回数据结构
data class Response(
    val code: Int,
    val message: String,
    val data: String?
)
