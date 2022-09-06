package com.example.android_room_with_a_view.model.remote.response


import com.google.gson.annotations.SerializedName

data class DetailsResponse(
    @SerializedName("base_happiness")
    val baseHappiness: Int,
    @SerializedName("capture_rate")
    val captureRate: Int,
    @SerializedName("color")
    val color: ColorResponse,
    @SerializedName("egg_groups")
    val eggGroups: List<EggGroupResponse>,
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChainResponse,
)

data class ColorResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class EggGroupResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class EvolutionChainResponse(
    @SerializedName("url")
    val url: String
)