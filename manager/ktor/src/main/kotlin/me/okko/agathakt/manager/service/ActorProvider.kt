package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.model.ActorOverviewDto

interface ActorProvider {
    fun getByIdOrNull(id: Int): ActorOverviewDto?
}

class ActorProviderImpl : ActorProvider {
    override fun getByIdOrNull(id: Int): ActorOverviewDto? {
        TODO("Not yet implemented")
    }

}