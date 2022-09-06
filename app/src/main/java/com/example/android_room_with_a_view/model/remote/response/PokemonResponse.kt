package com.example.android_room_with_a_view.model.remote.response


import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<ResultResponse>
)

data class ResultResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)