package com.alexeykov.spiritlevel

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

private lateinit var sensorManager: SensorManager
private var rotationSensor: Sensor? = null
private var deltaTime: Long = 0L
private var calibrateAngle: FloatArray = FloatArray(3)
private var lastAngle: FloatArray = FloatArray(3)
private lateinit var sPref: SharedPreferences
private var dataAx = mutableStateOf("")
private var dataAy = mutableStateOf("")
private var dataAz = mutableStateOf("")

class Sensors : SensorEventListener {

    init {
        //        magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
/*        val sensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        if (sensors.isNotEmpty()) {
            for (sensor in sensors) {
                if (sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    accelerometerSensor = sensor
                    break
                }
            }
        }*/
    }

    fun createSensors(context: Context) {
        // присвоили менеджеру работу с серсором
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // создали список сенсоров для записи и сортировки
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        sPref = context.getSharedPreferences("Settings", 0)
        calibrateAngle[2] = sPref.getFloat("CalibratedX", 0f)
        calibrateAngle[1] = sPref.getFloat("CalibratedY", 0f)
        calibrateAngle[0] = sPref.getFloat("CalibratedZ", 0f)
    }

    fun registerListener() {
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI)
//        sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if (sensorEvent?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val systemTime = System.currentTimeMillis()
            if (systemTime - deltaTime < 100)
                return
            deltaTime = systemTime
            val rotationMatrix = FloatArray(16)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values)
            // Convert to orientations
            val orientations = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientations)
            for (i in 0..2) {
                lastAngle[i] = Math.toDegrees(orientations[i].toDouble()).toFloat()
                orientations[i] = lastAngle[i] - calibrateAngle[i]
            }
            dataAx.value = String.format("%.1f", orientations[2])
            dataAy.value = String.format("%.1f", orientations[1])
            dataAz.value = String.format("%.1f", orientations[0])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    fun calibrate() {
        for (i in 0..2) {
            calibrateAngle[i] = lastAngle[i]
        }
        val editor = sPref.edit()
        editor.putFloat("CalibratedX", calibrateAngle[2])
        editor.putFloat("CalibratedY", calibrateAngle[1])
        editor.putFloat("CalibratedZ", calibrateAngle[0])
        editor.apply()
    }

    fun getDataAx(): MutableState<String> {
        return dataAx
    }

    fun getDataAy(): MutableState<String> {
        return dataAy
    }

    fun getDataAz(): MutableState<String> {
        return dataAz
    }
}