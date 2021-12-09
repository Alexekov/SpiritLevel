package com.alexeykov.spiritlevel

import android.content.Context
import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


class MainActivityFormer(sensor: Sensors) {

    private val sensors = sensor

    @Composable
    fun DrawPortrait() {
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
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 70.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Ox()
                    Oy()
                }
            }
        }
    }

    @Composable
    fun DrawLandscape() {
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
                    DrawRuler()
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Ox()
                    Oy()
                }
            }
        }
    }

    @Composable
    fun Ox() {
        val ox by sensors.getDataAx()
        Text(
            text = stringResource(id = R.string.OX).plus(ox),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(100.dp)
        )
    }

    @Composable
    fun Oy() {
        val oy by sensors.getDataAy()
        Text(
            text = stringResource(id = R.string.OY).plus(oy),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(100.dp)
        )
    }

    @Composable
    fun DrawCross() {
        val ox by sensors.getDataAx()
        val oy by sensors.getDataAy()
        Canvas(modifier = Modifier
            .fillMaxSize(), onDraw = {
            val drawHandler = DrawHandler(drawScopeInit = this)
            drawHandler.drawBubble(ox, oy)
            drawHandler.drawCross()
        })
    }

    @Composable
    fun DrawRuler() {
        val rotation: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            LocalContext.current.display?.rotation!!
        } else {
            val windowManager: WindowManager = LocalContext.current.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.rotation
        }
        val color = MaterialTheme.colors.background
        val oy by sensors.getDataAy()
        Canvas(modifier = Modifier
            .fillMaxSize(), onDraw = {
            val drawHandler = DrawHandler(drawScopeInit = this)
            drawHandler.drawRuler()
            drawHandler.drawBubble(oy, color, rotation)
            drawHandler.drawRulerFrame()
        })
    }}