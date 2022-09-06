package com.example.android_room_with_a_view.common

import com.example.android_room_with_a_view.domain.response.DetailsDomain
import com.example.android_room_with_a_view.domain.response.ResultDomain

interface OnPokemonClicked {
    fun pokemonClicked(pokemon: ResultDomain)
}