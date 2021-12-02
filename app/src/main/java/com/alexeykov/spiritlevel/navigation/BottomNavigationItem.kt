package com.alexeykov.spiritlevel.navigation

import com.alexeykov.spiritlevel.R

sealed class BottomNavigationItem(var position: Int, var icon: Int, var title: Int) {
    object About : BottomNavigationItem(0, R.drawable.ic_book, R.string.About)
    object Theme : BottomNavigationItem(1, R.drawable.ic_theme, R.string.Theme)
    object Calibrate : BottomNavigationItem(2, R.drawable.ic_calibrate, R.string.Calibrate)
    object Exit : BottomNavigationItem(3, R.drawable.ic_exit, R.string.Exit)
}