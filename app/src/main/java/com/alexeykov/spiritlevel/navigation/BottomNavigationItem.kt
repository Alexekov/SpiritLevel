package com.alexeykov.spiritlevel.navigation

import com.alexeykov.spiritlevel.R

sealed class BottomNavigationItem(var route: String, var icon: Int, var title: String) {
    object About : BottomNavigationItem("about", R.drawable.ic_book, "About")
    object Exit : BottomNavigationItem("exit", R.drawable.ic_exit, "Exit")
}