package me.okko.agatha.scribe

import io.ktor.features.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.okko.agatha.scribe.model.MongoSensorRecord
import me.okko.agatha.scribe.model.SensorRespond
import me.okko.agatha.scribe.model.asData
import me.okko.agatha.scribe.model.asMongo
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.security.SecureRandom

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
                ObjectId()
                val id = call.parameters["id"]
                if (id != null) {
                    val collection = database.getCollection<MongoSensorRecord>("p_$id")
                    call.respond(collection.find().toList().map { it.asData() })
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            post("/") {
                val sensorRespond = call.receive<SensorRespond>()
                val collection = database.getCollection<MongoSensorRecord>("p_${sensorRespond.id}")
                collection.insertOne(sensorRespond.asMongo())
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }.start(wait = true)
}

fun getRandomStringSupplier(strLength: Int) : () -> String {
    val random = SecureRandom()
    val bytes = ByteArray(strLength)
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return {
        random.nextBytes(bytes)
        (bytes.indices).map { charPool[random.nextInt(charPool.size)] }.joinToString("")
    }
}
