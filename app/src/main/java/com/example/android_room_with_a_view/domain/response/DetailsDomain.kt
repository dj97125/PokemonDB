package com.example.android_room_with_a_view.domain.response

import com.google.gson.annotations.SerializedName

data class DetailsDomain(
    @SerializedName("base_happiness")
    val baseHappiness: Int,
    @SerializedName("capture_rate")
    val captureRate: Int,
    @SerializedName("color")
    val color: ColorDomain,
    @SerializedName("egg_groups")
    val eggGroups: List<EggGroupDomain>,
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChainDomain,
)

data class ColorDomain(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class EggGroupDomain(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class EvolutionChainDomain(
    @SerializedName("url")
    val url: String
)