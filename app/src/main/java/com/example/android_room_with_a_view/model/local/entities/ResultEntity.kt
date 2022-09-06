package com.example.android_room_with_a_view.model.local.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)