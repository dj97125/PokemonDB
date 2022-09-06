package com.example.android_room_with_a_view.model.local

import androidx.room.*
import com.example.android_room_with_a_view.model.local.entities.ResultEntity


@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(userEntity: List<ResultEntity>)

    @Query("SELECT * FROM ResultEntity")
    suspend fun getAllPokemons(): List<ResultEntity>


}


@Database(
    version = 1,
    entities = [ResultEntity::class],
    exportSchema = false
)
abstract class PokemonDB : RoomDatabase() {
    abstract fun PokemonDao(): PokemonDao

}