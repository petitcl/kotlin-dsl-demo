package com.petitcl.domain4k.context

import com.petitcl.domain4k.stereotype.DomainEvent
import com.petitcl.domain4k.stereotype.WithEvents
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class A
class B

context(A)
fun doA() = "contextA"

context(B)
fun doB() = "contextB"

data class TestEvent(val data: String) : DomainEvent

data class TestWithEvents(val data: String, override val events: List<DomainEvent>) : WithEvents<TestWithEvents> {
    override fun addEvent(event: DomainEvent) = this.copy(events = this.events + event)

    override fun clearEvents() = this.copy(events = listOf())

}


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


class EventsTest {

    @Test
    fun `runAndCollectEvents should collect all events raised in block`() {
        val collector = CollectingEventsContext()
        runAndCollectEvents(collector) {
            publishEvent(TestEvent("TestEvent1"))
            publishEvents(listOf(TestEvent("TestEvent2"), TestEvent("TestEvent3")))
            val testWithEvents = TestWithEvents("TestWithEvents", listOf(TestEvent("TestEvent4")))
            publishEventsOf(testWithEvents)
        }
        val events = collector.events()

        assertThat(events)
            .containsExactly(
                TestEvent("TestEvent1"),
                TestEvent("TestEvent2"),
                TestEvent("TestEvent3"),
                TestEvent("TestEvent4")
            )
    }

    @Test
    fun `runAndCollectEvents should publish events after the block is finished`() {
        val collector = TestEventsContext()
        runAndCollectEvents(collector) {
            publishEvent(TestEvent("TestEvent1"))
            publishEvent(TestEvent("TestEvent2"))
        }
        val events = collector.events()

        assertThat(events)
            .containsExactly(
                TestEvent("TestEvent1"),
                TestEvent("TestEvent2"),
                TestEvent("TestEventContext.publishEvents"),
            )
    }

    @Test
    fun `runAndCollectEvents should allow to use additional contexts in block`() {
        val collector = CollectingEventsContext()
        runAndCollectEvents(collector, A(), B()) {
            publishEvent(TestEvent(doA()))
            publishEvent(TestEvent(doB()))
        }
        val events = collector.events()

        assertThat(events)
            .containsExactly(
                TestEvent("contextA"),
                TestEvent("contextB")
            )
    }

    @Test
    fun `runAndCollectEvents should forward return value`() {
        val collector = CollectingEventsContext()
        val result = runAndCollectEvents(collector) {
            42
        }

        assertThat(result).isEqualTo(42)
    }

    @Test
    fun `runAndReturnEvents should return all events raised in block`() {
        val events = runAndReturnEvents {
            publishEvent(TestEvent("TestEvent1"))
            publishEvents(listOf(TestEvent("TestEvent2"), TestEvent("TestEvent3")))
            val testWithEvents = TestWithEvents("TestWithEvents", listOf(TestEvent("TestEvent4")))
            publishEventsOf(testWithEvents)
        }

        assertThat(events)
            .containsExactly(
                TestEvent("TestEvent1"),
                TestEvent("TestEvent2"),
                TestEvent("TestEvent3"),
                TestEvent("TestEvent4")
            )
    }

    @Test
    fun `runAndReturnEvents should allow to use additional contexts in block`() {
        val events =  runAndReturnEvents(A(), B()) {
            publishEvent(TestEvent(doA()))
            publishEvent(TestEvent(doB()))
        }

        assertThat(events)
            .containsExactly(
                TestEvent("contextA"),
                TestEvent("contextB")
            )
    }

}