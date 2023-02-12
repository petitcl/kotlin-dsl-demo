package com.petitcl.sample.domain4k.spring

import com.petitcl.domain4k.context.*
import com.petitcl.domain4k.stereotype.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class SpringEventDispatcher(private val publisher: ApplicationEventPublisher) : EventsPublisher {
    override fun publishEvent(event: DomainEvent) = publisher.publishEvent(event)
}

@Configuration
class Domain4kSpringAdapter {
    @Bean
    fun eventsContext(publisher: ApplicationEventPublisher): EventsContext
        = EventsContext.of(SpringEventDispatcher(publisher))

    @Bean
    fun timeContext(): TimeContext = TimeContext.default()

}
