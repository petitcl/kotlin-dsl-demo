package com.petitcl.sample.application

import arrow.core.continuations.either
import com.petitcl.domain4k.context.EventsContext
import com.petitcl.domain4k.context.lazy
import com.petitcl.domain4k.context.runAndCollectEvents
import com.petitcl.domain4k.context.within
import com.petitcl.domain4k.stereotype.ErrorOr
import com.petitcl.sample.domain.*
import org.springframework.stereotype.Service

data class CreateProductCommand(
    val sku: String,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String,
)

@Service
class ProductsService(
    private val repository: ProductsRepository,
    private val events: EventsContext,
) {
    /**
     * Classic way of doing things:
     * - create the domain object
     * - then manually publish the events
     * - then save the domain object
     *
     * Issue here is that the events are published before the domain object is saved,
     * which could lead to unintended side effects.
     */
    suspend fun createProductV1(request: CreateProductCommand): ErrorOr<Product> = either {
        println("ProductsService.createProductV1")
        val product = Product.newProduct(
            sku = ProductSku(request.sku),
            name = request.name,
            description = request.description,
            price = request.price.toMoney(request.currency),
        ).bind()
        events.publishEvents(product.events)
        repository.save(product)
    }

    /**
     * Slightly better way of doing things:
     * - use runAndCollectEvents to collect the events
     * - create the object, save it, then publish its events
     *
     * The issue here is that the result of the repository does not have any events anymore
     * Therefore we're at risk of not publishing all events if we use the wrong variable.
     * Also, we must call publishEventsOf before saving in the repository,
     * in order to trigger the listeners after the object is saved.
     */
    suspend fun createProductV2(request: CreateProductCommand): ErrorOr<Product> = either {
        println("ProductsService.createProductV2")
        runAndCollectEvents(events) {
            val product = Product.newProduct(
                sku = ProductSku(request.sku),
                name = request.name,
                description = request.description,
                price = request.price.toMoney(request.currency),
            ).bind()
            val result = repository.save(product)
            publishEventsOf(product)
            result
        }
    }

    /**
     * Another variation:
     * - use within and events.pipe() to collect the events
     * - create the object, then publish its events, then save it
     * - the pipe method will delay publishing the events until the end of the block
     *
     * We will need to publish manually and make sure to use the right variable
     */
    suspend fun createProductV3(request: CreateProductCommand): ErrorOr<Product> = either {
        println("ProductsService.createProductV3")
        within(events.lazy()) {
            val product = Product.newProduct(
                sku = ProductSku(request.sku),
                name = request.name,
                description = request.description,
                price = request.price.toMoney(request.currency),
            ).bind()
            publishEventsOf(product)
            repository.save(product)
        }
    }

    /**
     * Another variation with a different approach:
     * - use within and events.pipe() to collect the events
     * - publish event creation directly from the domain object
     * - this combined with events.pipe() gives the guarantee that the events will be published
     * after the object is saved
     *
     * The only issue here is that event publication is not explicit anymore in the caller code.
     * However, it is super explicit in the domain object.
     */
    suspend fun createProductV4(request: CreateProductCommand): ErrorOr<Product> = either {
        println("ProductsService.createProductV4")
        within(events.lazy()) {
            val product = Product.newProductV2(
                sku = ProductSku(request.sku),
                name = request.name,
                description = request.description,
                price = request.price.toMoney(request.currency),
            ).bind()
            repository.save(product)
        }
    }
}
