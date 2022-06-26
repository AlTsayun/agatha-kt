package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.repository.Sensor
import java.nio.file.Path
import kotlin.io.path.createTempFile

interface ScriptComposer {
    fun getByMedium(mediumId: Int): Path
}

class ScriptComposerImpl() : ScriptComposer {
    override fun getByMedium(mediumId: Int): Path {
//        var sensorTypes = (Medium.findById(mediumId) ?: throw RuntimeException()).plugins
//                .mapLazy { plugin ->
//                    plugin.sensorTypes
//                }
//                .flatten()
//                .distinct()
//
//        sensorTypes.forEach {
//            TODO()
//        }
//
//        (Medium.findById(mediumId)?: throw RuntimeException()).sensors

        val file = createTempFile()
//        ScriptComposerImpl::class.java.classLoader.getResourceAsStream("script_template.js").use {
//            Files.copy(it, file, StandardCopyOption.REPLACE_EXISTING)
//        }
        return file
    }
}