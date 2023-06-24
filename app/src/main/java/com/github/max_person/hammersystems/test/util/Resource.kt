package com.github.max_person.hammersystems.test.util

sealed class Resource<D> private constructor(val data: D? = null, val message: String? = null){
    class Success<D>(data: D): Resource<D>(data, null)
    class Error<D>(message: String, data: D? = null,): Resource<D>(data, message)
    class Loading<D>(data: D? = null): Resource<D>(data, null)

    override fun toString(): String {
        return "Resource(data=$data, message=$message)"
    }


}
