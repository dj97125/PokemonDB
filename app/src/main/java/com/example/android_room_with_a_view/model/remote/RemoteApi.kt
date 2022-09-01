package com.example.android_room_with_a_view.model.remote

import com.example.android_room_with_a_view.common.END_POINT
import com.example.android_room_with_a_view.model.remote.response.DataResponse
import com.example.android_room_with_a_view.model.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApi {
    @GET(END_POINT)
    suspend fun getUserList(): Response <UserResponse>


}