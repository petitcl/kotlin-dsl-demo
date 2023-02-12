package com.petitcl.domain4k.context

/**
 * Context for logging from domain
 */
interface LoggingContext {
    fun log(message: String, vararg args: Any)
    fun debug(message: String, vararg args: Any)
    fun info(message: String, vararg args: Any)
    fun warn(message: String, vararg args: Any)
    fun error(message: String, vararg args: Any)

    companion object
}

fun LoggingContext.Companion.noOp(): LoggingContext = NoOpLoggingContext
internal object NoOpLoggingContext : LoggingContext {
    override fun log(message: String, vararg args: Any) = Unit

    override fun debug(message: String, vararg args: Any) = Unit

    override fun info(message: String, vararg args: Any) = Unit

    override fun warn(message: String, vararg args: Any) = Unit

    override fun error(message: String, vararg args: Any) = Unit
}