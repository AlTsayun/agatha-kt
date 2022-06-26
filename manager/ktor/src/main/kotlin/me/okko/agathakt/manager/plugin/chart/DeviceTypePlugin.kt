package me.okko.agathakt.manager.plugin.chart

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import me.okko.agathakt.common.model.SensorData
import me.okko.agathakt.manager.plugin.ChartPlugin
import me.okko.agathakt.manager.plugin.model.ChartOutput
import me.okko.agathakt.manager.plugin.model.DeviceType
import me.okko.agathakt.manager.plugin.model.DeviceTypeSensorData
import me.okko.agathakt.manager.plugin.model.chart.Chart
import me.okko.agathakt.manager.plugin.model.chart.ChartData
import me.okko.agathakt.manager.plugin.model.chart.ChartType
import me.okko.agathakt.manager.plugin.model.chart.PieDataset
import java.util.*


class DeviceTypePlugin(
    private val id: Int,
    private val deviceTypeSensorTypeId: Int,
) : ChartPlugin {

    override suspend fun compute(
        sensorTypeIdToData: Map<Int, List<SensorData>>,
        period: IntRange
    ): ChartOutput {
        var tabletCount = 0
        var mobileCount = 0
        var desktopCount = 0
        (sensorTypeIdToData[deviceTypeSensorTypeId] ?: throw RuntimeException(
            "Sensor data of type deviceTypeSensorTypeId[$deviceTypeSensorTypeId] is not present whereas " +
                    "needed by DeviceTypePlugin[$id]"
        ))
            .forEach {
                when (Json.decodeFromString(DeviceTypeSensorData.serializer(), it.data.toString()).d) {
                    DeviceType.tablet -> tabletCount++
                    DeviceType.mobile -> mobileCount++
                    DeviceType.desktop -> desktopCount++
                }
            }
        return ChartOutput(
            "Average page load time",
            Chart(
                ChartType.pie,
                ChartData(
                    listOf("Mobile", "PC", "Tablet"),
                    listOf(
                        PieDataset(
                            listOf(mobileCount, desktopCount, tabletCount),
                            listOf(
                                "rgb(255, 99, 132)",
                                "rgb(54, 162, 235)",
                                "rgb(255, 205, 86)"
                            )
                        )
                    )
                )
            )
        )
    }
}