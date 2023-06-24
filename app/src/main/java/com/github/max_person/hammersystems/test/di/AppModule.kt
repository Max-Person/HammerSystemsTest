package com.github.max_person.hammersystems.test.di

import com.github.max_person.hammersystems.test.data.remote.FoodApi
import com.github.max_person.hammersystems.test.data.remote.FoodRespository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFoodApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(FoodApi.BASE_URL)
        .build()
        .create(FoodApi::class.java)

    @Singleton
    @Provides
    fun provideFoodRepository(
        api: FoodApi,
    ) = FoodRespository(api)
}