package me.chill.utility.extensions.collection

import me.chill.utility.extensions.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SetExtensionsTests {
  @Test fun `Conditional map must modify values that match predicate`() {
    val set = setOf(
      "12345",
      "$5623",
      "01246",
      "$1299"
    )

    val expected = setOf(
      "$12345",
      "$5623",
      "$01246",
      "$1299"
    )

    val map = set.conditionalMap({ !it.startsWith("$") }) {
      "${'$'}$it"
    }.toSet()

    assertEquals(expected, map)
  }

  @Test fun `Single string is split and added with default pipes`() {
    val input = mutableSetOf<String>()
    input.splitAndAdd("item 1,item 2|stick to item 2,item 3")
    val expected = setOf("item 1", "item 2|stick to item 2", "item 3")

    assertEquals(expected, input)
  }

  @Test fun `Custom pipes split the string by it`() {
    val input = mutableSetOf<String>()
    input.splitAndAdd("Dog|Cat,Bird Walrus|Pig", "|")
    val expected = setOf("Dog", "Cat,Bird Walrus", "Pig")

    assertEquals(expected, input)
  }

  @Test fun `Split items lose any trailing spaces`() {
    val input = mutableSetOf<String>();
    input.splitAndAdd("Dog, Cat , Bird")
    val expected = setOf("Dog", "Cat", "Bird")

    assertEquals(expected, input)
  }

  @Test fun `Contents of string array is split individually and all added to the set`() {
    val arr = arrayOf(
      "Dog,Cat",
      "Bird",
      "Hamster,Kangaroo, Dolphin"
    )

    val input = mutableSetOf<String>()
    input.splitAndAdd(arr)
    val expected = setOf("Dog", "Cat", "Bird", "Hamster", "Kangaroo", "Dolphin")

    assertEquals(expected, input)
  }

  @Test fun `Spaces in strings of string array will be trimmed when added to set`() {
    val arr = arrayOf(
      "Dog, Cat , Bird",
      "Hamster,Kangaroo,  Dolphin"
    )

    val input = mutableSetOf<String>()
    input.splitAndAdd(arr)
    val expected = setOf("Dog", "Cat", "Bird", "Hamster", "Kangaroo", "Dolphin")

    assertEquals(expected, input)
  }

  @Test(expected = IllegalStateException::class)
  fun `Empty set throws IllegalStateException`() {
    val emptySet = emptySet<String>()
    emptySet.checkEmpty("Test")
  }

  @Test fun `Set with content passes checkEmpty`() {
    val set = setOf("Dog", "Cat")
    set.checkEmpty("Animals")
  }

  @Test(expected = IllegalStateException::class)
  fun `Set exceeding size limit throws IllegalStateException`() {
    val maxSet = setOf(1, 2, 3, 4, 5)
    maxSet.checkSizeLimit("Max", 4)
  }

  @Test fun `Set equal to size limit passes checkSizeLimit`() {
    val maxSet = setOf(1, 2, 3, 4, 5)
    maxSet.checkSizeLimit("Max", 5)
  }

  @Test fun `Set less than size limit passes checkSizeLimit`() {
    val set = setOf("Alpha", "Beta", "Charlie")
    set.checkSizeLimit("Positions", 5)
  }

  @Test fun `Empty set generates empty string`() {
    val str = emptySet<String>().generateString()
    assertEquals("", str)
  }

  @Test fun `Non-empty set generates string with generateString`() {
    val str = setOf(1, 2, 3, 4, 5).generateString()
    assertEquals("1,2,3,4,5", str)
  }

  @Test fun `Empty set generates null`() {
    val str = emptySet<String>().generateNullableString()
    assertNull(str)
  }

  @Test fun `Non-empty set generates string with generateNullableString`() {
    val str = setOf("Student", "Course", "Module").generateNullableString()
    assertNotNull(str)
    assertEquals("Student,Course,Module", str)
  }
}