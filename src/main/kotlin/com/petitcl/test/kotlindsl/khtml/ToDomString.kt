package com.petitcl.test.kotlindsl.khtml


/**
 * To DOM string
 */

private fun Tag.renderAttributes(): String =
    if (attributes.isEmpty()) ""
    else attributes.map { "${it.key}=\"${it.value}\"" }.joinToString(separator = " ", prefix = " ")

private fun StringBuilder.appendTag(
    tag: Tag, pretty: Boolean, indent: Int,
    block: StringBuilder.() -> Unit
) {
    if (pretty) {
        append("  ".repeat(indent))
        append("<${tag.tagName}${tag.renderAttributes()}>")
        append("\n")
    } else {
        append("<${tag.tagName}${tag.renderAttributes()}>")
    }

    if (pretty) {
        this.block()
    } else {
        this.block()
    }

    if (pretty) {
        append("  ".repeat(indent))
        append("</${tag.tagName}>")
        append("\n")
    } else {
        append("</${tag.tagName}>")
    }
}

private fun StringBuilder.appendString(
    content: String,
    pretty: Boolean, indent: Int
) {
    if (pretty) {
        append("  ".repeat(indent))
        append(content)
        append("\n")
    } else {
        append(content)
    }
}

fun HtmlContent.toDomString(pretty: Boolean = false): String = buildString {
    fun go(tag: HtmlContent, pretty: Boolean, indent: Int) {
        when (tag) {
            is StringContent -> appendString(tag.content, pretty, indent + 1)
            is Tag -> appendTag(tag, pretty, indent) {
                tag.children.forEach { go(it, pretty, indent + 1) }
            }
        }
    }
    go(tag = this@toDomString, pretty = pretty, indent = 0)
}
