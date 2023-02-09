package com.petitcl.domain4k.context

import java.time.Clock
import java.time.Instant

interface TimeContext {
    fun now(): Instant
    companion object
}

fun TimeContext.Companion.ofClock(clock: Clock): TimeContext = ClockTimeContext(clock)
internal class ClockTimeContext(private val clock: Clock) : TimeContext {
    override fun now(): Instant = clock.instant()
}

fun TimeContext.Companion.ofInstant(instant: Instant): TimeContext = FixedTimeContext(instant)
internal class FixedTimeContext(private val instant: Instant) : TimeContext {
    override fun now(): Instant = instant
}
