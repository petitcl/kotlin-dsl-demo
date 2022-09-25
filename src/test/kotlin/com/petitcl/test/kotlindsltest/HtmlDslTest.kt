package com.petitcl.test.kotlindsltest

import com.petitcl.test.kotlindsl.khtml.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class HtmlDslTest {

    @Test
    fun testSimpleEmptyBodyDom() {
        val dom = html {
            body {
                div {

                }
            }
        }.toDomString()
        assertEquals("<html><body><div></div></body></html>", dom)
    }

    @Test
    fun testSimpleBodyWithTwoDivsDom() {
        val dom = html {
            body {
                div {
                    +"this is a test"
                }
                div {
                    +"this is another test"
                }
            }
        }.toDomString()
        assertEquals("<html><body><div>this is a test</div><div>this is another test</div></body></html>", dom)
    }

    @Test
    fun testSimpleBodyWithTableDom() {
        val dom = html {
            body {
                table {
                    tr {
                        td { +"cell 1-1" }
                        td { +"cell 1-2" }
                    }
                    tr {
                        td { +"cell 2-1" }
                        td { +"cell 2-2" }
                    }
                }
            }
        }.toDomString()
        assertEquals(
            "<html><body><table><tr><td>cell 1-1</td><td>cell 1-2</td></tr><tr><td>cell 2-1</td><td>cell 2-2</td></tr></table></body></html>",
            dom
        )
    }

    @Test
    fun testCustomTableUtil() {
        fun BodyDsl.makeTable(rows: Int, columns: Int) = table {
            for (r in 1..rows) {
                tr {
                    for (c in 1..columns) {
                        td { +"cell $r-$c" }
                    }
                }
            }
        }

        val dom = html {
            body {
                makeTable(rows = 3, columns = 3)
            }
        }.toDomString(pretty = true)

        val expected = """
            <html>
              <body>
                <table>
                  <tr>
                    <td>
                        cell 1-1
                    </td>
                    <td>
                        cell 1-2
                    </td>
                    <td>
                        cell 1-3
                    </td>
                  </tr>
                  <tr>
                    <td>
                        cell 2-1
                    </td>
                    <td>
                        cell 2-2
                    </td>
                    <td>
                        cell 2-3
                    </td>
                  </tr>
                  <tr>
                    <td>
                        cell 3-1
                    </td>
                    <td>
                        cell 3-2
                    </td>
                    <td>
                        cell 3-3
                    </td>
                  </tr>
                </table>
              </body>
            </html>

            """.trimIndent()
        assertEquals(expected, dom)
    }

    @Test
    fun testSimpleBodyWithNestedDivDom() {
        val dom = html {
            body {
                div {
                    +"this "
                    +"is "
                    +"a "
                    +"test"
                    div {
                        +"this "
                        +"is "
                        +"another "
                        +"test"
                    }
                }
            }
        }.toDomString()
        assertEquals("<html><body><div>this is a test<div>this is another test</div></div></body></html>", dom)
    }

    @Test
    fun testSimpleAttributes() {
        val dom = html {
            body {
                div(attrs = attrs("id" to "12345", "class" to "text-md-red")) {
                }
            }
        }.toDomString()
        assertEquals("<html><body><div id=\"12345\" class=\"text-md-red\"></div></body></html>", dom)
    }

    @Test
    fun testExtendedAttributes() {
        val dom = html {
            body {
                div(attrs = attrs {
                    "id" `=` "12345"
                    "class" `=` "text-md-red"
                }) {

                }
            }
        }.toDomString()
        assertEquals("<html><body><div id=\"12345\" class=\"text-md-red\"></div></body></html>", dom)
    }

    @Test
    fun testMapOfAttributes() {
        val dom = html {
            body {
                val myAttrs = mapOf("id" to "12345", "class" to "text-md-red")
                div(attrs = myAttrs) {
                }
            }
        }.toDomString()
        assertEquals("<html><body><div id=\"12345\" class=\"text-md-red\"></div></body></html>", dom)
    }

    @Test
    fun testSimpleBodyWithTableDomPretty() {
        val dom = html {
            body {
                table {
                    tr {
                        td { +"cell 1-1" }
                        td { +"cell 1-2" }
                    }
                    tr {
                        td { +"cell 2-1" }
                        td { +"cell 2-2" }
                    }
                }
            }
        }.toDomString(pretty = true)

        val expected = """
            <html>
              <body>
                <table>
                  <tr>
                    <td>
                        cell 1-1
                    </td>
                    <td>
                        cell 1-2
                    </td>
                  </tr>
                  <tr>
                    <td>
                        cell 2-1
                    </td>
                    <td>
                        cell 2-2
                    </td>
                  </tr>
                </table>
              </body>
            </html>

            """.trimIndent()
        assertEquals(expected, dom)
    }

}
