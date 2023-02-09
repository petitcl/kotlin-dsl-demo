package com.petitcl.test.testdomain


import com.petitcl.domain4k.context.IdsContext
import com.petitcl.domain4k.context.TimeContext
import com.petitcl.domain4k.context.fixed
import com.petitcl.domain4k.context.ofInstant
import com.petitcl.test.testdomain.domain.LedgerEntry
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*
import kotlin.test.assertEquals

class Tests {

    @Test
    fun `should use mock time and uuid context in entity creation`() {
        val fixedDate = Instant.parse("2023-01-01T23:42:00Z")
        val entry = with(TimeContext.ofInstant(fixedDate)) {
            with (IdsContext.fixed("ChuckNorris")) {
                LedgerEntry.newEntry(from = "alice", to = "Bob", amount = 42.00)
            }
        }
        assertEquals(entry.id, UUID.fromString("0d861b74-529d-359d-a3ca-fbec2f3c2286"))
        assertEquals(entry.date, fixedDate)
    }
}
