package com.example.android_room_with_a_view.model.local

import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.response.DataDomain
import com.example.android_room_with_a_view.model.local.entities.DataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LocalDataSource {
    fun insertUser(userEntity: List<DataDomain>): Flow<StateAction>
    fun getAllUsers(): Flow<StateAction>


}

class LocalDataSourceImpl @Inject constructor(
    private val userDao: UsersDao
) : LocalDataSource {
    override fun insertUser(userEntity: List<DataDomain>): Flow<StateAction> = flow {
        userDao.insertUser(userEntity.fromDomainUserModel())
    }

    override fun getAllUsers(): Flow<StateAction> = flow {
        val cache = userDao.getAllUsers()
        cache.let { emit(StateAction.SUCCESS(it.toDomainUsersModel(), "Data From Cache")) }
    }

}


private fun DataEntity.toDomainUserModel(): DataDomain =
    DataDomain(avatar, email, firstName, id, lastName)

private fun List<DataEntity>.toDomainUsersModel(): List<DataDomain> = map {
    it.toDomainUserModel()
}


private fun List<DataDomain>.fromDomainUserModel(): List<DataEntity> = map {
    it.fromDomainDataModel()
}

private fun DataDomain.fromDomainDataModel(): DataEntity =
    DataEntity(avatar, email, firstName, id, lastName)















