package com.alexeykov.spiritlevel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.alexeykov.spiritlevel.ui.theme.Blue500
import com.alexeykov.spiritlevel.ui.theme.Blue700
import com.alexeykov.spiritlevel.ui.theme.ThemeState

class DrawHandler(drawScopeInit: DrawScope) {

    private val drawScope = drawScopeInit
    private val size = drawScopeInit.size

    fun drawCross() {
        var lineColor = Color(0xFFFFFFFF)
        if (ThemeState.isLight)
            lineColor = Color(0xFF000000)
        drawScope.drawLine(
            color = lineColor,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            strokeWidth = 4f
        )
        drawScope.drawLine(
            color = lineColor,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 4f
        )
        drawScope.drawCircle(
            color = lineColor,
            radius = size.width / 10f,
            center = size.center,
            style = Stroke(width = 4f)
        )
        drawScope.drawCircle(
            color = lineColor,
            radius = size.width / 5f,
            center = size.center,
            style = Stroke(width = 4f)
        )
        drawScope.drawCircle(
            color = lineColor,
            radius = size.width / 3f,
            center = size.center,
            style = Stroke(width = 4f)
        )
        drawScope.drawCircle(
            color = lineColor,
            radius = size.width / 2f,
            center = size.center,
            style = Stroke(width = 4f)
        )
    }

    fun drawBubble(ox: String, oy: String) {
        if (ox != "" && oy != "") {
            val x = ox.replace(",", ".").toFloat() * size.width / 180 * -1
            val y = oy.replace(",", ".").toFloat() * size.height / 180
            val pointX = (size.width / 2) + x
            val pointY = size.height / 2 + y
            drawScope.drawCircle(
                Brush.linearGradient(
                    colors = listOf(Blue500, Blue700)
                ),
                radius = size.width / 10,
                Offset(pointX, pointY),
                style = Fill
            )
        }
    }
}