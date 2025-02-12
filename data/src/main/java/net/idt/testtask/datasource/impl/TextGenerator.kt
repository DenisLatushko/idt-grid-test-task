package net.idt.testtask.datasource.impl

import java.util.UUID

class TextGenerator(
    private val randomTextLengthRange: IntRange = (5..15)
) {
    fun newRandomText(): String {
        val randomLength = randomTextLengthRange.random()
        return UUID.randomUUID().toString().take(randomLength)
    }
}
