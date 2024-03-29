package com.petitcl.domain4k.context

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ContextWithLifecycle<T> where T : ContextWithLifecycle<T> {
    fun initialize(): T
    fun finalize() = Unit
}

@Suppress("UNCHECKED_CAST")
fun <T> init(context: T): T = if (context is ContextWithLifecycle<*>) context.initialize() as T else context

fun finalize(vararg context: Any?) = context.forEach { if (it is ContextWithLifecycle<*>) it.finalize() }

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, R> within(a: A, block: context(A) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val a1 = init(a)

    val r = block(a1)

    finalize(a1)
    return r
}

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, R> within(a: A, b: B, block: context(A, B) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val a1 = init(a)
    val b1 = init(b)

    val r = block(a1, b1)

    finalize(a1)
    finalize(b1)
    return r
}

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, C, R> within(a: A, b: B, c: C, block: context(A, B, C) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val a1 = init(a)
    val b1 = init(b)
    val c1 = init(c)

    val r = block(a1, b1, c1)

    finalize(a1)
    finalize(b1)
    finalize(c1)
    return r
}

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, C, D, R> within(a: A, b: B, c: C, d: D, block: context(A, B, C, D) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val a1 = init(a)
    val b1 = init(b)
    val c1 = init(c)
    val d1 = init(d)

    val r = block(a1, b1, c1, d1)

    finalize(a1)
    finalize(b1)
    finalize(c1)
    finalize(d1)
    return r
}
