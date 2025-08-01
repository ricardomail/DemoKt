package com.oasis.app_user.bean

/**
 * {
 * 		"admin": false,
 * 		"chapterTops": [],
 * 		"coinCount": 10,
 * 		"collectIds": [],
 * 		"email": "",
 * 		"icon": "",
 * 		"id": 168514,
 * 		"nickname": "ricardomail",
 * 		"password": "",
 * 		"publicName": "ricardomail",
 * 		"token": "",
 * 		"type": 0,
 * 		"username": "ricardomail"
 * 	}
 */

data class LoginBean(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)