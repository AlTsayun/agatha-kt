package me.okko.agathakt.manager.plugin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import me.okko.agathakt.common.model.SensorData
import me.okko.agathakt.manager.service.SensorDataLoader

interface Plugin {
    suspend fun computeFromSensorData(sensorTypeIdToData: Map<Int, List<SensorData>>): JsonObject
}
