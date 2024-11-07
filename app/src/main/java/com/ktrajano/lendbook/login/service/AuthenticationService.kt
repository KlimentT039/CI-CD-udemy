package com.ktrajano.lendbook.login.service

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface AuthenticationService {

  fun login(mail : String, password : String)

  fun registerUser(name: String, email: String, password: String, age: Int)

  fun getUserLiveData() : FirebaseUser?

}