package com.example.android_room_with_a_view.domain.response


import com.google.gson.annotations.SerializedName

data class ResultDomain(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)