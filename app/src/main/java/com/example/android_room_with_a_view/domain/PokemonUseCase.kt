package com.example.android_room_with_a_view.domain

import javax.inject.Inject

class PokemonUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.pokemonCatched()
}