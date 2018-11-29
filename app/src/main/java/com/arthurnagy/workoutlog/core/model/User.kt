package com.arthurnagy.workoutlog.core.model

import androidx.annotation.Keep

@Keep
data class User(
    val id: String = "",
    val displayName: String? = "",
    val email: String? = "",
    var age: Int? = 0,
    var weight: Float? = 0f,
    val profilePictureUrl: String = "",
    val activeRoutineId: Int = 0,
    val routines: List<Routine>? = emptyList()
) {
    companion object {
        const val REFERENCE = "users"
    }
}