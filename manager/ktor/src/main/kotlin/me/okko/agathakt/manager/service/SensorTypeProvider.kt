package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.model.SensorTypeEntryDto
import me.okko.agathakt.manager.model.SensorTypeFullDto

interface SensorTypeProvider {
    fun getByIdOrNull(id: Int): SensorTypeFullDto?
    fun getAllByMediumIdOrNull(id: Int): List<SensorTypeEntryDto>?
}

class SensorTypeProviderImpl : SensorTypeProvider {
    override fun getByIdOrNull(id: Int): SensorTypeFullDto? {
        TODO("Not yet implemented")
    }

    override fun getAllByMediumIdOrNull(id: Int): List<SensorTypeEntryDto>? {
        TODO("Not yet implemented")
    }
}