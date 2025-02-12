package net.idt.testtask.domain.repo

import net.idt.testtask.domain.model.TextDomainModel

/**
 * A repository interface to get texts from data source
 */
interface TextDataRepo {

    /**
     * Get texts from data source
     *
     * @param pageNumber current data page number
     * @param maxItemsNumber number of strings to get from repo
     * @return list of generated texts with its own unique id
     */
    suspend fun getTexts(pageNumber: Int, maxItemsNumber: Int): List<TextDomainModel>
}
