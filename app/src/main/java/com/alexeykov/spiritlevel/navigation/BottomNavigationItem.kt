package com.alexeykov.spiritlevel.navigation

import com.alexeykov.spiritlevel.R

sealed class BottomNavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : BottomNavigationItem("home", R.drawable.ic_home, "Home")
    object Music : BottomNavigationItem("music", R.drawable.ic_music, "Music")
    object Movies : BottomNavigationItem("movies", R.drawable.ic_movie, "Movies")
    object Books : BottomNavigationItem("books", R.drawable.ic_book, "Books")
    object Profile : BottomNavigationItem("profile", R.drawable.ic_profile, "Profile")
}