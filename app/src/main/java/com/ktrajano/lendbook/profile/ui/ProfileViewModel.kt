package com.ktrajano.lendbook.profile.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ktrajano.lendbook.login.service.AuthenticationService
import com.ktrajano.lendbook.models.User
import com.ktrajano.lendbook.profile.service.ProfileService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ProfileViewModel"

class ProfileViewModel(private val profileService: ProfileService,
private val authenticationService: AuthenticationService) : ViewModel() {

  private val _currentUser = MutableSharedFlow<User>()
  val currentUser : SharedFlow<User> = _currentUser
  private val profileUser = FirebaseAuth.getInstance().currentUser
  private val db = Firebase.firestore


  suspend fun getCurrentUserInfo(){
    if(profileUser != null){
      val docRef = db.collection("Users").document(profileUser.email!!)
      docRef.get()
        .addOnSuccessListener { user ->
          if(user != null){
              viewModelScope.launch {
                _currentUser.emit(user.toObject(User::class.java)!!)
              }
          } else{
            Log.d(TAG,"No such user")
          }
        }
        .addOnFailureListener { exception ->
          Log.d(TAG, "get failed with ", exception)
        }
    }
    else{
      throw Exception("There is no current user")
    }
  }


}