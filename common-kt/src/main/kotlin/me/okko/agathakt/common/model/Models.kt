package me.okko.agathakt.common.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class SensorData(val t: Int, val data: JsonObject)