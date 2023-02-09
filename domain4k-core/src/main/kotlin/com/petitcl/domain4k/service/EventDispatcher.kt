package com.petitcl.domain4k.service

import com.petitcl.domain4k.stereotype.DomainEvent

interface EventDispatcher {
    fun publish(event: DomainEvent)
}
