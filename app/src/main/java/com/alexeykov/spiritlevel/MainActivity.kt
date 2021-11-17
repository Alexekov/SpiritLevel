package com.alexeykov.spiritlevel

import android.content.Context
import android.content.SharedPreferences
import android.hardware.SensorManager
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
private var Ax: String = ""
private var Ay: String = ""
private var Az: String = ""
private val dataAx = mutableStateOf(Ax)
private val dataAy = mutableStateOf(Ay)
private val dataAz = mutableStateOf(Az)
private var lastAngle: FloatArray = FloatArray(3)
private var calibrateAngle: FloatArray = FloatArray(3)
private lateinit var sPref: SharedPreferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensors = Sensors(getSystemService(Context.SENSOR_SERVICE) as SensorManager)

        sPref = getSharedPreferences("Settings", 0)
        calibrateAngle[2] = sPref.getFloat("CalibratedX", 0f)
        calibrateAngle[1] = sPref.getFloat("CalibratedY", 0f)
        calibrateAngle[0] = sPref.getFloat("CalibratedZ", 0f)

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
    val ox by dataAx
    Text(text = "oX: $ox", Modifier.padding(10.dp))
}

@Composable
fun Oy() {
    val oy by dataAy
    Text(text = "oY: $oy", Modifier.padding(10.dp))
}

@Composable
fun Oz() {
    val oz by dataAz
    Text(text = "oZ: $oz", Modifier.padding(10.dp))
}

@Composable
fun Calibrate() {
    Button(onClick = {
        for (i in 0..2) {
            calibrateAngle[i] = lastAngle[i]
        }
        val editor = sPref.edit()
        editor.putFloat("CalibratedX", calibrateAngle[2])
        editor.putFloat("CalibratedY", calibrateAngle[1])
        editor.putFloat("CalibratedZ", calibrateAngle[0])
        editor.apply()
//        calibrateData = g.clone()
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