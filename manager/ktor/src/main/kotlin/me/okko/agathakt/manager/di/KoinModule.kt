package me.okko.agathakt.manager.di

import me.okko.agathakt.manager.plugin.PluginFactory
import me.okko.agathakt.manager.plugin.PluginFactoryImpl
import me.okko.agathakt.manager.service.ScriptComposer
import me.okko.agathakt.manager.service.ScriptComposerImpl
import me.okko.agathakt.manager.service.SensorDataApiProvider
import me.okko.agathakt.manager.service.SensorDataApiProviderImpl
import me.okko.agathakt.manager.service.SensorDataLoader
import me.okko.agathakt.manager.service.SensorDataLoaderImpl
import org.koin.dsl.module

val koinModule = module(createdAtStart = true) {
    single { SensorDataLoaderImpl(get()) as SensorDataLoader }
    single { SensorDataApiProviderImpl() as SensorDataApiProvider }
    single { PluginFactoryImpl(get()) as PluginFactory }
    single { ScriptComposerImpl(get()) as ScriptComposer }
}