package me.okko.agathakt.manager.plugin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import me.okko.agathakt.common.model.SensorData
import me.okko.agathakt.manager.plugin.model.chart.ChartOutput
import me.okko.agathakt.manager.plugin.model.immediate.Dynamics
import me.okko.agathakt.manager.plugin.model.immediate.ImmediateOutput
import me.okko.agathakt.manager.plugin.model.immediate.ImmediateOutputType
import me.okko.agathakt.manager.plugin.model.immediate.TimeData

class AveragePageLoadTimePlugin(
    private val id: Int,
    private val pageLoadTimeSensorTypeId: Int,
) : ImmediatePlugin {

    @kotlinx.serialization.Serializable
    private data class LoadTimeSensorData(val l: Int)

    override suspend fun computeFromSensorData(sensorTypeIdToData: Map<Int, List<SensorData>>): ImmediateOutput {
        val averageLoadTime = (
                sensorTypeIdToData[pageLoadTimeSensorTypeId] ?:
                throw RuntimeException(
                    "Sensor data of type pageLoadTimeSensorType[$pageLoadTimeSensorTypeId] is not present whereas " +
                            "needed by AveragePageLoadTimePlugin[$id]"
                )
                )
            .map {
                Json.decodeFromString(LoadTimeSensorData.serializer(), it.data.toString()).l
            }
            .average().toInt()
        return ImmediateOutput(
            "Average page load time",
            ImmediateOutputType.time,
            "Average page load time",
            TimeData(
                Dynamics(0f, false),
                averageLoadTime
            )
        )
    }
}