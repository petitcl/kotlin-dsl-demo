package com.petitcl.test.kotlindsl.html


/**
 * Data model
 */

sealed interface HtmlContent

sealed interface TagOrStringContent : HtmlContent

sealed interface Tag : TagOrStringContent {
    val tagName: String
    val children: List<TagOrStringContent>
    val attributes: Map<String, String>
}

data class StringContent(val content: String) : TagOrStringContent

data class Html(val body: Body) : Tag {
    override val tagName get() = "html"
    override val children get() = listOf(body)
    override val attributes get() = emptyMap<String, String>()
}

data class Body(val tags: List<Tag>) : Tag {
    override val tagName: String get() = "body"
    override val children get() = tags
    override val attributes get() = emptyMap<String, String>()

    companion object {
        val empty = Body(tags = listOf())
    }
}

data class Div(
    override val attributes: Map<String, String>,
    val contents: List<TagOrStringContent>
) : Tag {
    override val tagName: String get() = "div"
    override val children get() = contents
}

data class Table(
    override val attributes: Map<String, String>,
    val trs: List<Tr>
) : Tag {
    override val tagName: String get() = "table"
    override val children get() = trs
}

data class Tr(
    override val attributes: Map<String, String>,
    val tds: List<Td>
) : Tag {
    override val tagName: String get() = "tr"
    override val children get() = tds
}

data class Td(
    override val attributes: Map<String, String>,
    val contents: List<TagOrStringContent>
) : Tag {
    override val tagName: String get() = "td"
    override val children get() = contents
}
