package me.okko.agathakt.manager.model

import kotlinx.serialization.Serializable

@Serializable
data class SensorTypeEntryDto(val id: Int, val name: String)

@Serializable
data class SensorTypeFullDto(
    val id: Int,
    val type: PluginType,
    val name: String,
    val description: String,
    val imageLink: String?
)

enum class PluginType {
    immediate,
    chart
}

@Serializable
data class PluginTypeEntryDto(val id: Int, val name: String)

@Serializable
data class PluginTypeFullDto(
    val id: Int,
    val type: PluginType,
    val name: String,
    val description: String,
    val image: String?,
    val sensorTypes: List<SensorTypeEntryDto>
)
@Serializable
data class PluginFullDto(
    val id: Int,
    val type: PluginTypeFullDto,
    val isDashboardVisible: Boolean
)

@Serializable
data class PluginOutputDto(val id: Int, val name: String, val sensorTypes: List<SensorTypeEntryDto>)

@Serializable
data class SensorEntryDto(val id: Int, val url: String)

@Serializable
data class SensorFullDto(val id: Int, val type: SensorTypeFullDto, val url: String)

@Serializable
data class MediumEntryDto(val id: Int, val name: String)

@Serializable
data class MediumFullDto(
    val id: Int,
    val name: String,
    val description: String,
    val sensors: List<SensorEntryDto>,
    val plugins: List<PluginTypeEntryDto>
)

@Serializable
data class ActorOverviewDto(
    val id: Int,
    val image: String?,
    val firstName: String,
    val lastName: String
)