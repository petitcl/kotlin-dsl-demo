package com.petitcl.domain4k.context

import java.time.Clock
import java.time.Instant

/**
 * Context for injecting time concern into domain
 */
interface TimeContext {
    /**
     * Return the current date and time
     */
    fun now(): Instant

    companion object
}

/**
 * Create a TimeContext using the default system clock
 */
fun TimeContext.Companion.default(): TimeContext = ClockTimeContext(Clock.systemDefaultZone())

/**
 * Create a TimeContext using the specified clock
 */
fun TimeContext.Companion.ofClock(clock: Clock): TimeContext = ClockTimeContext(clock)

internal class ClockTimeContext(private val clock: Clock) : TimeContext {
    override fun now(): Instant = clock.instant()
}

/**
 * Create a TimeContext that always returns the specified instant
 */
fun TimeContext.Companion.ofInstant(instant: Instant): TimeContext = FixedTimeContext(instant)

internal class FixedTimeContext(private val instant: Instant) : TimeContext {
    override fun now(): Instant = instant
}
