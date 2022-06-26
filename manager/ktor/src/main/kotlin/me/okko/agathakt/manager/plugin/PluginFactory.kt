package me.okko.agathakt.manager.plugin

import me.okko.agathakt.manager.plugin.chart.DeclinesPlugin
import me.okko.agathakt.manager.plugin.chart.DeviceTypePlugin
import me.okko.agathakt.manager.plugin.chart.VisitorsPlugin
import me.okko.agathakt.manager.plugin.immediate.AveragePageLoadTimePlugin
import me.okko.agathakt.manager.repository.Medium
import me.okko.agathakt.manager.service.SensorDataLoader

interface PluginFactory {
    fun getAllForMeduimById(meduimId: Int): List<Plugin>
    fun getByIdOrNull(id: Int): Plugin?
}

class PluginFactoryImpl(
    sensorDataLoader: SensorDataLoader
    ) : PluginFactory {

    private val plugins: Map<Int, Plugin> = mapOf(
        1 to AveragePageLoadTimePlugin(1, 1),
        2 to DeclinesPlugin(1, 2),
        3 to DeviceTypePlugin(1, 3),
        4 to VisitorsPlugin(1, 2),
    )

    override fun getByIdOrNull(id: Int): Plugin? {
        return plugins[id]
    }

    override fun getAllForMeduimById(meduimId: Int): List<Plugin> {
        return Medium[meduimId].plugins
            .map { it.id.value }
            .map { plugins[it] ?: throw RuntimeException("Plugin[$it] is not present in PluginFactory") }
    }
}