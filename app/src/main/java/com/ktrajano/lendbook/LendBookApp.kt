package com.ktrajano.lendbook

import android.app.Application
import com.ktrajano.lendbook.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class LendBookApp : Application() {

  override fun onCreate() {
    super.onCreate()
    initKoin()
  }

  private fun initKoin(){
    startKoin {
      androidContext(this@LendBookApp)
      loadKoinModules(appModule)
    }
  }

}