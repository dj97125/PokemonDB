package com.example.android_room_with_a_view.model.remote

import com.example.android_room_with_a_view.common.FailedNetworkResponseException
import com.example.android_room_with_a_view.common.NullResponseException
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.response.DataDomain
import com.example.android_room_with_a_view.domain.response.UserDomain
import com.example.android_room_with_a_view.model.remote.RemoteApi
import com.example.android_room_with_a_view.model.remote.response.DataResponse
import com.example.android_room_with_a_view.model.remote.response.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface RemoteDataSource {
    fun userCatched(): Flow<StateAction>
}

class RemoteDataSourceImpl @Inject constructor(
    private val service: RemoteApi
) : RemoteDataSource {

    override fun userCatched(): Flow<StateAction> = flow {
        val response = service.getUserList()
        if (response.isSuccessful) {
            response.body()?.let { result ->
                emit(StateAction.SUCCESS(result.data.toDomainDataModel(), "Data from Network"))
            } ?: throw NullResponseException()
        } else {
            throw FailedNetworkResponseException()
        }
    }


}


private fun List<DataResponse>.toDomainDataModel(): List<DataDomain> = map {
    it.toDomainDataModel()
}



private fun UserResponse.toDomainUserModel(): UserDomain =
    UserDomain(data = data.toDomainDataModel(), page, perPage, total, totalPages)

private fun DataResponse.toDomainDataModel(): DataDomain =
    DataDomain(avatar, email, firstName, id, lastName)


