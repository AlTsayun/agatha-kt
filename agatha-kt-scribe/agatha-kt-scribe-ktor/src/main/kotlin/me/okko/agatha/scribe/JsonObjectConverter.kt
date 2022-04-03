package me.okko.agatha.scribe

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import org.bson.json.JsonObject
import org.litote.kmongo.json
import java.nio.charset.Charset

object JsonAsStringSerializer: JsonTransformingSerializer<JsonObject>(tSerializer = JsonObjectSerializer(String.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return JsonPrimitive(value = element.toString())
    }
}

class JsonObjectSerializer(private val stringSerializer: KSerializer<String>) : KSerializer<JsonObject> {

    override fun deserialize(decoder: Decoder): JsonObject {
        return JsonObject(stringSerializer.deserialize(decoder))
    }

    override fun serialize(encoder: Encoder, value: JsonObject) {
        stringSerializer.serialize(encoder, value.json)
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        JsonObject::class.qualifiedName!!,
        PrimitiveKind.STRING
    )

    internal class PrimitiveSerialDescriptor(
        override val serialName: String,
        override val kind: PrimitiveKind
    ) : SerialDescriptor {
        override val elementsCount: Int get() = 0
        override fun getElementName(index: Int): String = error()
        override fun getElementIndex(name: String): Int = error()
        override fun isElementOptional(index: Int): Boolean = error()
        override fun getElementDescriptor(index: Int): SerialDescriptor = error()
        override fun getElementAnnotations(index: Int): List<Annotation> = error()
        override fun toString(): String = "PrimitiveDescriptor($serialName)"
        private fun error(): Nothing = throw IllegalStateException("Primitive descriptor does not have elements")
    }
}

class JsonObjectConverter : ContentConverter {
    override suspend fun deserialize(charset: Charset, typeInfo: TypeInfo, content: ByteReadChannel): Any? {
        // so that SensorRecord is not deserialized with this converter
        // return JsonObject(String(content.toByteArray(), charset))
        return null;
    }

    override suspend fun serialize(
        contentType: ContentType,
        charset: Charset,
        typeInfo: TypeInfo,
        value: Any
    ): OutgoingContent? {
        if (contentType == ContentType.Application.Json)
        {
            if (value is JsonObject)
            {
                return ByteArrayContent(value.json.toByteArray(charset), ContentType.Application.Json)
            } else if (value is List<*>) {
                val values = value.map { (it as JsonObject).json }.joinToString(prefix = "{", postfix = "}", separator = ",")
                return ByteArrayContent(values.toByteArray(charset), ContentType.Application.Json)
            }
        }
        return null;
    }
}