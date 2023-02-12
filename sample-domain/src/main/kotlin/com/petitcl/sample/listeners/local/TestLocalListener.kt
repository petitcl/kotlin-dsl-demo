package com.petitcl.sample.listeners.local

import com.petitcl.sample.domain.ProductCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class TestLocalListener {

     @EventListener
     fun onProductCreated(event: ProductCreatedEvent) {
         println("TestLocalListener.onProductCreated: $event")
     }
}
