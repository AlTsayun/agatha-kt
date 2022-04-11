package me.okko.agatha.manager.plugin

import me.okko.agatha.manager.service.SensorDataLoader

interface PluginFactory {
    fun getByIdOrNull(id: Int): Plugin?
}

class PluginFactoryImpl(
    sensorDataLoader: SensorDataLoader
    ) : PluginFactory {

    private val plugins: Map<Int, Plugin> = mapOf(
        1 to AveragePageLoadTimePlugin(1, sensorDataLoader)
    )

    override fun getByIdOrNull(id: Int): Plugin? {
        return plugins[id]
    }

}