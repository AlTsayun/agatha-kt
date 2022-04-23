package me.okko.agathakt.manager.plugin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import me.okko.agathakt.common.model.SensorData

class AveragePageLoadTimePlugin(
    private val id: Int,
    private val pageLoadTimeSensorTypeId: Int,
) : Plugin {

    @kotlinx.serialization.Serializable
    private data class LoadTimeSensorData(val l: Int)

    override suspend fun computeFromSensorData(sensorTypeIdToData: Map<Int, List<SensorData>>): JsonObject {
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
        return Json.decodeFromString(JsonObject.serializer(), "{\"l\":$averageLoadTime}")
    }
}