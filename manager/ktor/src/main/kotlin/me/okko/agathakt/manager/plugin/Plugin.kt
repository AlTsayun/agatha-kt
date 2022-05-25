package me.okko.agathakt.manager.plugin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import me.okko.agathakt.common.model.SensorData
import me.okko.agathakt.manager.plugin.model.ChartOutput
import me.okko.agathakt.manager.plugin.model.ImmediateOutput
import me.okko.agathakt.manager.plugin.model.Output
import me.okko.agathakt.manager.plugin.model.chart.ChartOutput
import me.okko.agathakt.manager.plugin.model.immediate.ImmediateOutput
import me.okko.agathakt.manager.service.SensorDataLoader

interface Plugin {
    suspend fun computeFromSensorData(sensorTypeIdToData: Map<Int, List<SensorData>>): Output
}
interface ChartPlugin : Plugin {
    override suspend fun computeFromSensorData(sensorTypeIdToData: Map<Int, List<SensorData>>): ChartOutput
}
interface ImmediatePlugin : Plugin {
    override suspend fun computeFromSensorData(sensorTypeIdToData: Map<Int, List<SensorData>>): ImmediateOutput
}
