package me.chill.utility.extensions.collection

import me.chill.utility.extensions.generateParameters
import org.junit.Test
import kotlin.test.assertEquals

class MapExtensionsTests {
  @Test fun `Generated parameters must exclude null values`() {
    val map = mapOf(
      "item 1" to null,
      "item 2" to "Hi",
      "item 3" to null
    )

    val expected = mapOf(
      "item 2" to "Hi"
    )

    assertEquals(expected, map.generateParameters())
  }

  @Test fun `Map with Any values returns map with String values`() {
    val map = mapOf(
      "item 1" to 1,
      "item 2" to "Hi",
      "item 3" to 3.2
    )

    val expected = mapOf(
      "item 1" to "1",
      "item 2" to "Hi",
      "item 3" to "3.2"
    )

    assertEquals(expected, map.generateParameters())
  }
}