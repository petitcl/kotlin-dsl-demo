package com.petitcl.domain4k.stereotype

/**
 * Base interface for overridden equality
 */
interface Eq {
    fun eq(other: Any?): Boolean
}

infix fun Eq.eq(other: Any?): Boolean = this.eq(other)
