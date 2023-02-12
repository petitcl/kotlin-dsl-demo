package com.petitcl.domain4k.fixtures

import com.petitcl.domain4k.stereotype.DomainEvent
import com.petitcl.domain4k.stereotype.WithEvents

class A
class B

context(A)
fun doA() = "contextA"

context(B)
fun doB() = "contextB"

data class TestEvent(val data: String) : DomainEvent

data class TestWithEvents(
    val data: String,
    override val events: List<DomainEvent>
) : WithEvents<TestWithEvents> {
    override fun addEvent(event: DomainEvent) = this.copy(events = this.events + event)

    override fun clearEvents() = this.copy(events = listOf())
}
