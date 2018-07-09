package com.arthurnagy.workoutlog.core.model

data class GenericData(val id: Int, val name: String) {
    companion object {
        const val CATEGORY_REFERENCE = "categories"
        const val EQUIPMENT_REFERENCE = "equipments"
        const val MUSCLE_REFERENCE = "muscles"
    }
}