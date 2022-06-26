package me.okko.agathakt.manager.plugin.chart

import me.okko.agathakt.manager.plugin.ChartPlugin
import me.okko.agathakt.manager.plugin.model.ChartOutput
import me.okko.agathakt.manager.plugin.model.chart.Chart
import me.okko.agathakt.manager.plugin.model.chart.ChartData
import me.okko.agathakt.manager.plugin.model.chart.ChartType
import me.okko.agathakt.manager.plugin.model.chart.LineDataset
import me.okko.agathakt.manager.plugin.timeLabel
import me.okko.agathakt.manager.plugin.unixRange
import java.util.*

abstract class LineChartPlugin : ChartPlugin {

    protected fun getChartOutput(
        periodToCount: MutableMap<IntRange, Int>,
        label: String,
        color: String = "lightblue"
    ) : ChartOutput {
        return ChartOutput(
            "Average page load time",
            Chart(
                ChartType.line,
                ChartData(
                    periodToCount.keys.toList().map { it.timeLabel() },
                    listOf(
                        LineDataset(
                            label,
                            periodToCount.values.toList(),
                            color,
                            color,
                        )
                    )
                )
            ),
        )
    }

    protected fun incPeriodValue(
        periodToCount: MutableMap<IntRange, Int>,
        time: Int,
        step: Int = 1
    ) {
        val firstNotNullOf = periodToCount.firstNotNullOf { it.takeIf { it.key.contains(time) } }
        periodToCount[firstNotNullOf.key] = periodToCount[firstNotNullOf.key]!! + step
    }

    protected fun getSubPeriodToCount(period: IntRange): MutableMap<IntRange, Int> {

        return period
            .chunked((period.last - period.first) / 12 ) { list -> list.first() until list.last() }
            .associateWith { 0 }
            .toMutableMap()
    }
}