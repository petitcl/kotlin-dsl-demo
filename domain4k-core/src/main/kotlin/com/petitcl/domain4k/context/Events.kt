package com.petitcl.domain4k.context

import com.petitcl.domain4k.stereotype.DomainEvent
import com.petitcl.domain4k.stereotype.WithEvents

/**
 * Context for raising events from domain
 */
interface EventsContext {
    fun raiseEvent(event: DomainEvent)
    fun raiseEvents(event: List<DomainEvent>) = event.forEach { raiseEvent(it) }
    fun raiseEventsOf(withEvents: WithEvents<*>) = raiseEvents(withEvents.events)

    companion object
}

/**
 * Base interface for any object that can publish domain events.
 */
interface EventsPublisher {
    fun publishEvent(event: DomainEvent)
    fun publishEvents(event: List<DomainEvent>) = event.forEach { publishEvent(it) }
}

fun EventsContext.Companion.of(publisher: EventsPublisher): EventsContext = DefaultEventContext(publisher)
class DefaultEventContext(private val publisher: EventsPublisher) : EventsContext {
    override fun raiseEvent(event: DomainEvent) = publisher.publishEvent(event)
    override fun raiseEvents(event: List<DomainEvent>) = publisher.publishEvents(event)
}

class NoOpEventsPublisher : EventsPublisher {
    override fun publishEvent(event: DomainEvent) = Unit
    override fun publishEvents(event: List<DomainEvent>) = Unit
}

class CollectingEventsPublisher : EventsPublisher {
    private val events = mutableListOf<DomainEvent>()
    fun events() = events.toList()
    override fun publishEvent(event: DomainEvent) {
        events.add(event)
    }
    override fun publishEvents(event: List<DomainEvent>) {
        events.addAll(event)
    }
}

/**
 * Run a block of code and collect all events raised during the execution.
 * Events collected are flushed to the given publisher after the block is done executing.
 */
fun runAndCollectEvents(sink: EventsPublisher, block: context(EventsContext) () -> Unit) {
    val publisher = CollectingEventsPublisher()
    block(EventsContext.of(publisher))
    val events = publisher.events()
    sink.publishEvents(events)
}

/**
 * Run a block of code and collect all events raised during the execution.
 * Events collected are returned as a list.
 */
fun runAndCollectEvents(block: context(EventsContext) () -> Unit): List<DomainEvent> {
    val publisher = CollectingEventsPublisher()
    block(EventsContext.of(publisher))
    return publisher.events()
}

fun EventsContext.Companion.noOp(): EventsContext = DefaultEventContext(NoOpEventsPublisher())
