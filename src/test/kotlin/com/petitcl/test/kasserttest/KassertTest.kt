@file:Suppress("UNSUPPORTED_FEATURE")
package com.petitcl.test.kasserttest

import com.petitcl.test.kotlindsl.kassert.*
import com.petitcl.test.kotlindsl.kassertext.describedAs
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class KassertTest {
    private val kassertError = KAssertionFailedError::class.java

    @Test
    fun beNotNull_onNotNull_doesNotThrow() {
        assertDoesNotThrow {
            kassert("123456789") {
                it should beNotNull()
            }
        }
    }

    @Test
    fun beNotNull_onNull_throws() {
        val t = assertThrows(kassertError) {
            kassert(null) {
                it should beNotNull()
            }
        }
        assertEquals("Expected <not null> but was <null>", t.message)
    }

    @Test
    fun beNull_onNull_doesNotThrow() {
        assertDoesNotThrow {
            kassert(null) {
                it should beNull()
            }
        }
    }

    @Test
    fun beNull_onNotNull_throws() {
        val t = assertThrows(kassertError) {
            kassert("123456789") {
                it should beNull()
            }
        }
        assertEquals("Expected <null> but was `123456789`", t.message)
    }

    @Test
    fun negatedBeNull_onNotNull_doesNotThrow() {
        assertDoesNotThrow {
            kassert("123456789") {
                 it shouldNot beNull()
            }
        }
    }

    @Test
    fun negatedBeNull_onNull_throws() {
        val t = assertThrows(kassertError) {
            kassert(null) {
                it shouldNot beNull()
            }
        }
        assertEquals("Expected <not null> but was <null>", t.message)
    }

    @Test
    fun impossibleCombination_throws() {
        val t = assertThrows(kassertError) {
            kassert {
                "123456789" should beNotNull() andShould beNull()
            }
        }
        assertEquals("Expected <null> but was `123456789`", t.message)
    }

    @Test
    fun impossibleCombination_andShouldNot_throws() {
        val t = assertThrows(kassertError) {
            kassert {
                "123456789" should beNotNull() andShouldNot beNotNull()
            }
        }
        assertEquals("Expected <null> but was `123456789`", t.message)
    }

    @Test
    fun impossibleCombination_scoped_throws() {
        val t = assertThrows(kassertError) {
            kassert("123456789") {
                it should beNotNull()
                it should beNull()
            }
        }
        assertEquals("Expected <null> but was `123456789`", t.message)
    }

    @Test
    fun beEqualTo_whenEquals_doesNotThrow() {
        assertDoesNotThrow {
            kassert("123456789") {
                it should beEqualTo("123456789")
            }
        }
    }

    @Test
    fun beEqualTo_whenNotEquals_throws() {
        val t = assertThrows(kassertError) {
            kassert("abcdefgh") {
                it should beEqualTo("123456789")
            }
        }
        assertEquals("Expected `123456789` but was `abcdefgh`", t.message)
    }

    @Test
    fun shouldNot_beEqualTo_whenEquals_throws() {
        val t = assertThrows(kassertError) {
            kassert("123456789") {
                it shouldNot beEqualTo("123456789")
            }
        }
        assertEquals("Expected not equal but was `123456789`", t.message)
    }

    @Test
    fun shouldNot_beEqualTo_whenNotEquals_doesNotThrow() {
        assertDoesNotThrow {
            kassert("abcdefgh") {
                it shouldNot beEqualTo("123456789")
            }
        }
    }

    @Test
    fun notBeEqualTo_whenEquals_doesNotThrow() {
        val t = assertThrows(kassertError) {
            kassert("123456789") {
                it should notBeEqualTo("123456789")
            }
        }
        assertEquals("Expected not equal but was `123456789`", t.message)
    }

    @Test
    fun notBeEqualTo_whenNotEquals_throws() {
        assertDoesNotThrow {
            kassert("abcdefgh") {
                it should notBeEqualTo("123456789")
            }
        }
    }

    @Test
    fun test_contextReceivers() {
        val t = assertThrows(kassertError) {
            kassert("abcdefgh") {
                it describedAs "Hello!" should beEqualTo("123456789")
            }
        }
        assertEquals("Expected `123456789` but was `abcdefgh`", t.message)
    }

}