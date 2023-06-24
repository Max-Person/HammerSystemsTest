package com.github.max_person.hammersystems.test.data.remote

import com.github.max_person.hammersystems.test.data.remote.responses.FoodItem
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApi {

    @GET("{category}")
    suspend fun getFoodsByCategory(
        @Path("category") categoryId: String,
    ) : List<FoodItem>

    companion object {
        const val BASE_URL = "https://free-food-menus-api-production.up.railway.app/"
    }
}