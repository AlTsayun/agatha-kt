package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.repository.Meduim
import me.okko.agathakt.manager.repository.Sensor
import me.okko.agathakt.manager.repository.SensorType
import org.jetbrains.exposed.sql.mapLazy
import java.io.File
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.createTempFile

interface ScriptComposer {
    fun get(mediumId: Int): Path
}

class ScriptComposerImpl(private val apiProvider: SensorDataApiProvider) : ScriptComposer {
    override fun get(mediumId: Int): Path {
        var sensorTypes = (Meduim.findById(mediumId) ?: throw RuntimeException()).plugins
                .mapLazy { plugin ->
                    plugin.sensorTypes
                }
                .flatten()
                .distinct()

        sensorTypes.forEach {
            TODO()
        }

        (Meduim.findById(mediumId)?: throw RuntimeException()).sensors

        val file = createTempFile()
        ScriptComposerImpl::class.java.classLoader.getResourceAsStream("script_template.js").use {
            Files.copy(it, file, StandardCopyOption.REPLACE_EXISTING)
        }
        return file
    }

    private fun get(sensor: Sensor) : String {
        TODO()
    }
}