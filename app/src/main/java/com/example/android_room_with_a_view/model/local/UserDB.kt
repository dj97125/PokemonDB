package com.example.android_room_with_a_view.model.local

import androidx.room.*
import com.example.android_room_with_a_view.model.local.entities.DataEntity


@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: List<DataEntity>)

    @Query("SELECT * FROM DataEntity")
    suspend fun getAllUsers(): List<DataEntity>


}


@Database(
    version = 1,
    entities = [DataEntity::class],
    exportSchema = false
)
abstract class UserDB : RoomDatabase() {
    abstract fun UsersDao(): UsersDao

}