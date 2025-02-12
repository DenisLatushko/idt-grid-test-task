package net.idt.testtask.datasource.impl

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.UUID

private const val TEST_TEXT = "1234567890"

class TextGeneratorTest {

    @Before
    fun `ser up`() {
        mockkStatic(UUID::class)
        every { UUID.randomUUID().toString() } returns TEST_TEXT
    }

    @After
    fun `tear down`() {
        unmockkStatic(UUID::class)
    }

    @Test
    fun `given text length range when new random text then return text with correct length`() {
        val range = 5..15
        val textGenerator = TextGenerator(randomTextLengthRange = range)

        val text = textGenerator.newRandomText()

        assert(text.length in range)
    }

    @Test
    fun `given text length range with same borders when new random text then return text with correct length`() {
        val range = 5..5
        val textGenerator = TextGenerator(randomTextLengthRange = range)

        val text = textGenerator.newRandomText()

        assert(text.length in range)
    }
}
