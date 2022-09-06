//package com.example.android_room_with_a_view.model.local.entities
//
//
//import androidx.room.Embedded
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.google.gson.annotations.SerializedName
//
//
//@Entity
//data class DetailsEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    val baseHappiness: Int,
//    val captureRate: Int,
//    @Embedded
//    val color: ColorEntity,
//    @Embedded
//    val eggGroups: List<EggGroupEntity>,
//    @Embedded
//    val evolutionChain: EvolutionChainEntity,
//)
//
//data class ColorEntity(
//    val name: String,
//    val url: String
//)
//
//data class EggGroupEntity(
//    val name: String,
//    val url: String
//)
//
//data class EvolutionChainEntity(
//    val url: String
//)