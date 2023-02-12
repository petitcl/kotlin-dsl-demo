package com.petitcl.domain4k.context

import com.petitcl.domain4k.fixtures.TestEvent
import com.petitcl.domain4k.stereotype.DomainEvent


class TestEventsContext : EventsContext {
    private val events = mutableListOf<DomainEvent>()
    fun events() = events.toList()
    override fun publishEvent(event: DomainEvent) {
        events.add(event)
        events.add(TestEvent("TestEventContext.publishEvent"))
    }
    override fun publishEvents(event: List<DomainEvent>) {
        events.addAll(event)
        events.add(TestEvent("TestEventContext.publishEvents"))
    }
}
