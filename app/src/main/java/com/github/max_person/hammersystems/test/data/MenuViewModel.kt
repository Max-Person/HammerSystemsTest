package com.github.max_person.hammersystems.test.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.max_person.hammersystems.test.data.models.FoodCard
import com.github.max_person.hammersystems.test.data.remote.FoodRespository
import com.github.max_person.hammersystems.test.data.remote.responses.FoodCategory
import com.github.max_person.hammersystems.test.data.remote.responses.FoodItem
import com.github.max_person.hammersystems.test.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: FoodRespository,
) : ViewModel() {

    val selectedCategory = mutableStateOf<FoodCategory?>(null)

    val categories = object : ListLoader<FoodCategory, FoodCategory, Unit>(viewModelScope){
        override suspend fun getData(param: Unit): Resource<List<FoodCategory>> {
            return repository.getAllFoodCategories()
        }

        override fun List<FoodCategory>.mapToView(): List<FoodCategory> {
            return this
        }

    }

    val foods = object : ListLoader<FoodCard, FoodItem, FoodCategory>(viewModelScope){
        override suspend fun getData(param: FoodCategory): Resource<List<FoodItem>> {
            return repository.getFoodsByCategory(param)
        }

        override fun List<FoodItem>.mapToView(): List<FoodCard> {
            return this.map{
                FoodCard(
                    it.name,
                    it.dsc,
                    it.img,
                    it.price,
                )
            }
        }

    }
}

abstract class DataLoader<ViewType, ModelType, ParamType>(
    val defaultData: ViewType,
    val coroutineScope: CoroutineScope,
) {
    val data = mutableStateOf(defaultData)
    val loadError = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    protected abstract suspend fun getData(param: ParamType) : Resource<ModelType>
    protected abstract fun ModelType.mapToView() : ViewType
    fun load(param: ParamType){
        println("fetching start")
        coroutineScope.launch {
            isLoading.value = true
            println("fetching start 2")
            val result = getData(param)
            println("fetching ended")
            println(result)
            when(result){
                is Resource.Success -> {
                    loadError.value = ""
                    isLoading.value = false
                    data.value = result.data!!.mapToView()
                    println(data.value)
                }
                else -> {
                    loadError.value = result.message ?: "Unknown Error Occurred"
                    isLoading.value = false
                    data.value = defaultData
                }
            }
        }
    }
}

abstract class ListLoader<ViewType, ModelType, ParamType>(
    coroutineScope: CoroutineScope,
    defaultData: List<ViewType> = listOf(),
) : DataLoader<List<ViewType>, List<ModelType>, ParamType>(defaultData, coroutineScope)