package com.alexeykov.spiritlevel

import android.view.Surface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.alexeykov.spiritlevel.ui.theme.*

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

    fun drawRuler() {
        drawScope.drawRect(
            color = Yellow500,
            topLeft = Offset(20f, size.height / 3),
            size = Size(size.width - 40, size.height / 2),
        )
    }

    fun drawRulerFrame() {
        var lineColor = Color(0xFFFFFFFF)
        if (ThemeState.isLight)
            lineColor = Color(0xFF000000)
        drawScope.drawLine(
            color = lineColor,
            start = Offset(20f,  size.height / 3),
            end = Offset(size.width - 20f, size.height / 3),
            strokeWidth = 4f
        )
        drawScope.drawLine(
            color = lineColor,
            start = Offset(20f,  size.height / 3 * 2.5f),
            end = Offset(size.width - 20f, size.height / 3 * 2.5f),
            strokeWidth = 4f
        )
        drawScope.drawLine(
            color = lineColor,
            start = Offset(20f, size.height / 3),
            end = Offset(20f, size.height / 3 * 2.5f),
            strokeWidth = 4f
        )
        drawScope.drawLine(
            color = lineColor,
            start = Offset(size.width - 20f, size.height / 3),
            end = Offset(size.width - 20f, size.height / 3 * 2.5f),
            strokeWidth = 4f
        )

        drawScope.drawLine(
            color = lineColor,
            start = Offset(size.width / 2 - size.width / 20 + 4, size.height / 3),
            end = Offset(size.width / 2 - size.width / 20 + 4, size.height / 3 * 1.3f),
            strokeWidth = 4f
        )
        drawScope.drawLine(
            color = lineColor,
            start = Offset(size.width / 2 + size.width / 20 - 4, size.height / 3),
            end = Offset(size.width / 2 + size.width / 20 - 4, size.height / 3 * 1.3f),
            strokeWidth = 4f
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
                    colors = listOf(Yellow500, Yellow700)
                ),
                radius = size.width / 10,
                Offset(pointX, pointY),
                style = Fill
            )
        }
    }

    fun drawBubble(oy: String, color: Color, rotations: Int) {
        if (oy != "") {
            var length =  (size.width / 2) / 90
            if (rotations == Surface.ROTATION_270)
                length *= -1
            val y = oy.replace(",", ".").toFloat()
            val pointX = (size.width / 2) + y * length
            val pointY = size.height / 3
            drawScope.drawCircle(
                color = color,
                radius = size.height / 10,
                Offset(pointX, pointY),
                style = Fill
            )
        }
    }
}