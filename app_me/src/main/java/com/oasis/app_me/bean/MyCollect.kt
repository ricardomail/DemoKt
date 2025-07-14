package com.oasis.app_me.bean

data class MyCollectDetail(
    val author: String,
    val chapterName: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val title: String,
)

data class MyCollect(
    val curPage: Int,
    val datas: List<MyCollectDetail>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)