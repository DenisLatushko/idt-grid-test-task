package net.idt.testtask.di

import net.idt.testtask.data.TextDataRepoImpl
import net.idt.testtask.data.TextDomainModelMapper
import net.idt.testtask.datasource.api.TextData
import net.idt.testtask.datasource.api.TextDataSource
import net.idt.testtask.datasource.impl.TEXT_PAGE_SIZE_ITEMS
import net.idt.testtask.datasource.impl.TextDataSourceImpl
import net.idt.testtask.datasource.impl.TextGenerator
import net.idt.testtask.domain.model.TextDomainModel
import net.idt.testtask.domain.repo.TextDataRepo
import org.koin.core.module.Module
import org.koin.dsl.module

fun getDataModule(): Module = module {
    factory<TextDataRepo> {
        TextDataRepoImpl(
            textDataSource = get(),
            textDomainModelMapper = get()
        )
    }

    factory<(TextData) -> TextDomainModel> { TextDomainModelMapper() }

    factory { TextGenerator() }

    single<TextDataSource> {
        TextDataSourceImpl(
            textGenerator = get(),
            pageSize = TEXT_PAGE_SIZE_ITEMS
        )
    }
}
