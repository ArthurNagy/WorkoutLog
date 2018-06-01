package com.arthurnagy.workoutlog.core.model

data class User(
    val id: String = "",
    val displayName: String? = "",
    val email: String? = "",
    var age: Int? = 0,
    var weight: Float? = 0f,
    val profilePictureUrl: String = ""
) {
    companion object {
        const val REFERENCE = "user"
    }
}