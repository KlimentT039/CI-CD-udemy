package com.ktrajano.lendbook.login.service

import com.google.firebase.auth.FirebaseUser
import com.ktrajano.lendbook.login.repository.AuthAppRepository

class AuthenticationServiceLogic(private val authAppRepository: AuthAppRepository) : AuthenticationService {

  override fun login(mail: String, password: String) {
    authAppRepository.login(mail,password)
  }

  override fun registerUser(name: String, email: String, password: String, age: Int) {
    authAppRepository.registerUser(name, email, password, age)
  }

  override fun getUserLiveData(): FirebaseUser? {
    return authAppRepository.isLogged()
  }
}