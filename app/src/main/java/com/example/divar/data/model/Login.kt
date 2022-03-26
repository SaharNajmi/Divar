package com.example.divar.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("status")
    @Expose
    var status: Boolean = false
)