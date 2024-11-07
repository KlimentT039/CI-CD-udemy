package com.ktrajano.lendbook.login.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ktrajano.lendbook.models.Book
import com.ktrajano.lendbook.models.User


const val TAG = "AUTH_REPOSITORY"

interface AuthenticatedUserRepository {
  fun isLogged(): FirebaseUser?
}

class AuthAppRepository :  AuthenticatedUserRepository {

  private val firebaseAuth = FirebaseAuth.getInstance()
  private val db = Firebase.firestore

  fun login(email: String, password: String) {
    firebaseAuth.signInWithEmailAndPassword(email, password)
      .addOnCompleteListener {
        if (it.isSuccessful) {
          //Do nothing
        } else {
          Log.d(TAG, "The user with $email and $password is not logged in")
        }
      }
  }

  fun registerUser(name: String, email: String, password: String, age: Int) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
      .addOnCompleteListener {
        if (it.isSuccessful) {
          val user = User(name, email, password, age, emptyList())
          storeInDatabase(user)
        } else {
          Log.d("AUTH_REPOSITORY", "The user can not be created")
        }
      }
  }

  private fun storeInDatabase(user: User) {
    val updateUser = hashMapOf(
      "name" to user.name,
      "mail" to user.mail,
      "password" to user.password,
      "age" to user.age,
      "books" to user.books
    )
    db.collection("Users").document(user.mail!!)
      .set(updateUser)
      .addOnSuccessListener { Log.d("$TAG - - - database", "${user.name} is added in database") }
      .addOnFailureListener {
        Log.d(
          "$TAG - - - database",
          "${user.name} can not be added in database"
        )
      }
  }

  override fun isLogged(): FirebaseUser? {
    return firebaseAuth.currentUser
  }

}