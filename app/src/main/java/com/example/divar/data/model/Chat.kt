package com.example.divar.data.model

data class Chat(
    val id: Int = 0,
    var bannerID: Int = 0,
    var bannerTitle: String = "",
    var bannerImage: String = "",
    var receiver: String = "",
    var sender: String = "",
    var message: String = ""
)