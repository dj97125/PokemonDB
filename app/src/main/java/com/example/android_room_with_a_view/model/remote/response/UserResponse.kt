package com.example.android_room_with_a_view.model.remote.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val `data`: List<DataResponse>,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)

data class DataResponse(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("last_name")
    val lastName: String?
)