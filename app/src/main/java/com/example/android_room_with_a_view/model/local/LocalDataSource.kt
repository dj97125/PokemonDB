package com.example.android_room_with_a_view.model.local

import com.example.android_room_with_a_view.common.StateAction
import com.example.android_room_with_a_view.domain.response.*
import com.example.android_room_with_a_view.model.local.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LocalDataSource {
    fun insertPokemon(PokemonEntity: List<ResultDomain>): Flow<StateAction>
    fun getAllPokemons(): Flow<StateAction>
//    fun insertDetail(DetailEntity: DetailsDomain): Flow<StateAction>
//    fun getAllDetail(): Flow<StateAction>


}

class LocalDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
) : LocalDataSource {
    override fun insertPokemon(PokemonEntity: List<ResultDomain>): Flow<StateAction> = flow {
        pokemonDao.insertPokemon(PokemonEntity.fromDomainPokemonModel())
    }

    override fun getAllPokemons(): Flow<StateAction> = flow {
        val cache = pokemonDao.getAllPokemons()
        cache.let { emit(StateAction.SUCCESS(it.toDomainPokemonModel(), "Data From Cache")) }

    }

//    override fun insertDetail(DetailEntity: DetailsDomain): Flow<StateAction> = flow {
//        pokemonDao.insertDetail(DetailEntity.fromDomainDetailsModel())
//    }
//
//    override fun getAllDetail(): Flow<StateAction> = flow {
//        val cache = pokemonDao.getAllDetails()
//        cache.let { emit(StateAction.SUCCESS(it.toDomainDetailsModel(), "Data From Cache")) }
//    }

}

//private fun List<EggGroupEntity>.toDomainEggModel(): List<EggGroupDomain> = map {
//    it.toDomainEggModel()
//}
//
//private fun EggGroupEntity.toDomainEggModel(): EggGroupDomain =
//    EggGroupDomain(name, url)

private fun ResultEntity.toDomainPokemonModel(): ResultDomain =
    ResultDomain(name, url)

//private fun ColorEntity.toDomainColorModel(): ColorDomain =
//    ColorDomain(name, url)
//
//private fun EvolutionChainEntity.toDomainEvolutionModel(): EvolutionChainDomain =
//    EvolutionChainDomain(url)
//
//private fun DetailsEntity.toDomainDetailsModel(): DetailsDomain =
//    DetailsDomain(
//        baseHappiness,
//        captureRate,
//        color = color.toDomainColorModel(),
//        eggGroups = eggGroups.toDomainEggModel(),
//        evolutionChain = evolutionChain.toDomainEvolutionModel()
//    )

private fun List<ResultEntity>.toDomainPokemonModel(): List<ResultDomain> = map {
    it.toDomainPokemonModel()
}


private fun List<ResultDomain>.fromDomainPokemonModel(): List<ResultEntity> = map {
    it.fromDomainPokemonModel()
}


//
//private fun DetailsDomain.fromDomainDetailsModel(): DetailsEntity =
//    DetailsEntity(
//        id = 0,
//        baseHappiness,
//        captureRate,
//        color = color.fromDomainColorModel(),
//        eggGroups = eggGroups.fromDomainEggModel(),
//        evolutionChain = evolutionChain.fromDomainEvolutionModel()
//    )
//
//private fun List<EggGroupDomain>.fromDomainEggModel(): List<EggGroupEntity> = map {
//    it.fromDomainEggModel()
//}
//
private fun ResultDomain.fromDomainPokemonModel(): ResultEntity =
    ResultEntity(id = 0, name, url)
//
//private fun ColorDomain.fromDomainColorModel(): ColorEntity =
//    ColorEntity(name, url)
//
//private fun EggGroupDomain.fromDomainEggModel(): EggGroupEntity =
//    EggGroupEntity(name, url)
//
//private fun EvolutionChainDomain.fromDomainEvolutionModel(): EvolutionChainEntity =
//    EvolutionChainEntity(url)















