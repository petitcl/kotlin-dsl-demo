package com.petitcl.sample.domain

interface ProductsRepository {

    fun save(product: Product): Product

    fun findBySku(sku: ProductSku): Product?
}
