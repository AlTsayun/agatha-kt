package me.okko.agathakt.scribe.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import me.okko.agathakt.common.model.SensorData
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.id.toId
import kotlinx.serialization.json.JsonObject as KotlinJsonObject
import org.bson.json.JsonObject as MongoJsonObject

@Serializable
data class SensorRespond(val id: String, val t: Int, val data: KotlinJsonObject)

fun SensorRespond.asMongo(senderId: Int): MongoSensorRecord {
    return MongoSensorRecord(ObjectId(this.t, senderId).toId(), this.data.toString())
}

@Serializable
data class MongoSensorRecord(@Contextual val _id: Id<MongoSensorRecord>, val d: String)

fun MongoSensorRecord.asData(): SensorData {
    val bytes = ObjectId(this._id.toString()).toByteArray()
    val timestampIndex = 0
    val timestamp = makeInt(
        bytes[timestampIndex],
        bytes[timestampIndex + 1],
        bytes[timestampIndex + 2],
        bytes[timestampIndex + 3]
    )
    val counterIndex = 9

    val counter = makeInt(
        0,
        bytes[counterIndex],
        bytes[counterIndex + 1],
        bytes[counterIndex + 2]
    )
    return SensorData(timestamp, counter, KotlinJsonObject.fromString(this.d))
}

fun KotlinJsonObject.asMongoDocument(): MongoJsonObject {
    return MongoJsonObject(this.toString())
}
fun MongoJsonObject.asKotlinJsonObject(): KotlinJsonObject {
    return Json.decodeFromString(KotlinJsonObject.serializer(), this.toString())
}

fun KotlinJsonObject.Companion.fromString(str: String): KotlinJsonObject =
    Json.decodeFromString(KotlinJsonObject.serializer(), str)


// Big-Endian helpers, in this class because all other BSON numbers are little-endian
private fun makeInt(b3: Byte, b2: Byte, b1: Byte, b0: Byte): Int {
    return b3.toInt() shl 24 or
            (b2.toInt() and 0xff shl 16) or
            (b1.toInt() and 0xff shl 8) or
            (b0.toInt() and 0xff)
}