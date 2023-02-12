package com.petitcl.domain4k.stereotype

/**
 * Marker interface for a domain event
 */
interface DomainEvent

/**
 * Marker interface for an immutable class that can accumulate events
 */
interface WithEvents<T> where T : WithEvents<T> {
    /**
     * The list of events
     */
    val events: List<DomainEvent>

    /**
     * Adds an event to the list of events
     * @param event the event to add
     * @return a new instance with the event added
     */
    fun addEvent(event: DomainEvent): T

    /**
     * Adds a list of events to the list of events
     * @param events the events to add
     * @return a new instance with the events added
     */
    @Suppress("UNCHECKED_CAST")
    fun addEvents(events: List<DomainEvent>): T = events.fold(this as T) { acc, event -> acc.addEvent(event) }

    /**
     * Remove all events from the list of events
     * @return a new instance with no events
     */
    fun clearEvents(): T
}