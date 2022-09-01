package com.example.android_room_with_a_view.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.android_room_with_a_view.common.BASE_URL
import com.example.android_room_with_a_view.common.DATABASE_NAME
import com.example.android_room_with_a_view.domain.Repository
import com.example.android_room_with_a_view.domain.RepositoryImpl
import com.example.android_room_with_a_view.model.local.LocalDataSource
import com.example.android_room_with_a_view.model.local.LocalDataSourceImpl
import com.example.android_room_with_a_view.model.local.UserDB
import com.example.android_room_with_a_view.model.remote.RemoteApi
import com.example.android_room_with_a_view.model.remote.RemoteDataSource
import com.example.android_room_with_a_view.model.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
interface ServiceModule {
    @ViewModelScoped
    @Binds
    fun bindRepository(
        repositoryImpl: RepositoryImpl
    ): Repository

    @ViewModelScoped
    @Binds
    fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @ViewModelScoped
    @Binds
    fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource


    companion object {
        @Provides
        fun provideExceptionHandler(): CoroutineExceptionHandler =
            CoroutineExceptionHandler { context, throwable ->
                Log.d(
                    "ViewModel",
                    "provideExceptionHandler: $throwable"
                )
            }

        @Provides
        fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO


        @Provides
        fun provideCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope =
            CoroutineScope(dispatcher)


        @Provides
        @ProductionDB
        fun provideRoom(@ApplicationContext context: Context): UserDB =
            Room.databaseBuilder(
                context,
                UserDB::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration().build()


        @Provides
        fun provideDao(@ProductionDB dataBase: UserDB) = dataBase.UsersDao()


        @Provides
        fun provideService(): RemoteApi =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
                .create(RemoteApi::class.java)

        @Provides
        fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

    }
}

