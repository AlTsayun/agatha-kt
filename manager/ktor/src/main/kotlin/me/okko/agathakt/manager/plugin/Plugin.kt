package me.okko.agathakt.manager.plugin

import me.okko.agathakt.common.model.SensorData
import me.okko.agathakt.manager.plugin.model.ChartOutput
import me.okko.agathakt.manager.plugin.model.ImmediateOutput
import me.okko.agathakt.manager.plugin.model.Output
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.BiFunction

interface Plugin {
    suspend fun compute(sensorTypeIdToData: Map<Int, List<SensorData>>, period: IntRange): Output
}

interface ChartPlugin : Plugin {
    override suspend fun compute(
        sensorTypeIdToData: Map<Int, List<SensorData>>,
        period: IntRange
    ): ChartOutput
}

interface ImmediatePlugin : Plugin {
    override suspend fun compute(
        sensorTypeIdToData: Map<Int, List<SensorData>>,
        period: IntRange
    ): ImmediateOutput
}

fun Pair<Date, Date>.unixRange(): IntRange {
    return (this.first.time / 1000).toInt()..(this.second.time / 1000).toInt()
}

fun IntRange.timeLabel(): String {
    val beginning = Date(first * 1000L)

    val formatter = when (last - first) {
        in 0 until 86400 -> SimpleDateFormat("h:mm a") // hours
        in 86400 until 604800 -> SimpleDateFormat("EEE") // days
        in 604800 until 2629743 -> SimpleDateFormat("MMM") // months
        else -> {
            SimpleDateFormat("yyyy-MM") // year-month
        }
    }
    return formatter.format(beginning)
}