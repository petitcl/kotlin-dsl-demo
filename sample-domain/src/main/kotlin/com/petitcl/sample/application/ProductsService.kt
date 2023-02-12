package com.petitcl.sample.application

import arrow.core.continuations.EffectScope
import com.petitcl.domain4k.context.EventsContext
import com.petitcl.domain4k.stereotype.AppError
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
    private val eventsContext: EventsContext,
) {
    context(EffectScope<AppError>)
    suspend fun createProduct(request: CreateProductCommand): Product {
        val product = Product.newProduct(
            sku = ProductSku(request.sku),
            name = request.name,
            description = request.description,
            price = request.price.toMoney(request.currency),
        )
        eventsContext.raiseEvents(product.events)
        return repository.save(product)
    }
}
