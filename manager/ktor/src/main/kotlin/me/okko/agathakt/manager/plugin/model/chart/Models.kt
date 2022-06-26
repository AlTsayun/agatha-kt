package me.okko.agathakt.manager.plugin.model.chart

import kotlinx.serialization.Serializable

enum class ChartType {
    bar,
    doughnut,
    line,
    pie
}

@Serializable
sealed class Dataset

@Serializable
data class BarDataset(
    val label: String,
    val data: List<Int>,
    val borderColor: String?,
) : Dataset()

@Serializable
data class LineDataset(
    val label: String,
    val data: List<Int>,
    val borderColor: String?,
    val backgroundColor: String?
) : Dataset()

@Serializable
data class PieDataset(
    val data: List<Int>,
    val backgroundColor: List<String>
) : Dataset()

@Serializable
data class ChartData(val labels: List<String>, val datasets: List<Dataset>)

@Serializable
data class Chart(val type: ChartType, val data: ChartData)
