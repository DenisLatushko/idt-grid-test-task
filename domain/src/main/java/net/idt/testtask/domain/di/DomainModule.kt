package net.idt.testtask.domain.di

import net.idt.testtask.domain.usecase.CheckGridSettingsUseCase
import net.idt.testtask.domain.usecase.GetGridTextUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

fun getDomainModule(): Module = module {
    factory { CheckGridSettingsUseCase() }

    factory { GetGridTextUseCase(textDataRepo = get()) }
}
