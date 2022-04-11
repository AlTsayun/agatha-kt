package me.okko.agatha.manager.service

interface SensorDataApiProvider {
    suspend fun get(): SensorDataApi
    interface SensorDataApi {
        fun interpolate(sensorId: Int): List<String>
    }
}
class SensorDataApiProviderImpl : SensorDataApiProvider {
    override suspend fun get(): SensorDataApiProvider.SensorDataApi {
        return object : SensorDataApiProvider.SensorDataApi {
            override fun interpolate(sensorId: Int): List<String> =
                //todo: inject current meduim to get scribe url
                listOf("http://localhost:8081/$sensorId")
        }
    }
}


