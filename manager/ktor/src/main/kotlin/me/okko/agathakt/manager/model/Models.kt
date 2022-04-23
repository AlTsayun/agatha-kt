package me.okko.agathakt.manager.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

fun JsonObject.Companion.fromString(str: String): JsonObject =
    Json.decodeFromString(JsonObject.serializer(), str)