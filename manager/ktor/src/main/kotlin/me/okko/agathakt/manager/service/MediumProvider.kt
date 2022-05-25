package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.model.MediumEntryDto

interface MediumProvider {
    fun getAllByActorIdOrNull(actorId: Int): List<MediumEntryDto>?
}

class MediumProviderImpl : MediumProvider {
    override fun getAllByActorIdOrNull(actorId: Int): List<MediumEntryDto>? {
        TODO("Not yet implemented")
    }
}