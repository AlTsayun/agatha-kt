package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.model.SensorEntryDto
import me.okko.agathakt.manager.model.SensorFullDto

interface SensorProvider {
    fun getAllByMediumIdOrNull(mediumId: Int): List<SensorEntryDto>?
    fun getByIdOrNull(id: Int): SensorFullDto?
}

class SensorProviderImpl : SensorProvider {
    override fun getAllByMediumIdOrNull(mediumId: Int): List<SensorEntryDto>? {
        TODO("Not yet implemented")
    }

    override fun getByIdOrNull(id: Int): SensorFullDto? {
        TODO("Not yet implemented")
    }
}