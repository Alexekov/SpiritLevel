package com.alexeykov.spiritlevel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexeykov.spiritlevel.navigation.BottomNavigationItem
import com.alexeykov.spiritlevel.ui.theme.SpiritLevelTheme
import kotlin.system.exitProcess

private var sensors: Sensors = Sensors()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensors.createSensors(this)
        setContent {
            val mainActivityFormer = MainActivityFormer(sensors)
            mainActivityFormer.DrawSurface()
//            MainPreview()
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
    SpiritLevelTheme {
        Scaffold(bottomBar = { NavigationMenu(true) }) {
            val mainActivityFormer = MainActivityFormer(sensors)
            mainActivityFormer.DrawSurface()
        }
    }
}

@Composable
fun NavigationMenu(isVisible: Boolean) {
    val items = listOf(
        BottomNavigationItem.Calibrate,
        BottomNavigationItem.About,
        BottomNavigationItem.Exit
    )
    if (isVisible)
        BottomNavigation(
//        backgroundColor = colorResource(id = R.color.teal_200),
            contentColor = androidx.compose.ui.graphics.Color.White,
            ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) },
                    selectedContentColor = androidx.compose.ui.graphics.Color.White,
                    unselectedContentColor = androidx.compose.ui.graphics.Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = false,
                    onClick = {
                        if (item.route == "exit") {
                            exitProcess(0)
                        }
                        if (item.route == "calibrate") {
                            sensors.calibrate()
                        }
                        /* Add code later */
                    }
                )
            }
        }
}