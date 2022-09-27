@file:Suppress("UNSUPPORTED_FEATURE")

package com.petitcl.test.kotlindsl.kassertext

import com.petitcl.test.kotlindsl.kassert.KassertDsl

@DslMarker
annotation class KassertTestExtDslMarker

/*
 * Without context receivers, it would be impossible to declare this extension of T
 * and make it available only in the context of KassertDsl.
 * It's also possible for describedAs to access members of KassertDsl.
 */
context(KassertDsl)
@KassertTestExtDslMarker
infix fun <T> T.describedAs(description: String): T =
    this.also { println("described as $description") }
