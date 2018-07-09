package com.arthurnagy.workoutlog.core.storage

import me.arthurnagy.kotlincoroutines.Result

abstract class MemoryDataSource<K : Any, D : Any> : DataSource<K, D> {

    private val cache = linkedMapOf<K, D>()

    override suspend fun get(): Result<List<D>> {
        val cachedList = cache.values.toList()
        return if (cachedList.isNotEmpty()) Result.Success(cachedList) else Result.Error(Exception("Memory cache is empty"))
    }

    override suspend fun get(id: K): Result<D> = cache[id]?.let { Result.Success(it) } ?: Result.Error(Exception("Item with id: $id not found"))

    fun cache(key: K, data: D) {
        cache[key] = data
    }

}