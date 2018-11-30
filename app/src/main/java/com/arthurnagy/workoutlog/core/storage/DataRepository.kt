package com.arthurnagy.workoutlog.core.storage

import com.arthurnagy.workoutlog.core.Result

abstract class DataRepository<K : Any, D : Any>(
    private val remoteSource: DataSource<K, D>,
    protected val memorySource: MemoryDataSource<K, D>
) {

    suspend fun get(id: K): Result<D> {
        val memoryResult = memorySource.get(id)
        return when (memoryResult) {
            is Result.Success<D> -> memoryResult
            is Result.Error -> remoteSource.get(id)
        }
    }

    suspend fun get(): Result<List<D>> {
        val memoryResult = memorySource.get()
        return when (memoryResult) {
            is Result.Success -> memoryResult
            is Result.Error -> fetch()
        }
    }

    suspend fun fetch(): Result<List<D>> = remoteSource.get().also {
        (it as? Result.Success)?.value?.forEach(::cacheData)
    }

    protected abstract fun cacheData(data: D)

}