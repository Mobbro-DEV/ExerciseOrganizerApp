package com.organizer

sealed class Routes(val route: String) {
    object Sports: Routes("sports")
    object Subcategory: Routes("subcategory/{categoryId}") {
        fun createRoute(categoryId: Long): String {
            return "subcategory/$categoryId"
        }
    }
}
