package me.okko.agathakt.manager.plugin.model

import kotlinx.serialization.Serializable

@Serializable
data class LoadTimeSensorData(val t: Int)

@Serializable
data class VisitTimeSensorData(val t: Int)


enum class DeviceType {
    tablet,
    mobile,
    desktop
}

@Serializable
data class DeviceTypeSensorData(val d: DeviceType)
