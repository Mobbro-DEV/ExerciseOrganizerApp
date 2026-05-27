package com.organizer

sealed class Routes(val route: String) {
    data object Sports: Routes("sports")
    data object Subcategory: Routes("category")
}
