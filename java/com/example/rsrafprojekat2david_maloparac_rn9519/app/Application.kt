package com.example.rsrafprojekat2david_maloparac_rn9519.app

import android.app.Application
import com.example.rsrafprojekat2david_maloparac_rn9519.modules.categoryModule
import com.example.rsrafprojekat2david_maloparac_rn9519.modules.coreModule
import com.example.rsrafprojekat2david_maloparac_rn9519.modules.dishModule
import com.example.rsrafprojekat2david_maloparac_rn9519.modules.savedDishModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(
            coreModule,
            dishModule,
            categoryModule,
            savedDishModule
        )
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            androidFileProperties()
            fragmentFactory()
            modules(modules)
        }
    }

}