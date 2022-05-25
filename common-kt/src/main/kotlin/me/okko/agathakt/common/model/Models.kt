package me.okko.agathakt.common.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * @param t timestamp
 * @param s sender id
 */
@Serializable
data class SensorData(val t: Int, val s: Int, val data: JsonObject)