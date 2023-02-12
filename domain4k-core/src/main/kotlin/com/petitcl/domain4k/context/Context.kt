package com.petitcl.domain4k.context

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, R> withContexts(a: A, block: context(A) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a)
}
@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, R> withContexts(a: A, b: B, block: context(A, B) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a, b)
}
@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, C, R> withContexts(a: A, b: B, c: C, block: context(A, B, C) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a, b, c)
}

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, C, D, R> withContexts(a: A, b: B, c: C, d: D, block: context(A, B, C, D) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a, b, c, d)
}

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, C, D, E, R> withContexts(a: A, b: B, c: C, d: D, e: E, block: context(A, B, C, D, E) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a, b, c, d, e)
}

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, C, D, E, F, R> withContexts(a: A, b: B, c: C, d: D, e: E, f: F, block: context(A, B, C, D, E, F) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a, b, c, d, e, f)
}

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, C, D, E, F, G, R> withContexts(a: A, b: B, c: C, d: D, e: E, f: F, g: G, block: context(A, B, C, D, E, F, G) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a, b, c, d, e, f, g)
}
