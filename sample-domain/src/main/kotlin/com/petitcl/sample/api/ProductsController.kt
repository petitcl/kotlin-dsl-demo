package com.petitcl.sample.api

import com.petitcl.sample.application.ProductsService
import com.petitcl.sample.domain.Product
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductsController(
    private val productsService: ProductsService,
) {

    @PostMapping
    fun createProducts(): List<Product> = TODO()

    @GetMapping
    fun getProducts(): List<Product> = TODO()

}