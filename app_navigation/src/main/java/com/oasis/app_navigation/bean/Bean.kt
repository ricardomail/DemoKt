package com.oasis.app_navigation.bean

data class NaviChild(val id: Int, val link: String, val title: String)
data class Navi(val cid: Int, val name: String, val articles: List<NaviChild>)
data class SysChild(val id: Int, val name: String)
data class Sys(val id: Int, val name: String, val children: List<SysChild>)

data class NaviItemEvent(
    var p1: Int,
    var p2: Int,
)