package me.okko.agatha.manager.service

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.coroutines.*

@Serializable
data class SensorData(val t: Int, val data: JsonObject)

interface SensorDataLoader {
    suspend fun getBySensorId(id: Int): List<SensorData>
}

class SensorDataLoaderImpl(private val apiProvider: SensorDataApiProvider) : SensorDataLoader {

    override suspend fun getBySensorId(id: Int): List<SensorData> {
        return coroutineScope {
            HttpClient {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }.use { client ->
                apiProvider.get().interpolate(id)
                    .map { api ->
                        async {
                            client.get<List<SensorData>>(api) {
                                onDownload { bytesSentTotal, contentLength ->
                                    println("Received $bytesSentTotal bytes of $contentLength from $api")
                                }
                                header("Accept", "application/json")
                            }
                        }
                    }
                    .map { loadFromApiTask -> loadFromApiTask.await() }.flatten();
            }
        }
    }

}