package com.example.android_room_with_a_view.model.remote

import com.example.android_room_with_a_view.common.FailedNetworkResponseException
import com.example.android_room_with_a_view.common.NullResponseException
import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.response.*
import com.example.android_room_with_a_view.model.remote.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface RemoteDataSource {
    fun pokemonCatched(): Flow<StateAction>
    fun detailsCatched(pokemon: String): Flow<StateAction>
}

class RemoteDataSourceImpl @Inject constructor(
    private val service: RemoteApi
) : RemoteDataSource {

    override fun pokemonCatched(): Flow<StateAction> = flow {
        val response = service.getPokemonList()
        if (response.isSuccessful) {
            response.body()?.let { result ->
                emit(StateAction.SUCCESS(result.results.toDomainDataModel(), "Data from Network"))
            } ?: throw NullResponseException()
        } else {
            throw FailedNetworkResponseException()
        }
    }

    override fun detailsCatched(pokemon: String): Flow<StateAction> = flow {
        val response = service.getPokemonDetails(pokemon)

        if (response.isSuccessful) {
            response.body()?.let { result ->
                emit(StateAction.SUCCESS(result.toDomainDetailsModel(), "Data from Network"))
            } ?: throw NullResponseException()
        } else {
            throw FailedNetworkResponseException()
        }
    }


}


private fun List<ResultResponse>.toDomainDataModel(): List<ResultDomain> = map {
    it.toDomainDataModel()
}



private fun List<EggGroupResponse>.toDomainEggModel(): List<EggGroupDomain> = map {
    it.toDomainEggModel()
}


private fun ResultResponse.toDomainDataModel(): ResultDomain =
    ResultDomain(name, url)

private fun ColorResponse.toDomainColorModel(): ColorDomain =
    ColorDomain(name, url)

private fun EggGroupResponse.toDomainEggModel(): EggGroupDomain =
    EggGroupDomain(name, url)



private fun EvolutionChainResponse.toDomainEvolutionModel(): EvolutionChainDomain =
    EvolutionChainDomain(url)

private fun DetailsResponse.toDomainDetailsModel(): DetailsDomain =
    DetailsDomain(
        baseHappiness,
        captureRate,
        color = color.toDomainColorModel(),
        eggGroups = eggGroups.toDomainEggModel(),
        evolutionChain = evolutionChain.toDomainEvolutionModel()
    )


