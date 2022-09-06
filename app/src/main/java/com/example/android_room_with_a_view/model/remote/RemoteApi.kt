package com.example.android_room_with_a_view.model.remote

import com.example.android_room_with_a_view.common.END_POINT
import com.example.android_room_with_a_view.common.END_POINT_DETAILS
import com.example.android_room_with_a_view.model.remote.response.DetailsResponse
import com.example.android_room_with_a_view.model.remote.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {
    @GET(END_POINT)
    suspend fun getPokemonList(): Response<PokemonResponse>

    @GET(END_POINT_DETAILS)
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): Response<DetailsResponse>


}