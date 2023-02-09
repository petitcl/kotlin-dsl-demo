package com.petitcl.sample.domain

import com.petitcl.domain4k.stereotype.DomainEvent
import javax.money.MonetaryAmount


fun Product.Companion.newProduct(
    sku: ProductSku,
    name: String,
    description: String,
    price: MonetaryAmount,
    attributes: List<ProductAttribute> = emptyList(),
): Product = Product(sku, name, description, price, attributes, emptyList())
    .addEvent(ProductCreatedEvent(sku, name, description, price, attributes))

data class ProductCreatedEvent(
    val sku: ProductSku,
    val name: String,
    val description: String,
    val price: MonetaryAmount,
    val attributes: List<ProductAttribute>,
) : DomainEvent
