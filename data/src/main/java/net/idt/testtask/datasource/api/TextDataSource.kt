package net.idt.testtask.datasource.api

/**
 * A data source for text data
 */
internal interface TextDataSource {

    /**
     * Generate text based on the pagination approach. The pages have the limited number of items.
     * There is one more limitation which is depends on a number of cells in the grid.
     *
     * @param pageNumber - requested current page number
     * @param maxItemsNumber - max number of items to be generated
     * @return a page with text data items. If it is null then the pagination is finished
     */
    suspend fun getTexts(pageNumber: Int, maxItemsNumber: Int): List<TextData>?
}
