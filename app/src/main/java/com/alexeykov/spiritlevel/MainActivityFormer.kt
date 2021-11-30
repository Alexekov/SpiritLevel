package com.alexeykov.spiritlevel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


class MainActivityFormer(sensor: Sensors) {

    private val sensors = sensor

    @Composable
    fun DrawSurface() {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .weight(1F)
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DrawCross()
                }
//                    Greeting("Показания датчиков")
                Row(
                    Modifier
                        .fillMaxWidth()
//                            .height(70.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 70.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Ox()
                    Oy()
//                        Oz()
                }
/*                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 70.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Calibrate()
                }*/
            }
        }
    }

    @Composable
    fun Ox() {
        val ox by sensors.getDataAx()
        Text(
            text = "oX\n$ox",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(100.dp)
                .padding(start = 10.dp, end = 10.dp)
        )
    }

    @Composable
    fun Oy() {
        val oy by sensors.getDataAy()
        Text(
            text = "oY\n$oy",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(100.dp)
                .padding(start = 10.dp, end = 10.dp)
        )
    }

    @Composable
    fun DrawCross() {
        val ox by sensors.getDataAx()
        val oy by sensors.getDataAy()
        Canvas(modifier = Modifier
            .fillMaxSize(), onDraw = {
            val drawHandler = DrawHandler(drawScopeInit = this)
            // Bubble
            drawHandler.drawBubble(ox, oy)
            // Cross
            drawHandler.drawCross()
        })
    }

    @Composable
    fun Oz() {
        val oz by sensors.getDataAz()
        Text(
            text = "oZ\n$oz",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(100.dp)
                .padding(10.dp)
        )
    }

    @Composable
    fun Calibrate() {
        Button(onClick = {
            sensors.calibrate()
        }) {
            Text(text = "Calibrate")
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = name, Modifier.padding(10.dp))
    }
}