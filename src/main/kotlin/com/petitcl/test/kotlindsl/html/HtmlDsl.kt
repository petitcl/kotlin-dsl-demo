@file:Suppress("SuspiciousCollectionReassignment")

package com.petitcl.test.kotlindsl.html

/**
 * DSL classes
 */
@DslMarker
annotation class HtmlDslMarker

@HtmlDslMarker
class AttributesDsl {
    private val attrs : MutableMap<String, String> = mutableMapOf()

    @HtmlDslMarker
    infix fun String.`=`(value: String) = attrs.set(this@`=`, value)

    internal fun attrs(): Map<String, String> = attrs
}

@HtmlDslMarker
fun attrs(block: AttributesDsl.() -> Unit): Map<String, String> {
    return AttributesDsl().apply(block).attrs()
}

@HtmlDslMarker
fun attrs(vararg attrs: Pair<String, String>): Map<String, String> = mapOf(*attrs)

@HtmlDslMarker
interface TagOrStringContentContainerDsl {
    @HtmlDslMarker
    operator fun String.unaryPlus()
    @HtmlDslMarker
    fun div(
        attrs: Map<String, String> = mapOf(),
        block: DivDsl.() -> Unit
    )
}

@HtmlDslMarker
class TagOrStringContentContainerDslImpl : TagOrStringContentContainerDsl {
    private var contents: List<TagOrStringContent> = listOf()

    @HtmlDslMarker
    override operator fun String.unaryPlus() {
        contents += StringContent(this)
    }

    @HtmlDslMarker
    override fun div(
        attrs: Map<String, String>,
        block: DivDsl.() -> Unit
    ) {
        contents += DivDsl(attrs).apply(block).toDomain()
    }

    internal fun contents() = contents
}

@HtmlDslMarker
class HtmlDsl {

    @HtmlDslMarker
    fun body(block: BodyDsl.() -> Unit) {
        body = BodyDsl().apply(block).toDomain()
    }

    /* internal */
    private var body: Body = Body.empty
    internal fun toDomain(): Html = Html(body)
}

@HtmlDslMarker
class BodyDsl {

    @HtmlDslMarker
    fun div(
        attrs: Map<String, String> = mapOf(),
        block: DivDsl.() -> Unit) {
        tags += DivDsl(attrs).apply(block).toDomain()
    }

    @HtmlDslMarker
    fun table(
        attrs: Map<String, String> = mapOf(),
        block: TableDsl.() -> Unit
    ) {
        tags += TableDsl(attrs).apply(block).toDomain()
    }

    /* internal */
    private var tags: List<Tag> = listOf()
    internal fun toDomain(): Body = Body(tags)
}

@HtmlDslMarker
class DivDsl private constructor(
    private val attributes: Map<String, String>,
    private val contents: TagOrStringContentContainerDslImpl,
) : TagOrStringContentContainerDsl by contents {

    /* internal */
    internal fun toDomain(): Div = Div(attributes, contents.contents())

    companion object {
        operator fun invoke(attributes: Map<String, String>): DivDsl {
            return DivDsl(
                attributes,
                TagOrStringContentContainerDslImpl(),
            )
        }
    }
}

@HtmlDslMarker
class TableDsl private constructor(
    private val attributes: Map<String, String>,
    private val contents: TagOrStringContentContainerDslImpl
) : TagOrStringContentContainerDsl by contents {

    @HtmlDslMarker
    fun tr(attrs: Map<String, String> = mapOf(), block: TrDsl.() -> Unit) {
        trs += TrDsl(attrs).apply(block).toDomain()
    }

    /* internal */
    private var trs: List<Tr> = listOf()
    internal fun toDomain(): Table = Table(attributes, trs)

    companion object {
        operator fun invoke(attributes: Map<String, String>): TableDsl = TableDsl(
            attributes,
            TagOrStringContentContainerDslImpl()
        )
    }
}

@HtmlDslMarker
class TrDsl private constructor(
    private val attributes: Map<String, String>,
    private val contents: TagOrStringContentContainerDslImpl
) : TagOrStringContentContainerDsl by contents {

    @HtmlDslMarker
    fun td(attrs: Map<String, String> = mapOf(), block: TdDsl.() -> Unit) {
        tds += TdDsl(attrs).apply(block).toDomain()
    }

    /* internal */
    private var tds: List<Td> = listOf()
    internal fun toDomain(): Tr = Tr(attributes, tds)

    companion object {
        operator fun invoke(attributes: Map<String, String>): TrDsl = TrDsl(
            attributes,
            TagOrStringContentContainerDslImpl()
        )
    }
}

@HtmlDslMarker
class TdDsl private constructor(
    private val attributes: Map<String, String>,
    private val contents: TagOrStringContentContainerDslImpl
) : TagOrStringContentContainerDsl by contents {

    /* internal */
    internal fun toDomain(): Td = Td(attributes, contents.contents())

    companion object {
        operator fun invoke(attributes: Map<String, String>): TdDsl = TdDsl(
            attributes,
            TagOrStringContentContainerDslImpl()
        )
    }
}

/**
 * Entry points
 */
@HtmlDslMarker
fun html(block: HtmlDsl.() -> Unit): Html {
    return HtmlDsl().apply(block).toDomain()
}