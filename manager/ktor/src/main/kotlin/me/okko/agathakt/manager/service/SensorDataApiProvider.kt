package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.repository.Meduim

interface SensorDataApiProvider {
    suspend fun get(mediumId: Int, sensorTypeId: Int): Map<Int, String>
}
class SensorDataApiProviderImpl : SensorDataApiProvider {
    override suspend fun get(mediumId: Int, sensorTypeId: Int): Map<Int, String> =
        Meduim[mediumId].sensors
            .filter { it.type.id.value == sensorTypeId }
            .associate { it.id.value to it.url }
}


