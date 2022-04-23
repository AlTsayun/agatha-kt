package me.okko.agathakt.manager.service

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.coroutines.*
import me.okko.agathakt.common.model.SensorData
import me.okko.agathakt.manager.repository.Meduim

interface SensorDataLoader {
    suspend fun loadSensorDataForMeduimByIdOrNull(meduimId: Int): Map<Int, List<SensorData>>?
}

class SensorDataLoaderImpl(private val apiProvider: SensorDataApiProvider) : SensorDataLoader {

    override suspend fun loadSensorDataForMeduimByIdOrNull(meduimId: Int): Map<Int, List<SensorData>>? {

        val meduim = Meduim.findById(meduimId) ?: return null

        val sensorTypes = meduim.plugins.map { it.sensorTypes }.flatten().distinct()

        val sensorTypeTpApis = sensorTypes.map { it.id.value }.associateWith { apiProvider.get(meduimId, it).values }
        return coroutineScope {
            HttpClient {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }.use { client ->
                sensorTypeTpApis.mapValues {
                    it.value.map { api ->
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

}