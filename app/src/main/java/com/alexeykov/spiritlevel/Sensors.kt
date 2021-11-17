package com.alexeykov.spiritlevel

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

private lateinit var sensorManager: SensorManager
private var rotationSensor: Sensor? = null
private var deltaTime: Long = 0L

class Sensors constructor(sensorMngr: SensorManager) : SensorEventListener {

    init {
        // присвоили менеджеру работу с серсором
        sensorManager = sensorMngr
        // создали список сенсоров для записи и сортировки
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

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

    fun registerListener() {
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI)
//        sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
//        Sensor change value
        if (sensorEvent?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val systemTime = System.currentTimeMillis()
            if (systemTime - deltaTime < 100)
                return
            deltaTime = systemTime
/*            g = sensorEvent.values.clone()
            g[0] = (g[0] + lastData[0] + lastData2[0] + lastData3[0]) / 4
            g[1] = (g[1] + lastData[1] + lastData2[1] + lastData3[1]) / 4
            g[2] = (g[2] + lastData[2] + lastData2[2] + lastData3[2]) / 4
            val norm = sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2])
//            if (norm < 3.20) return
            g[0] = g[0] / norm - calibrateData[0]
            g[1] = g[1] / norm - calibrateData[1]
            g[2] = g[2] / norm - calibrateData[2]

            val kFilteringFactor = 0.1f
            g[0] = (g[0] * kFilteringFactor + g[0] * (1.0f - kFilteringFactor))
            g[1] = (g[1] * kFilteringFactor + g[1] * (1.0f - kFilteringFactor))

            lastData3 = lastData2.clone()
            lastData2 = lastData.clone()
            lastData = g.clone()

/*            val roll = atan(-sensorEvent.values[0] / sensorEvent.values[2]).toDouble()
            val pitch =
                atan(sensorEvent.values[1] / sqrt(sensorEvent.values[0] * sensorEvent.values[0] + sensorEvent.values[2] * sensorEvent.values[2])).toDouble()*/
            val roll = atan(-g[0] / g[2]).toDouble()
            val pitch = atan(g[1] / sqrt(g[0] * g[0] + g[2] * g[2])).toDouble()
            lastAngle[0] = Math.toDegrees(roll) + calibrateAngle[0]
            lastAngle[1] = Math.toDegrees(pitch) + calibrateAngle[1]
//            val inclination = round(Math.toDegrees(acos(g[2]).toDouble()))*/
            val rotationMatrix = FloatArray(16)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values)
            // Remap coordinate system
//            val remappedRotationMatrix = FloatArray(16)
//            SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Y, remappedRotationMatrix)

            // Convert to orientations
            val orientations = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientations)
            for (i in 0..2) {
                lastAngle[i] = Math.toDegrees(orientations[i].toDouble()).toFloat()
                orientations[i] = lastAngle[i] - calibrateAngle[i]
            }
            // X - 2, Y - 1, Z (North) - 0
            dataAx.value = String.format("%.1f", orientations[2])
            dataAy.value = String.format("%.1f", orientations[1])
//            dataAx.value = String.format("%.4f", sensorEvent.values[SensorManager.DATA_X])
//            dataAy.value = String.format("%.4f", sensorEvent.values[SensorManager.DATA_Y])
            dataAz.value = String.format("%.1f", orientations[0])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}