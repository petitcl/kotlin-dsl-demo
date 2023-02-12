package com.petitcl.sample.domain

import javax.money.Monetary
import javax.money.MonetaryAmount

fun Number.euros(): MonetaryAmount = Monetary.getDefaultAmountFactory()
    .setCurrency("EUR")
    .setNumber(this)
    .create()

fun Number.toMoney(currency: String): MonetaryAmount = Monetary.getDefaultAmountFactory()
    .setCurrency(currency)
    .setNumber(this)
    .create()
