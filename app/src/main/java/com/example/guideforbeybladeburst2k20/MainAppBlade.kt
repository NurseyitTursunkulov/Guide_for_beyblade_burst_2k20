package com.example.guideforbeybladeburst2k20

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


class MainAppBlade : Application() {
    override fun onCreate() {
        super.onCreate()
        // Creating an extended library configuration.
        val config =
            YandexMetricaConfig.newConfigBuilder("91c8db8c-7ee6-4756-aace-0ecb5c8b4c10").build()
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this)
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@MainAppBlade)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(appModules)
        }
    }

    private val appModules: Module = module {
        viewModel {
            MainViewModelBlade(this@MainAppBlade)
        }
    }
}