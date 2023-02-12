package com.petitcl.sample.domain

import com.petitcl.domain4k.context.EventsContext
import com.petitcl.domain4k.context.TimeContext
import com.petitcl.domain4k.stereotype.DomainEntity
import com.petitcl.domain4k.stereotype.DomainEvent
import java.time.Instant
import java.util.UUID

data class Cart(
    override val id: UUID,
    val creationTime: Instant,
) : DomainEntity<UUID, Cart> {
    override fun eq(other: Any?): Boolean = other != null
            && (other is Cart)
            && (this.id == other.id)

    companion object
}

context(EventsContext, TimeContext)
fun Cart.Companion.newCart(): Cart
    = Cart(
        id = UUID.randomUUID(),
        creationTime = now(),
      ).also { raiseEvent(CartCreated(it.id, it.creationTime)) }

data class CartCreated(
    val cartId: UUID,
    val creationTime: Instant,
) : DomainEvent
