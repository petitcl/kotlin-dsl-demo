package com.petitcl.sample.api

import com.petitcl.sample.application.CreateProductCommand
import com.petitcl.sample.application.ProductsService
import com.petitcl.sample.domain.Product
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

val command = CreateProductCommand(
    sku = "ABCDEF123456",
    name = "Product 123",
    description = "Product 123 description",
    price = 123.0,
    currency = "EUR",
)

@RestController
@RequestMapping("/v1/products")
class ProductsController(
    private val productsService: ProductsService,
) {

    @PostMapping("/v1")
    fun createProducts() = runBlocking {
        productsService.createProductV1(command)
    }

    @PostMapping("/v2")
    fun createProductsV2() = runBlocking {
        productsService.createProductV2(command)
    }

    @PostMapping("/v3")
    fun createProductsV3() = runBlocking {
        productsService.createProductV3(command)
    }

    @PostMapping("/v4")
    fun createProductsV4() = runBlocking {
        productsService.createProductV4(command)
    }

}