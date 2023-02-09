package com.petitcl.sample.domain

import com.petitcl.domain4k.stereotype.DomainEntity
import com.petitcl.domain4k.stereotype.DomainEvent
import javax.money.MonetaryAmount

@JvmInline
value class ProductSku(val value: String) {
    init {
        require(value.isNotBlank()) { "ProductSku cannot be blank" }
        require(value.contains(Regex("^[A-Z0-9]{12}$")) ) { "ProductSku should have valid format" }
    }
}

/**
 * Attribute names must be unique per Product
 */
data class Product(
    val sku: ProductSku,
    val name: String,
    val description: String,
    val price: MonetaryAmount,
    val attributes: List<ProductAttribute>,
    override val events: List<DomainEvent>
) : DomainEntity<ProductSku, Product> {
    init {
        validateAttributes(attributes)
    }

    override val id = sku

    override fun addEvent(event: DomainEvent): Product = copy(events = events + event)

    override fun withoutEvents(): Product = copy(events = emptyList())

    companion object
}

internal fun validateAttributes(attributes: List<ProductAttribute>): List<ProductAttribute> {
    require(attributes.map { it.name }.toSet().size == attributes.size) { "Attributes must have unique names" }
    return attributes
}

data class ProductAttribute(
    val name: String,
    val description: String,
    val value: String
)
