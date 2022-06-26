package me.okko.agathakt.manager.service

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import me.okko.agathakt.common.model.SensorData

interface SensorDataLoader {
    suspend fun loadSensorData(sensorIdToUrl: Map<Int, String>, period: IntRange): Map<Int, List<SensorData>>
}

class SensorDataLoaderImpl : SensorDataLoader {

    override suspend fun loadSensorData(sensorIdToUrl: Map<Int, String>, period: IntRange): Map<Int, List<SensorData>> {
        return coroutineScope {
            HttpClient {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }.use { client ->
                sensorIdToUrl
                    .mapValues { idToUrl ->
                        async {
                            client.get<List<SensorData>>(idToUrl.value) {
                                url {
                                    parameters.append("from", period.first.toString())
                                    parameters.append("to", period.last.toString())
                                }
                                header("Accept", "application/json")
                            }
                        }
                    }
                    .mapValues { it.value.await() }
            }
        }
    }
}