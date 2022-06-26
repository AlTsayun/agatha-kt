package me.okko.agathakt.manager.di

import me.okko.agathakt.manager.plugin.PluginFactory
import me.okko.agathakt.manager.plugin.PluginFactoryImpl
import me.okko.agathakt.manager.service.ActorProvider
import me.okko.agathakt.manager.service.ActorProviderImpl
import me.okko.agathakt.manager.service.MediumProvider
import me.okko.agathakt.manager.service.MediumProviderImpl
import me.okko.agathakt.manager.service.PluginProvider
import me.okko.agathakt.manager.service.PluginProviderImpl
import me.okko.agathakt.manager.service.ScriptComposer
import me.okko.agathakt.manager.service.ScriptComposerImpl
import me.okko.agathakt.manager.service.SensorDataLoader
import me.okko.agathakt.manager.service.SensorDataLoaderImpl
import me.okko.agathakt.manager.service.SensorProvider
import me.okko.agathakt.manager.service.SensorProviderImpl
import me.okko.agathakt.manager.service.SensorTypeProvider
import me.okko.agathakt.manager.service.SensorTypeProviderImpl
import org.koin.dsl.module

val koinModule = module(createdAtStart = true) {
    single { SensorDataLoaderImpl() as SensorDataLoader }
    single { PluginFactoryImpl(get()) as PluginFactory }
    single { ScriptComposerImpl() as ScriptComposer }
    single { PluginProviderImpl() as PluginProvider }
    single { SensorTypeProviderImpl() as SensorTypeProvider }
    single { ActorProviderImpl() as ActorProvider }
    single { SensorProviderImpl() as SensorProvider }
    single { MediumProviderImpl() as MediumProvider }
}