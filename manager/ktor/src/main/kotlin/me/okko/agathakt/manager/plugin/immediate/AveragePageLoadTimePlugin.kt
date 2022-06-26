package me.okko.agathakt.manager.plugin.immediate

import kotlinx.serialization.json.Json
import me.okko.agathakt.common.model.SensorData
import me.okko.agathakt.manager.plugin.ImmediatePlugin
import me.okko.agathakt.manager.plugin.model.ImmediateOutput
import me.okko.agathakt.manager.plugin.model.LoadTimeSensorData
import me.okko.agathakt.manager.plugin.model.immediate.Dynamics
import me.okko.agathakt.manager.plugin.model.immediate.ImmediateOutputType
import me.okko.agathakt.manager.plugin.model.immediate.TimeData

class AveragePageLoadTimePlugin(
    private val id: Int,
    private val pageLoadTimeSensorTypeId: Int,
) : ImmediatePlugin {


    override suspend fun compute(
        sensorTypeIdToData: Map<Int, List<SensorData>>,
        period: IntRange
    ): ImmediateOutput {
        val averageLoadTime = (
                sensorTypeIdToData[pageLoadTimeSensorTypeId] ?: throw RuntimeException(
                    "Sensor data of type pageLoadTimeSensorType[$pageLoadTimeSensorTypeId] is not present whereas " +
                            "needed by AveragePageLoadTimePlugin[$id]"
                )
                )
            .map {
                Json.decodeFromString(LoadTimeSensorData.serializer(), it.data.toString()).t
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