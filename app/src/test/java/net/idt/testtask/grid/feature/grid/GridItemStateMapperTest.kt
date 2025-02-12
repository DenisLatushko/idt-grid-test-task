package net.idt.testtask.grid.feature.grid

import net.idt.testtask.domain.model.TextDomainModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

private const val TEST_TEXT = "test"

/**
 * Tests for [GridItemStateMapper]
 */
class GridItemStateMapperTest {

    private val mapper = GridItemStateMapper()

    @Test
    fun `given domain model when map then state created`() {
        val domainModel = TextDomainModel(id = 1, text = TEST_TEXT)

        val actualState = mapper(domainModel)

        assertEquals(domainModel.id, actualState.id)
        assertEquals(domainModel.text, actualState.title)
        assertFalse(actualState.isSelected)
        assertFalse(actualState.isEditable)
    }
}
