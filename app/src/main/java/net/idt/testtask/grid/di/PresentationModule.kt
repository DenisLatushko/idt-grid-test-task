package net.idt.testtask.grid.di

import net.idt.testtask.grid.feature.gridbuilder.GridBuilderViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal fun getPresentationModule(): Module = module {
    viewModel { GridBuilderViewModel(get()) }
}
