package com.petitcl.sample.domain4k.spring

import com.petitcl.domain4k.context.EventsContext
import com.petitcl.domain4k.context.EventsPublisher
import com.petitcl.domain4k.context.of
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
    fun eventDispatcher(publisher: ApplicationEventPublisher): EventsContext
        = EventsContext.of(SpringEventDispatcher(publisher))
}
