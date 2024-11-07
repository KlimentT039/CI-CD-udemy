package com.ktrajano.lendbook.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.ktrajano.lendbook.BuildConfig
import com.ktrajano.lendbook.books.service.AddBooksService
import com.ktrajano.lendbook.books.service.AddBooksServiceLogic
import com.ktrajano.lendbook.books.ui.AddBookViewModel
import com.ktrajano.lendbook.interceptor.AuthInterceptor
import com.ktrajano.lendbook.login.repository.AuthAppRepository
import com.ktrajano.lendbook.login.service.AuthenticationService
import com.ktrajano.lendbook.login.service.AuthenticationServiceLogic
import com.ktrajano.lendbook.login.ui.AuthenticationViewModel
import com.ktrajano.lendbook.profile.service.ProfileService
import com.ktrajano.lendbook.profile.service.ProfileServiceLogic
import com.ktrajano.lendbook.profile.ui.ProfileViewModel
import com.ktrajano.lendbook.utils.GoogleRestApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create

internal val appModule = module {

  fun provideRetrofit(okHttpClient: OkHttpClient): GoogleRestApi {
    return Retrofit.Builder().baseUrl("https://www.googleapis.com/").client(okHttpClient)
      .addConverterFactory(createConverter()).build().create(GoogleRestApi::class.java)
  }

  fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(authInterceptor)
    setLoggingInterceptor(httpClient)
    return httpClient.build()
  }

  fun getApi(retrofit: Retrofit) : GoogleRestApi = retrofit.create(GoogleRestApi::class.java)

  //Factory
  factory { AuthInterceptor() }

  //Singles
  single { AuthAppRepository() }
  single { }
  single<AuthenticationService> { AuthenticationServiceLogic(get()) }
  single<AddBooksService> { AddBooksServiceLogic(get()) }
  single { provideRetrofit(get()) }
  single { provideOkHttpClient(get()) }
  single<ProfileService> { ProfileServiceLogic() }

  //ViewModels
  viewModel { AuthenticationViewModel(get()) }
  viewModel { ProfileViewModel(get(), get()) }
  viewModel { AddBookViewModel(get()) }

}

fun setLoggingInterceptor(httpClient: OkHttpClient.Builder) {
  if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    httpClient.addInterceptor(loggingInterceptor)
  }
}


fun createConverter(): JacksonConverterFactory {
  val objectMapper = ObjectMapper()
  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  return JacksonConverterFactory.create(objectMapper)
}