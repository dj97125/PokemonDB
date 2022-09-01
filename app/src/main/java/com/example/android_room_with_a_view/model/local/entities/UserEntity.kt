package com.example.android_room_with_a_view.model.local.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class DataEntity(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @SerializedName("last_name")
    val lastName: String?
)