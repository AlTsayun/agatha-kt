package me.okko.agathakt.manager.plugin

import me.okko.agathakt.manager.repository.Meduim
import me.okko.agathakt.manager.service.SensorDataLoader

interface PluginFactory {
    fun getAllForMeduimById(meduimId: Int): List<Plugin>
    fun getByIdOrNull(id: Int): Plugin?
}

class PluginFactoryImpl(
    sensorDataLoader: SensorDataLoader
    ) : PluginFactory {

    private val plugins: Map<Int, Plugin> = mapOf(
        1 to AveragePageLoadTimePlugin(1, 1)
    )

    override fun getByIdOrNull(id: Int): Plugin? {
        return plugins[id]
    }

    override fun getAllForMeduimById(meduimId: Int): List<Plugin> {
        return Meduim[meduimId].plugins
            .map { it.id.value }
            .map { plugins[it] ?: throw RuntimeException("Plugin[$it] is not present in PluginFactory") }
    }
}