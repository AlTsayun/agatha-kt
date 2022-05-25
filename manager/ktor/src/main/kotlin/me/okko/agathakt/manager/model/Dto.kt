package me.okko.agathakt.manager.model

import kotlinx.serialization.Serializable

@Serializable
data class SensorTypeEntryDto(val id: Int, val name: String)

@Serializable
data class SensorTypeFullDto(val id: Int, val name: String)

@Serializable
data class PluginEntryDto(val id: Int, val name: String)

@Serializable
data class PluginFullDto(val id: Int, val name: String, val sensorTypes: List<SensorTypeEntryDto>)

@Serializable
data class PluginOutputDto(val id: Int, val name: String, val sensorTypes: List<SensorTypeEntryDto>)

@Serializable
data class SensorEntryDto(val id: Int, val url: String)

@Serializable
data class SensorFullDto(val id: Int, val url: String, val sensorType: SensorTypeEntryDto)

@Serializable
data class MediumEntryDto(val id: Int, val name: String)

@Serializable
data class MediumFullDto(
    val id: Int,
    val name: String,
    val sensors: List<SensorEntryDto>,
    val plugins: List<PluginEntryDto>
)

@Serializable
data class ActorOverviewDto(
    val id: Int,
    val firstName: String,
    val lastName: String
)