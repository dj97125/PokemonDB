package com.example.android_room_with_a_view.domain

import com.example.android_room_with_a_view.common.FailedCacheResponseException
import com.example.android_room_with_a_view.common.FailedNetworkResponseException
import com.example.android_room_with_a_view.common.InternetCheck
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.response.DataDomain
import com.example.android_room_with_a_view.domain.response.UserDomain
import com.example.android_room_with_a_view.model.local.LocalDataSource
import com.example.android_room_with_a_view.model.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface Repository {
    fun userCatched(): Flow<StateAction>
}

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {


    override fun userCatched(): Flow<StateAction> = flow {
        val connected = InternetCheck()
        val remoteService = remoteDataSource.userCatched()
        if (connected.isConnected()) {
            remoteService.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedUser = stateAction.response as List<DataDomain>
                        val retrievedMessage = stateAction.message
                        emit(StateAction.SUCCESS(retrievedUser, retrievedMessage))
                        localDataSource.insertUser(retrievedUser).collect()


                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedNetworkResponseException()))
                    }
                }
            }
        } else {
            val cache = localDataSource.getAllUsers()
            cache.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedUser = stateAction.response as List<DataDomain>
                        val retrievedMessage = stateAction.message
                        emit(StateAction.SUCCESS(retrievedUser, retrievedMessage))
                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedCacheResponseException()))
                    }
                }

            }

        }
    }

}



