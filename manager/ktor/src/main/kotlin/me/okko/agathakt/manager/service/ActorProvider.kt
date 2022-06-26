package me.okko.agathakt.manager.service

import me.okko.agathakt.manager.model.ActorOverviewDto
import me.okko.agathakt.manager.repository.Actor
import org.jetbrains.exposed.sql.transactions.transaction

interface ActorProvider {
    fun getByIdOrNull(id: Int): ActorOverviewDto?
    fun getLoggedInIdOrNull(): Int?

    fun logIn(id: Int)
}

class ActorProviderImpl : ActorProvider {

    private var loggedIn: Int? = null

    override fun getByIdOrNull(id: Int): ActorOverviewDto? {
        TODO("Not yet implemented")
    }

    override fun getLoggedInIdOrNull(): Int? = loggedIn

    override fun logIn(id: Int) {
        transaction {
            loggedIn = Actor.findById(id)?.id?.value ?: throw RuntimeException("No user with id $id is registered")
        }
    }

}