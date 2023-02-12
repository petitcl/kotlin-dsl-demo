package com.petitcl.domain4k.stereotype

import arrow.core.Either
import arrow.core.continuations.EffectScope

typealias ErrorOr<T> = Either<AppError, T>

/**
 * Base interface for errors
 */
interface AppError {
    val message: String
    val cause: Throwable?
}

context(EffectScope<R>)
suspend fun <R, A> Either<R, A>.bind(): A =
when (this) {
    is Either.Left -> shift(value)
    is Either.Right -> value
}
