package com.petitcl.sample.domain

import com.petitcl.domain4k.stereotype.DomainEvent


fun Product.addAttribute(
    attribute: ProductAttribute,
): Product = this.copy(attributes = validateAttributes(attributes + attribute))
    .addEvent(AttributesAddedToProductEvent(sku, listOf(attribute)))

fun Product.addAttributes(
    attributes: List<ProductAttribute>,
): Product = this.copy(attributes = validateAttributes(attributes + attributes))
    .also { validateAttributes(it.attributes) }
    .addEvent(AttributesAddedToProductEvent(sku, attributes))

fun Product.removeAttribute(
    attribute: ProductAttribute,
): Product = this.copy(attributes = attributes.filter { it.name != attribute.name })
    .addEvent(AttributesRemovedFromProductEvent(sku, listOf(attribute)))

fun Product.removeAttributes(
    attributes: List<ProductAttribute>,
): Product = this.copy(attributes = attributes.filter { attributes.any { a -> a.name == it.name } })
    .addEvent(AttributesRemovedFromProductEvent(sku, attributes))

fun Product.replaceAttributes(
    attributes: List<ProductAttribute>,
): Product {
    val attributeChanges = attributes.mapNotNull { attribute ->
        this.attributes.firstOrNull { a -> a.name == attribute.name }
            ?.let { oldAttribute -> AttributeChange(attribute.name, oldAttribute.value, attribute.value) }
    }
    return this.copy(attributes = attributes.map { attributes.firstOrNull { a -> a.name == it.name } ?: it })
        .addEvent(AttributesUpdatedInProductEvent(sku, attributeChanges))
}

data class AttributeChange(
    val name: String,
    val oldValue: String,
    val newValue: String,
)

data class AttributesAddedToProductEvent(
    val sku: ProductSku,
    val addedAttributes: List<ProductAttribute>,
) : DomainEvent

data class AttributesRemovedFromProductEvent(
    val sku: ProductSku,
    val removedAttributes: List<ProductAttribute>,
) : DomainEvent

data class AttributesUpdatedInProductEvent(
    val sku: ProductSku,
    val updates: List<AttributeChange>,
) : DomainEvent
