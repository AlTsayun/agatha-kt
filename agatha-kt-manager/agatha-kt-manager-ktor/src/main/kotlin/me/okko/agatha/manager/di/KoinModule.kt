package me.okko.agatha.manager.di

import me.okko.agatha.manager.repository.HelloRepository
import me.okko.agatha.manager.service.HelloService
import me.okko.agatha.manager.service.HelloServiceImpl
import org.koin.dsl.module

val koinModule = module(createdAtStart = true) {
    single {HelloServiceImpl(get()) as HelloService}
    single {HelloRepository()}
}