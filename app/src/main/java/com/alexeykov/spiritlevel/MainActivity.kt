package com.alexeykov.spiritlevel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexeykov.spiritlevel.navigation.BottomNavigationItem
import com.alexeykov.spiritlevel.ui.theme.SpiritLevelTheme

private var sensors: Sensors? = null

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensors = Sensors(this)
        setContent {
            MainPreview()
        }
    }

    override fun onPause() {
        sensors?.unregisterListener()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        sensors?.registerListener()
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = name, Modifier.padding(10.dp))
}

@Composable
fun Ox() {
    val ox by sensors!!.getDataAx()
    Text(text = "oX: $ox", Modifier.padding(10.dp))
}

@Composable
fun Oy() {
    val oy by sensors!!.getDataAy()
    Text(text = "oY: $oy", Modifier.padding(10.dp))
}

@Composable
fun Oz() {
    val oz by sensors!!.getDataAz()
    Text(text = "oZ: $oz", Modifier.padding(10.dp))
}

@Composable
fun Calibrate() {
    Button(onClick = {
        sensors?.calibrate()
    }) {
        Text(text = "Calibrate")
    }
}

@Composable
fun NavigationMenu() {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Music,
        BottomNavigationItem.Movies,
        BottomNavigationItem.Books,
        BottomNavigationItem.Profile
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.purple_200),
        contentColor = androidx.compose.ui.graphics.Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = androidx.compose.ui.graphics.Color.White,
                unselectedContentColor = androidx.compose.ui.graphics.Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    SpiritLevelTheme {
        Scaffold(bottomBar = { NavigationMenu() }) {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Greeting("Показания датчиков")
                    Row(
                        Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Ox()
                        Oy()
                        Oz()
                    }
                    Calibrate()
                }
            }
        }
    }
}