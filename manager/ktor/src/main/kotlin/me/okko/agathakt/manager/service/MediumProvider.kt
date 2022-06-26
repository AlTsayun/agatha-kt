package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.model.MediumEntryDto
import me.okko.agathakt.manager.repository.Actor
import me.okko.agathakt.manager.repository.Medium
import me.okko.agathakt.manager.repository.MediumTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

interface MediumProvider {
    fun getAllByActorIdOrNull(actorId: Int): List<MediumEntryDto>?
}

class MediumProviderImpl : MediumProvider {
    override fun getAllByActorIdOrNull(actorId: Int): List<MediumEntryDto>? {
        return transaction {
            Actor.findById(actorId)?. let {
                Medium
                    .find(MediumTable.actor eq it.id.value)
                    .map {
                        MediumEntryDto(it.id.value, it.name)
                    }

            }
        }
    }
}