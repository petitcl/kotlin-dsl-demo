package com.petitcl.sample.application

import arrow.core.continuations.either
import com.petitcl.domain4k.context.EventsContext
import com.petitcl.domain4k.context.runAndCollectEvents
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
    suspend fun createProductV1(request: CreateProductCommand): ErrorOr<Product> = either {
        val product = Product.newProduct(
            sku = ProductSku(request.sku),
            name = request.name,
            description = request.description,
            price = request.price.toMoney(request.currency),
        ).bind()
        events.publishEvents(product.events)
        repository.save(product)
    }

    suspend fun createProductV2(request: CreateProductCommand): ErrorOr<Product> = either {
        runAndCollectEvents(events) {
            val product = Product.newProduct(
                sku = ProductSku(request.sku),
                name = request.name,
                description = request.description,
                price = request.price.toMoney(request.currency),
            ).bind()
            repository.save(product)
        }
    }
}
