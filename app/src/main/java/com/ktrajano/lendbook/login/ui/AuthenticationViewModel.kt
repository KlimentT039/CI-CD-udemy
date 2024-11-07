package com.ktrajano.lendbook.login.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ktrajano.lendbook.login.service.AuthenticationService


sealed class AuthenticationEvents{
  object UserAuthenticated : AuthenticationEvents()
  object UserNotAuthenticated : AuthenticationEvents()
}

class AuthenticationViewModel(
  private val authenticationService: AuthenticationService
) : ViewModel() {

  private val _authEvents =
    MutableLiveData<AuthenticationEvents>()
  val authEvents: LiveData<AuthenticationEvents>
    get() = _authEvents

  fun checkIfUserIsAuthenticatedInFirebase() {
    val firebaseUser = authenticationService.getUserLiveData()
    if (firebaseUser == null) {
      _authEvents.postValue(AuthenticationEvents.UserNotAuthenticated)
    } else {
      _authEvents.postValue(AuthenticationEvents.UserAuthenticated)
    }
  }

  fun login(mail : String,password : String){
    authenticationService.login(mail,password)
  }

  fun registerUser(name: String, email: String, password: String, age: Int){
    authenticationService.registerUser(name,email,password,age)
  }

}