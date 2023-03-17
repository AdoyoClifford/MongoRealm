package com.adoyo.mongodbrealm.navigation

sealed class Screens(val route: String) {
    object Authentication: Screens(route = "authentication_screen")
    object Home: Screens(route = "home_screen")
}
