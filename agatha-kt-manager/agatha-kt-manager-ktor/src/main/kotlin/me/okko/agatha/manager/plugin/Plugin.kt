package me.okko.agatha.manager.plugin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import me.okko.agatha.manager.service.SensorDataLoader

interface Plugin {
    suspend fun getOutput(): JsonObject
}

class AveragePageLoadTimePlugin(
    private val pageLoadTimeSensorId: Int,
    private val sensorDataLoader : SensorDataLoader,
    ) : Plugin {

    @kotlinx.serialization.Serializable
    private data class LoadTimeSensorData(val l: Int)

    override suspend fun getOutput(): JsonObject {
        val averageLoadTime = sensorDataLoader.getBySensorId(pageLoadTimeSensorId)
            .map {
                Json.decodeFromString(LoadTimeSensorData.serializer(), it.data.toString()).l
            }
            .average().toInt()
        return Json.decodeFromString(JsonObject.serializer(), "{\"l\":$averageLoadTime}")
    }

}