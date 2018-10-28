package com.arthurnagy.workoutlog.core.storage

import com.arthurnagy.workoutlog.core.Result

interface DataSource<K : Any, D : Any> {

    suspend fun get(id: K): Result<D>

    suspend fun get(): Result<List<D>>

}