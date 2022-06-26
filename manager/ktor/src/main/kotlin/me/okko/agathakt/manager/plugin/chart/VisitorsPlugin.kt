package me.okko.agathakt.manager.plugin.chart

import kotlinx.serialization.json.Json
import me.okko.agathakt.common.model.SensorData
import me.okko.agathakt.manager.plugin.model.ChartOutput
import me.okko.agathakt.manager.plugin.model.VisitTimeSensorData

class VisitorsPlugin(
    private val id: Int,
    private val visitTimeSensorTypeId: Int
) : LineChartPlugin() {

    override suspend fun compute(
        sensorTypeIdToData: Map<Int, List<SensorData>>,
        period: IntRange
    ): ChartOutput {
        val periodToCount = getSubPeriodToCount(period)

        (sensorTypeIdToData[visitTimeSensorTypeId] ?: throw RuntimeException(
            "Sensor data of type VisitTimeSensorType[$visitTimeSensorTypeId] is not present whereas " +
                    "needed by VisitorsPlugin[$id]"
        )).forEach { sensorData ->
            val data = Json.decodeFromString(VisitTimeSensorData.serializer(), sensorData .data.toString())
            if (data.t > 10) {
                incPeriodValue(periodToCount, sensorData.t)
            }
        }

        return getChartOutput(periodToCount, "Visitors")
    }
}