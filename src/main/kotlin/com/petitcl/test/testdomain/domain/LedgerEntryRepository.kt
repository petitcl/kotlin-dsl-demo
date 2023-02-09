package com.petitcl.test.testdomain.domain

import java.util.UUID

interface LedgerEntryRepository {
    fun save(entry: LedgerEntry): LedgerEntry
    fun findById(id: UUID): LedgerEntry?
}
