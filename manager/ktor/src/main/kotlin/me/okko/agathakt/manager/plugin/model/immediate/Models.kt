package me.okko.agathakt.manager.plugin.model.immediate

import kotlinx.serialization.Serializable

enum class ImmediateOutputType {
    number,
    time,
    percent,
    text
}

@Serializable
sealed class ImmediateOutputData

@Serializable
data class Dynamics(val delta: Float, val isGrowthPositive: Boolean)

@Serializable
data class NumberData(val dynamics: Dynamics, val number: Float): ImmediateOutputData()

@Serializable
data class TimeData(val dynamics: Dynamics, val seconds: Int): ImmediateOutputData()

@Serializable
data class PercentData(val dynamics: Dynamics, val fraction: Float): ImmediateOutputData()

@Serializable
data class TextData(val statement: String, val description: String?): ImmediateOutputData()