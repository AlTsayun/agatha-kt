package me.okko.agatha.scribe

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.*
import io.ktor.util.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.*
import org.bson.json.JsonObject
import java.nio.charset.Charset

class SensorRecordConverter : ContentConverter {
    override suspend fun deserialize(charset: Charset, typeInfo: TypeInfo, content: ByteReadChannel): Any? {
        val data = String(content.toByteArray(), charset)
        val jsonObject = Json.parseToJsonElement(data).jsonObject
        return SensorRecord(
            jsonObject["id"]!!.jsonPrimitive.content,
            JsonObject(jsonObject["data"]!!.toString())
        )
    }

    override suspend fun serialize(
        contentType: ContentType,
        charset: Charset,
        typeInfo: TypeInfo,
        value: Any
    ): OutgoingContent? {
        return null;
    }
}