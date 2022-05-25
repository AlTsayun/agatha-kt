package me.okko.agathakt.manager.plugin.model

import kotlinx.serialization.Serializable
import me.okko.agathakt.manager.plugin.model.chart.Chart
import me.okko.agathakt.manager.plugin.model.immediate.ImmediateOutputData
import me.okko.agathakt.manager.plugin.model.immediate.ImmediateOutputType

@Serializable
sealed class Output

@Serializable
data class ChartOutput(val name: String, val chart: Chart) : Output()

@Serializable
data class ImmediateOutput(
    val name: String,
    val type: ImmediateOutputType,
    val tooltip: String?,
    val data:ImmediateOutputData
) : Output()