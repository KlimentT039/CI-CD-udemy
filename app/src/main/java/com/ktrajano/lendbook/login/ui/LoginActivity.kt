package com.ktrajano.lendbook.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ktrajano.lendbook.databinding.ActivityLoginBinding
import com.ktrajano.lendbook.profile.ui.ProfileActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

  private lateinit var binding : ActivityLoginBinding
  private val viewModel  by viewModel<AuthenticationViewModel>()

  private val authEvent : Observer<AuthenticationEvents> = Observer<AuthenticationEvents> {
    if(it!=null && it!=AuthenticationEvents.UserNotAuthenticated){
      lifecycleScope.launchWhenStarted {
        startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
      }
    }else{
      Log.d("Proverka", "Hello ")
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.checkIfUserIsAuthenticatedInFirebase()
    binding = ActivityLoginBinding.inflate(layoutInflater)
    handleButtonsAndObservers()
    setContentView(binding.root)
  }

  private fun handleButtonsAndObservers(){
    viewModel.authEvents.observe(this,authEvent)
    binding.signButton.setOnClickListener {
      Log.d("Proverka", binding.passwordLogin.text.toString())
      viewModel.login(
        binding.mailLogin.text.toString(),
        binding.passwordLogin.text.toString()
      )
    }
    viewModel.checkIfUserIsAuthenticatedInFirebase()
    binding.registerText.setOnClickListener {
      startActivity(Intent(this,RegisterActivity::class.java))
    }
  }


  private fun validationOfFields(){
    //TODO
  }

}