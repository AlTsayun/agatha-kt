package me.okko.agatha.manager.di

import me.okko.agatha.manager.plugin.PluginFactory
import me.okko.agatha.manager.plugin.PluginFactoryImpl
import me.okko.agatha.manager.service.SensorDataApiProvider
import me.okko.agatha.manager.service.SensorDataApiProviderImpl
import me.okko.agatha.manager.service.SensorDataLoader
import me.okko.agatha.manager.service.SensorDataLoaderImpl
import org.koin.dsl.module

val koinModule = module(createdAtStart = true) {
    single { SensorDataLoaderImpl(get()) as SensorDataLoader }
    single { SensorDataApiProviderImpl() as SensorDataApiProvider }
    single { PluginFactoryImpl(get()) as PluginFactory }
}