package com.petitcl.test.kotlindsl.kassert

@DslMarker
annotation class KassertDslMarker

class KAssertionFailedError(override val message: String) : Exception()

class AssertionContext<T>(
    val asserted: T
) {
    fun test(assertion: Assertion<T>) {
        val result = assertion.test(asserted)
        if (!result) {
            throw assertion.reportError(asserted)
        }
    }
}

interface Assertion<T> {
    fun test(asserted: T): Boolean
    fun reportError(asserted: T): KAssertionFailedError
}

interface NegatableAssertion<T> : Assertion<T> {
    fun negated(): Assertion<T>
}

data class EqualsAssertion<T, U>(private val expected: U): NegatableAssertion<T> {

    override fun test(asserted: T) = asserted == expected

    override fun reportError(asserted: T): KAssertionFailedError =
        KAssertionFailedError("Expected `$expected` but was `$asserted`")

    override fun negated(): Assertion<T> = NotEqualsAssertion(expected)
}

data class NotEqualsAssertion<T, U>(private val expected: U): NegatableAssertion<T> {

    override fun test(asserted: T) = asserted != expected

    override fun reportError(asserted: T): KAssertionFailedError =
        KAssertionFailedError("Expected not equal but was `$asserted`")

    override fun negated(): Assertion<T> = EqualsAssertion(expected)
}

class NotNullAssertion<T>: NegatableAssertion<T> {

    override fun test(asserted: T) = asserted != null

    override fun reportError(asserted: T): KAssertionFailedError =
        KAssertionFailedError("Expected <not null> but was <null>")

    override fun negated(): Assertion<T> = NullAssertion()

    override fun toString() = "NotNullAssertion()"
}

class NullAssertion<T>: NegatableAssertion<T> {

    override fun test(asserted: T) = asserted == null

    override fun reportError(asserted: T): KAssertionFailedError =
        KAssertionFailedError("Expected <null> but was `$asserted`")

    override fun negated(): Assertion<T> = NotNullAssertion()

    override fun toString() = "NullAssertion()"
}

@KassertDslMarker
class KassertDsl {

    @KassertDslMarker
    fun <T> beNotNull() = NotNullAssertion<T>()

    @KassertDslMarker
    fun <T> beNull() = NullAssertion<T>()

    @KassertDslMarker
    fun <T, U> beEqualTo(expected: U) = EqualsAssertion<T, U>(expected)

    @KassertDslMarker
    fun <T, U> notBeEqualTo(expected: U) = NotEqualsAssertion<T, U>(expected)

    @KassertDslMarker
    infix fun <T> T.should(assert: Assertion<T>): AssertionContext<T> {
        val ctx = AssertionContext(this)
        ctx.test(assert)
        return ctx
    }

    @KassertDslMarker
    infix fun <T> T.shouldNot(assert: NegatableAssertion<T>): AssertionContext<T> {
        val ctx = AssertionContext(this)
        ctx.test(assert.negated())
        return ctx
    }

    @KassertDslMarker
    infix fun <T> AssertionContext<T>.andShould(assert: Assertion<T>): AssertionContext<T> {
        val ctx = AssertionContext(this.asserted)
        ctx.test(assert)
        return ctx
    }

    @KassertDslMarker
    infix fun <T> AssertionContext<T>.andShouldNot(assert: NegatableAssertion<T>): AssertionContext<T> {
        val ctx = AssertionContext(this.asserted)
        ctx.test(assert.negated())
        return ctx
    }
}

@KassertDslMarker
fun kassert(block: KassertDsl.() -> Unit) {
    KassertDsl().apply(block)
}

@KassertDslMarker
fun <T> kassert(asserted: T, block: KassertDsl.(T) -> Unit) {
    KassertDsl().block(asserted)
}
