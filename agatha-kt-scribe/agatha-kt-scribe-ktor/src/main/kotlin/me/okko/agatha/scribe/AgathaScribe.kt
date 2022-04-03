package me.okko.agatha.scribe

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.bson.json.JsonObject

val client = KMongo.createClient("mongodb://root:example@localhost:27017").coroutine
val database = client.getDatabase("census")

//@kotlinx.serialization.Serializable
data class SensorRecord(
    val id: String,
//    @kotlinx.serialization.Serializable(with = JsonAsStringSerializer::class)
    val data: JsonObject
    );

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        install(ContentNegotiation) {
//            json(Json {
//                prettyPrint = true
//                isLenient = true
//            })
            register(ContentType.Application.Json, JsonObjectConverter())
            register(ContentType.Application.Json, SensorRecordConverter())
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Options)
            anyHost()
            allowSameOrigin = true
            allowNonSimpleContentTypes = true
        }
        routing {
            get("/{id}") {
                val id = call.parameters["id"]
                if (id != null) {
                    val collection = database.getCollection<JsonObject>(id)
                    println(collection.find().toList())
                    call.respond(collection.find().toList())
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            post("/") {
                val sensorRecord = call.receive<SensorRecord>()
                val collection = database.getCollection<JsonObject>(sensorRecord.id)
                collection.insertOne(sensorRecord.data)
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }.start(wait = true)
}
