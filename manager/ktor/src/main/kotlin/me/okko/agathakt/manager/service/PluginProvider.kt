package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.model.PluginEntryDto
import me.okko.agathakt.manager.model.PluginFullDto

interface PluginProvider {
    fun getAllByMediumIdOrNull(mediumId: Int): List<PluginEntryDto>?
    fun getAll(): List<PluginEntryDto>
    fun getByIdOrNull(id: Int): PluginFullDto?
}

class PluginProviderImpl : PluginProvider {
    override fun getAllByMediumIdOrNull(mediumId: Int): List<PluginEntryDto>? {
        TODO("Not yet implemented")
    }
    override fun getAll(): List<PluginEntryDto> {
        TODO("Not yet implemented")
    }

    override fun getByIdOrNull(id: Int): PluginFullDto? {
        TODO("Not yet implemented")
    }
}