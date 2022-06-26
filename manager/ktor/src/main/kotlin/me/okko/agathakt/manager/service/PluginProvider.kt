package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.model.PluginTypeEntryDto
import me.okko.agathakt.manager.model.PluginTypeFullDto

interface PluginProvider {
    fun getAllByMediumIdOrNull(mediumId: Int): List<PluginTypeEntryDto>?
    fun getAll(): List<PluginTypeEntryDto>
    fun getByIdOrNull(id: Int): PluginTypeFullDto?
}

class PluginProviderImpl : PluginProvider {
    override fun getAllByMediumIdOrNull(mediumId: Int): List<PluginTypeEntryDto>? {
        TODO("Not yet implemented")
    }
    override fun getAll(): List<PluginTypeEntryDto> {
        TODO("Not yet implemented")
    }

    override fun getByIdOrNull(id: Int): PluginTypeFullDto? {
        TODO("Not yet implemented")
    }
}