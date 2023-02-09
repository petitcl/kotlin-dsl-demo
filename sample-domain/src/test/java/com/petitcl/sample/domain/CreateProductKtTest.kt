package com.petitcl.sample.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val PRODUCT_SKU_STRING = "ABCD12345678"
private val PRODUCT_SKU = ProductSku(PRODUCT_SKU_STRING)
private const val PRODUCT_NAME = "Blue Shoes"
private const val PRODUCT_DESCRIPTION = "A pair of blue shoes, perfect for any occasion"
private val PRODUCT_PRICE =  100.euros()

internal class CreateProductTest {
    @Test
    fun `should create product with ProductCreatedEvent`() {

        val product = Product.newProduct(
            sku = PRODUCT_SKU,
            name = PRODUCT_NAME,
            description =PRODUCT_DESCRIPTION,
            price = PRODUCT_PRICE,
        )

        assertEquals(PRODUCT_SKU_STRING, product.sku.value)
        assertEquals(PRODUCT_NAME, product.name)
        assertEquals(PRODUCT_DESCRIPTION, product.description)
        assertEquals(PRODUCT_PRICE, product.price)
        assertEquals(emptyList<ProductAttribute>(), product.attributes)

        assertEquals(1, product.events.size)
        assertTrue(product.events[0] is ProductCreatedEvent)
        val event = product.events[0] as ProductCreatedEvent
        assertEquals(PRODUCT_SKU_STRING, event.sku.value)
        assertEquals(PRODUCT_NAME, event.name)
        assertEquals(PRODUCT_DESCRIPTION, event.description)
        assertEquals(PRODUCT_PRICE, event.price)
        assertEquals(emptyList<ProductAttribute>(), event.attributes)
    }

}