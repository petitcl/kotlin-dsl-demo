package com.petitcl.sample.domain

import com.petitcl.domain4k.context.EventsContext
import com.petitcl.domain4k.stereotype.DomainEntity
import com.petitcl.domain4k.stereotype.DomainEvent
import java.util.UUID

data class Cart(
    override val id: UUID,
) : DomainEntity<UUID, Cart> {
    override fun eq(other: Any?): Boolean = other != null
            && (other is Cart)
            && (this.id == other.id)

    companion object
}

context(EventsContext)
fun Cart.Companion.newCart(): Cart
    = Cart(id = UUID.randomUUID()).also { raiseEvent(CartCreated(it.id)) }

data class CartCreated(
    val cartId: UUID,
) : DomainEvent
