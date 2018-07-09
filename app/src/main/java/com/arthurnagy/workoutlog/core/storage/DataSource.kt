package com.arthurnagy.workoutlog.core.storage

import me.arthurnagy.kotlincoroutines.Result

interface DataSource<K : Any, D : Any> {

    suspend fun get(id: K): Result<D>

    suspend fun get(): Result<List<D>>

}