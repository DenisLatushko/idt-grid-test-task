package net.idt.testtask.grid.di

import net.idt.testtask.domain.model.TextDomainModel
import net.idt.testtask.grid.feature.grid.GridItemState
import net.idt.testtask.grid.feature.grid.GridItemStateMapper
import net.idt.testtask.grid.feature.grid.GridViewModel
import net.idt.testtask.grid.feature.gridbuilder.GridBuilderViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal fun getPresentationModule(): Module = module {
    viewModel { GridBuilderViewModel(get()) }

    viewModel { parameters ->
        GridViewModel(
            initParams = parameters.get(),
            getGridTextUseCase = get(),
            gridItemStateMapper = get()
        )
    }

    factory<(TextDomainModel) -> GridItemState> { GridItemStateMapper() }
}
