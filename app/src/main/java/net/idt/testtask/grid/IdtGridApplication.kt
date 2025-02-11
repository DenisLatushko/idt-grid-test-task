package net.idt.testtask.grid

import android.app.Application
import net.idt.testtask.grid.di.getDomainModule
import net.idt.testtask.grid.di.getPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IdtGridApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@IdtGridApplication)
            modules(
                getDomainModule(),
                getPresentationModule()
            )
        }
    }
}
