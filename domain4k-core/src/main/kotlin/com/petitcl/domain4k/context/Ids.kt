package com.petitcl.domain4k.context

import java.util.*

interface IdsContext {
    fun randomUUID(): UUID
    companion object
}

fun IdsContext.Companion.random(): IdsContext = RandomIdsContext
internal object RandomIdsContext : IdsContext {
    override fun randomUUID(): UUID = UUID.randomUUID()
}

fun IdsContext.Companion.fixed(seed: String): IdsContext = FixedIdsContext(seed)
internal class FixedIdsContext(private val seed: String) : IdsContext {
    override fun randomUUID(): UUID = UUID.nameUUIDFromBytes(seed.toByteArray())
}
