package com.alexeykov.spiritlevel

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexeykov.spiritlevel.ui.theme.SpiritLevelTheme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutPreview()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(modifier = Modifier.padding(10.dp),
        text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
    val activity = (LocalContext.current as? Activity)
    SpiritLevelTheme {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting("Android")
            Button(
                modifier = Modifier.padding(top = 10.dp),
                onClick = { activity?.finish() }) {
                Text(text = "OK")
            }
        }
    }
}