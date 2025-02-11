package net.idt.testtask.grid.di

import net.idt.testtask.domain.usecase.CheckGridSettingsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun getDomainModule(): Module = module {
    factory { CheckGridSettingsUseCase() }
}
