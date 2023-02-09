package com.petitcl.sample.domain4k.spring

import com.petitcl.domain4k.service.EventDispatcher
import com.petitcl.domain4k.stereotype.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class SpringEventDispatcher(private val publisher: ApplicationEventPublisher) : EventDispatcher {
    override fun publish(event: DomainEvent) = publisher.publishEvent(event)
}

@Configuration
class Domain4kSpringAdapter {
    @Bean
    fun eventDispatcher(publisher: ApplicationEventPublisher) = SpringEventDispatcher(publisher)
}