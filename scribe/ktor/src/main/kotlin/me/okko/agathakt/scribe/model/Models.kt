package me.okko.agathakt.scribe.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import me.okko.agathakt.common.model.SensorData
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.id.toId
import kotlinx.serialization.json.JsonObject as KotlinJsonObject
import org.bson.json.JsonObject as MongoJsonObject

@Serializable
data class SensorRespond(val id: String, val t: Int, val data: KotlinJsonObject)

fun SensorRespond.asMongo(): MongoSensorRecord {
    return MongoSensorRecord(ObjectId(this.t, 0).toId(), this.data.toString())
}

@Serializable
data class MongoSensorRecord(@Contextual val _id: Id<MongoSensorRecord>, val d: String)

fun MongoSensorRecord.asData(): SensorData {
    return SensorData(ObjectId(this._id.toString()).timestamp, KotlinJsonObject.fromString(this.d))
}

fun KotlinJsonObject.asMongoDocument(): MongoJsonObject {
    return MongoJsonObject(this.toString())
}
fun MongoJsonObject.asKotlinJsonObject(): KotlinJsonObject {
    return Json.decodeFromString(KotlinJsonObject.serializer(), this.toString())
}

fun KotlinJsonObject.Companion.fromString(str: String): KotlinJsonObject =
    Json.decodeFromString(KotlinJsonObject.serializer(), str)