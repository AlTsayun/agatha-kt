package me.okko.agathakt.scribe

import io.ktor.features.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.okko.agathakt.scribe.model.MongoSensorRecord
import me.okko.agathakt.scribe.model.SensorRespond
import me.okko.agathakt.scribe.model.asData
import me.okko.agathakt.scribe.model.asMongo
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.security.SecureRandom
import java.util.*

import org.bson.json.JsonObject as MongoJsonObject

val client = KMongo.createClient("mongodb://root:example@localhost:27017").coroutine
val database = client.getDatabase("census")
//val saltSupplier = getRandomStringSupplier(3)

fun main() {
    embeddedServer(Netty, port = 8081, host = "localhost") {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Options)
            anyHost()
            allowSameOrigin = true
            allowNonSimpleContentTypes = true
            allowHeaders {
                it == HttpHeaders.ContentType
            }
        }
        routing {
            get("/{id}") {
                val id = call.parameters["id"]
                val from = call.request.queryParameters["from"]?.toInt() ?: 0
                val to = call.request.queryParameters["to"]?.toInt() ?: (System.currentTimeMillis() / 1000).toInt()

                call.respond(
                    if (id != null) {
                        database.getCollection<MongoSensorRecord>("s_$id")
                            .find().toList().map { it.asData() }.filter { it.t in from..to }
                    } else {
                        HttpStatusCode.NotFound
                    }
                )
            }

            post("/") {
                val sensorRespond = call.receive<SensorRespond>()
                val collection = database.getCollection<MongoSensorRecord>("s_${sensorRespond.id}")
                val hashCode = call.request.origin.remoteHost.hashCode() and 0x00_ff_ff_ff
                collection.insertOne(sensorRespond.asMongo(hashCode))
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }.start(wait = true)
}

fun getRandomStringSupplier(strLength: Int): () -> String {
    val random = SecureRandom()
    val bytes = ByteArray(strLength)
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return {
        random.nextBytes(bytes)
        (bytes.indices).map { charPool[random.nextInt(charPool.size)] }.joinToString("")
    }
}
