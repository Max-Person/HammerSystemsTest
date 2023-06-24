package com.github.max_person.hammersystems.test.data.remote

import com.github.max_person.hammersystems.test.data.remote.responses.FoodCategory
import com.github.max_person.hammersystems.test.data.remote.responses.FoodItem
import com.github.max_person.hammersystems.test.util.Resource
import javax.inject.Inject

class FoodRespository @Inject constructor(
    private val api: FoodApi,
) {
    suspend fun getFoodsByCategory(category: FoodCategory) : Resource<List<FoodItem>> {
        val res = try {
            Resource.Success(api.getFoodsByCategory(category.id))
        } catch (e: Exception){
            Resource.Error(e.localizedMessage ?: "An unexpected error occured.")
        }
        println(res)
        return res
    }


    suspend fun getAllFoodCategories() : Resource<List<FoodCategory>> {
        return try {
            Resource.Success(foodCategories)
        } catch (e: Exception){
            Resource.Error(e.localizedMessage ?: "An unexpected error occured.")
        }
    }


    companion object {
        //Заглушка, поскольку выбранный API прост и не предоставляет запроса на получение всех категорий продуктов
        private val foodCategories = listOf<FoodCategory>(
            FoodCategory("bbqs", "BBQ"),
            //FoodCategory("breads", "Baked goods"),
            FoodCategory("pizzas", "Pizza"),
            FoodCategory("burgers", "Burgers"),
            //FoodCategory("chocolates", "BBQ"),
            FoodCategory("desserts", "Desserts"),
//            FoodCategory("drinks", "Drinks"),
            FoodCategory("fried-chicken", "Chicken"),
            //FoodCategory("ice-cream", "BBQ"),
            //FoodCategory("porks", "BBQ"),
//            FoodCategory("sandwiches", "Sandwiches"),
            //FoodCategory("sausages", "BBQ"),
            //FoodCategory("steaks", "BBQ"),
        )
    }
}