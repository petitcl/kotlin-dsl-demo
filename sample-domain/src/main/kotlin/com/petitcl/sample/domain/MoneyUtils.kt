package com.petitcl.sample.domain

import java.math.BigDecimal
import javax.money.Monetary
import javax.money.MonetaryAmount


fun Int.euros(): MonetaryAmount = Monetary.getDefaultAmountFactory()
    .setCurrency("EUR")
    .setNumber(this)
    .create()

fun BigDecimal.toMoney(currency: String): MonetaryAmount = Monetary.getDefaultAmountFactory()
    .setCurrency(currency)
    .setNumber(this)
    .create()
