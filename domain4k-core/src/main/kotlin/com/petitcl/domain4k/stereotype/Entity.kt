package com.petitcl.domain4k.stereotype


/**
 * Marker interface for a domain entity
 * @param Id the type of the entity's id (eg: String, Long, UUID, etc.)
 * @param T the type of the entity
 */
interface DomainEntity<Id, T> : Eq where T : DomainEntity<Id, T> {
    val id: Id
}

/**
 * Marker interface for a class that can accumulate domain events
 * @param Id the type of the entity's id (eg: String, Long, UUID, etc.)
 * @param T the type of the entity
 */
interface DomainEntityWithEvents<Id, T> : DomainEntity<Id, T>, WithEvents<T> where T : DomainEntityWithEvents<Id, T>
