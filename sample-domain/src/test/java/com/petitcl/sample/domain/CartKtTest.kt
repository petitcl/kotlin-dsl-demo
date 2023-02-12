package com.petitcl.sample.domain

import com.petitcl.domain4k.context.runAndCollectEvents
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CartKtTest {

    @Test
    fun `test create new cart with events context`() {

        val events = runAndCollectEvents {
            val cart = Cart.newCart()
            assertNotNull(cart.id)
        }
        assertEquals(1, events.size)
        assertTrue(events[0] is CartCreated)
    }
}