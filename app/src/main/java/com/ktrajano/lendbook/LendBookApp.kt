package com.ktrajano.lendbook

import android.app.Application
import com.ktrajano.lendbook.di.appModule
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class LendBookApp : Application() {

  override fun onCreate() {
    super.onCreate()
    initKoin()
    AppCenter.start(
      this,
      "8bcfd5b6-3806-4df4-9588-be7ad7cf2cc4",
      Analytics::class.java,
      Crashes::class.java
    )
  }

  private fun initKoin() {
    startKoin {
      androidContext(this@LendBookApp)
      loadKoinModules(appModule)
    }
  }

}