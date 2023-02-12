package com.petitcl.test.testdomain.domain

import com.petitcl.domain4k.stereotype.DomainEntityWithEvents
import com.petitcl.domain4k.stereotype.DomainEvent
import java.time.Instant
import java.util.*

data class LedgerEntry(
    override val id: UUID,
    val from: String,
    val to: String,
    val amount: Double,
    val date: Instant,
    override val events: List<DomainEvent> = listOf()
) : DomainEntityWithEvents<UUID, LedgerEntry> {

    override fun eq(other: Any?): Boolean = other != null
            && (other is LedgerEntry)
            && (this.id == other.id)

    override fun addEvent(event: DomainEvent) = this.copy(events = this.events + event)

    override fun clearEvents() = this.copy(events = listOf())

    companion object {

        fun newEntry(
            id: UUID = UUID.randomUUID(),
            from: String,
            to: String,
            amount: Double,
            date: Instant = Instant.now(),
        ) = LedgerEntry(id, from, to, amount, date)
            .let {
                it.addEvent(LedgerEntryCreatedEvent(it.id, it.from, it.to, it.amount, it.date))
            }
    }

}

data class LedgerEntryCreatedEvent(
    val id: UUID,
    val from: String,
    val to: String,
    val amount: Double,
    val date: Instant,
) : DomainEvent
