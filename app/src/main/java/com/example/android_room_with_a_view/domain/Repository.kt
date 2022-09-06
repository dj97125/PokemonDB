package com.example.android_room_with_a_view.domain

import android.util.Log
import com.example.android_room_with_a_view.common.FailedCacheResponseException
import com.example.android_room_with_a_view.common.FailedNetworkResponseException
import com.example.android_room_with_a_view.common.InternetCheck
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.response.DetailsDomain
import com.example.android_room_with_a_view.domain.response.ResultDomain
import com.example.android_room_with_a_view.model.local.LocalDataSource
import com.example.android_room_with_a_view.model.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface Repository {
    fun pokemonCatched(): Flow<StateAction>
    fun detailsCatched(pokemon: String): Flow<StateAction>
}

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {


    override fun pokemonCatched(): Flow<StateAction> = flow {
        val connected = InternetCheck()
        val remoteService = remoteDataSource.pokemonCatched()
        if (connected.isConnected()) {
            remoteService.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedPokemon = stateAction.response as List<ResultDomain>
                        val retrievedMessage = stateAction.message
                        emit(StateAction.SUCCESS(retrievedPokemon, retrievedMessage))
                        localDataSource.insertPokemon(retrievedPokemon).collect()


                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedNetworkResponseException()))
                    }
                }
            }
        } else {
            val cache = localDataSource.getAllPokemons()
            cache.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedPokemon = stateAction.response as List<ResultDomain>
                        val retrievedMessage = stateAction.message
                        emit(StateAction.SUCCESS(retrievedPokemon, retrievedMessage))
                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedCacheResponseException()))
                    }
                }

            }

        }
    }

    override fun detailsCatched(pokemon: String): Flow<StateAction> = flow {
        val connected = InternetCheck()
        val remoteService = remoteDataSource.detailsCatched(pokemon)
        if (connected.isConnected()) {
            remoteService.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedPokemon = stateAction.response as DetailsDomain
                        val retrievedMessage = stateAction.message
                        emit(StateAction.SUCCESS(retrievedPokemon, retrievedMessage))
//                        localDataSource.insertDetail(retrievedPokemon).collect()


                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedNetworkResponseException()))
                    }
                }
            }
        }
//        else {
//            val cache = localDataSource.getAllDetail()
//            cache.collect { stateAction ->
//                when (stateAction) {
//                    is StateAction.SUCCESS<*> -> {
//                        val retrievedPokemon = stateAction.response as DetailsDomain
//                        val retrievedMessage = stateAction.message
//                        emit(StateAction.SUCCESS(retrievedPokemon, retrievedMessage))
//                    }
//                    is StateAction.ERROR -> {
//                        emit(StateAction.ERROR(FailedCacheResponseException()))
//                    }
//                }
//
//            }
//
//        }
    }

}



