package com.alexeykov.spiritlevel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexeykov.spiritlevel.navigation.BottomNavigationItem
import com.alexeykov.spiritlevel.ui.theme.SpiritLevelTheme
import com.alexeykov.spiritlevel.ui.theme.ThemeState

private var sensors: Sensors = Sensors()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensors.createSensors(this)
        setContent {
            MainPreview()
        }
    }

    override fun onPause() {
        sensors.unregisterListener()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        sensors.registerListener()
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val sPref: SharedPreferences =
        LocalContext.current.getSharedPreferences("Main", Context.MODE_PRIVATE)
    ThemeState.setIsLight(sPref.getBoolean("theme", !isSystemInDarkTheme()))
    SpiritLevelTheme {
        val orientation: Int = LocalConfiguration.current.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Scaffold(bottomBar = { NavigationMenu() }) {
                val mainActivityFormer = MainActivityFormer(sensors)
                mainActivityFormer.DrawPortrait()
            }
        } else {
            val mainActivityFormer = MainActivityFormer(sensors)
            mainActivityFormer.DrawLandscape()
        }
    }
}

@Composable
fun NavigationMenu() {
    val context = LocalContext.current
    val sPref = LocalContext.current.getSharedPreferences("Main", Context.MODE_PRIVATE)
    val items = listOf(
        BottomNavigationItem.About,
        BottomNavigationItem.Theme,
        BottomNavigationItem.Calibrate,
        BottomNavigationItem.Exit
    )
    BottomNavigation(
//        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = androidx.compose.ui.graphics.Color.White,
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = context.getString(item.title)
                    )
                },
                label = { Text(text = context.getString(item.title)) },
                selectedContentColor = androidx.compose.ui.graphics.Color.White,
                unselectedContentColor = androidx.compose.ui.graphics.Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    when (item.position) {
                        0 -> {
                            val intent = Intent(context, AboutActivity::class.java)
                            intent.putExtra("theme", ThemeState.isLight)
                            context.startActivity(intent)
                        }
                        1 -> {
                            ThemeState.isLight = !ThemeState.isLight
                            sPref.edit().putBoolean("theme", ThemeState.isLight).apply()
                        }
                        2 -> sensors.calibrate()
                        3 -> {
                            val activity = context as? Activity
                            activity?.finish()
                        }
                    }
                }
            )
        }
    }
}